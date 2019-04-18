/******************************************************************************
 *                                                                            *
 *  Copyright 2019 Tyler Cook (https://github.com/cilki)                      *
 *                                                                            *
 *  Licensed under the Apache License, Version 2.0 (the "License");           *
 *  you may not use this file except in compliance with the License.          *
 *  You may obtain a copy of the License at                                   *
 *                                                                            *
 *      http://www.apache.org/licenses/LICENSE-2.0                            *
 *                                                                            *
 *  Unless required by applicable law or agreed to in writing, software       *
 *  distributed under the License is distributed on an "AS IS" BASIS,         *
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 *  See the License for the specific language governing permissions and       *
 *  limitations under the License.                                            *
 *                                                                            *
 *****************************************************************************/
package com.github.cilki.tree_constants;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * The annotation processor that transforms each {@link TreeConstant} into keys
 * of a constant tree.
 * 
 * @author cilki
 * @since 1.0.0
 */
public final class TreeConstantProcessor extends AbstractProcessor {

	/**
	 * Whether source code will be emitted in the first round of annotation
	 * processing. Doing so avoids a compile warning, but is technically incorrect
	 * because some constants will be missed if they show up in later rounds. If
	 * this situation arises, then this should be switched back to {@code false}.
	 * The current setting should be fine as long as other annotation processors do
	 * not produce {@link TreeConstant} fields.
	 */
	private static final boolean EMIT_FIRST_ROUND = true;

	/**
	 * Maps fully qualified tree constant class names to root {@link Node}s.
	 */
	private Map<String, Node> trees = new HashMap<>();

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Set.of(TreeConstant.class.getName());
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
		if (round.processingOver()) {
			if (!EMIT_FIRST_ROUND)
				// Time to write the source files
				emitSource();

			return true;
		}

		// Check whether the source has already been emitted
		if (trees == null) {
			// Warn if some constants will be skipped
			if (!annotations.isEmpty())
				System.out.println(
						annotations.size() + " annotations will be skipped due to unsafe EMIT_FIRST_ROUND setting");
			return false;
		}

		round.getElementsAnnotatedWith(TreeConstant.class).stream().map(VariableElement.class::cast).forEach(elem -> {
			TreeConstant annotation = elem.getAnnotation(TreeConstant.class);

			if (!elem.getModifiers().contains(FINAL))
				throw new RuntimeException("@TreeConstant requires field (" + elem.getSimpleName() + ") to be final");

			String packageName = annotation.packageName();
			if (packageName.isEmpty())
				// If an explicit package is not specified, use the field's package
				packageName = processingEnv.getElementUtils().getPackageOf(elem).getQualifiedName().toString();

			String rootName = annotation.name();
			if (rootName.isEmpty())
				// Set a default name
				rootName = getDefaultName(elem.getEnclosingElement().getSimpleName().toString());

			Node root = trees.get(packageName + "." + rootName);
			if (root == null) {
				root = new Node(rootName, packageName);
				trees.put(packageName + "." + rootName, root);
			}

			// Convert the underscore separated field name into a dot separated path
			String[] path = elem.getSimpleName().toString().replace('_', '.').replaceAll("\\.\\.", "_").split("\\.");

			// Move n down the tree, building nodes if necessary along the way, until the
			// last element of path is reached. The last element will become a field.
			Node n = root;
			for (int i = 0; i < path.length; i++) {

				// Java keywords are not allowed
				if (!SourceVersion.isName(path[i]))
					path[i] = path[i] + "_";

				if (i == path.length - 1) {
					// Build the field
					var field = FieldSpec.builder(TypeName.get(elem.asType()), path[i]).addModifiers(PUBLIC, STATIC,
							FINAL);

					if (elem.asType().getKind().isPrimitive())
						field.initializer("$L", elem.getConstantValue());
					else if (elem.getConstantValue() instanceof String)
						field.initializer("\"$L\"", elem.getConstantValue());
					else
						// TODO throw exception if source field is not visible
						field.initializer("$L", processingEnv.getElementUtils().getPackageOf(elem).getSimpleName() + "."
								+ elem.getSimpleName());

					// Add comment if present
					String javadoc = processingEnv.getElementUtils().getDocComment(elem);
					if (javadoc != null)
						field.addJavadoc("$L", javadoc);

					n.fields.add(field.build());
					break;
				} else {
					Optional<Node> opt = n.getChild(path[i]);
					if (opt.isEmpty()) {
						Node sub = new Node(path[i]);
						n.children.add(sub);
						n = sub;
					} else {
						n = opt.get();
					}
				}
			}
		});

		if (EMIT_FIRST_ROUND)
			// Time to write the source files
			emitSource();

		return true;
	}

	/**
	 * Write the root classes that were built incrementally during annotation
	 * processing.
	 */
	private void emitSource() {
		if (trees == null)
			throw new IllegalStateException("Source has already been emitted");

		for (Node root : trees.values()) {
			try {
				JavaFile.builder(root.packageName, root.toTypeSpec().build()).build().writeTo(processingEnv.getFiler());
			} catch (FilerException e) {
				// Ignore
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		trees.clear();
		trees = null;
	}

	/**
	 * Get the default class name for the root of a constant tree.
	 * 
	 * @param className The name of the class that contains a {@link TreeConstant}
	 * @return The default class name
	 */
	private String getDefaultName(String className) {
		if (className.endsWith("Constants"))
			return className.substring(0, className.length() - 1);
		else
			return className + "Constant";
	}

	/**
	 * A temporary container for nodes while the constant tree is still being
	 * parsed.
	 */
	private static class Node {

		private final String name;
		private final String packageName;
		private final List<Node> children;
		private final List<FieldSpec> fields;

		public Node(String name) {
			this(name, null);
		}

		public Node(String name, String packageName) {
			this.name = Objects.requireNonNull(name);
			this.packageName = packageName;
			this.children = new ArrayList<>();
			this.fields = new ArrayList<>();
		}

		/**
		 * Get a child {@link Node} by name.
		 * 
		 * @param name The name to look for
		 * @return The node
		 */
		public Optional<Node> getChild(String name) {
			return children.stream().filter(node -> node.name.equals(name)).findAny();
		}

		/**
		 * Convert the {@link Node} into a new {@link TypeSpec} builder.
		 * 
		 * @return A new {@link TypeSpec.Builder}
		 */
		public TypeSpec.Builder toTypeSpec() {
			return TypeSpec.classBuilder(name).addModifiers(PUBLIC, FINAL)
					// Add constants
					.addFields(fields)
					// Add children
					.addTypes(() -> children.stream().map(node -> node.toTypeSpec().addModifiers(STATIC).build())
							.iterator())
					// Add private constructor
					.addMethod(MethodSpec.constructorBuilder().addModifiers(PRIVATE).build());
		}
	}
}
