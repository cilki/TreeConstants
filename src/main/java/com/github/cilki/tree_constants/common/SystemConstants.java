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
package com.github.cilki.tree_constants.common;

import com.github.cilki.tree_constants.TreeConstant;

public final class SystemConstants {

	@TreeConstant
	private static final String awt_toolkit = "awt.toolkit";

	/**
	 * Character that separates components of a file path. This is "/" on UNIX and
	 * "\" on Windows.
	 */
	@TreeConstant
	private static final String file_separator = "file.separator";

	/**
	 * Path used to find directories and JAR archives containing class files.
	 * Elements of the class path are separated by a platform-specific character
	 * specified in the {@code path.separator} property.
	 */
	@TreeConstant
	private static final String java_class__path = "java.class.path";

	@TreeConstant
	private static final String java_class__version = "java.class.version";

	/**
	 * Installation directory for Java Runtime Environment (JRE).
	 */
	@TreeConstant
	private static final String java_home = "java.home";

	@TreeConstant
	private static final String java_io_tmpdir = "java.io.tmpdir";

	@TreeConstant
	private static final String java_library_path = "java.library.path";

	@TreeConstant
	private static final String java_specification_vendor = "java.specification.vendor";

	@TreeConstant
	private static final String java_specification_version = "java.specification.version";

	/**
	 * JRE vendor name.
	 */
	@TreeConstant
	private static final String java_vendor = "java.vendor";

	/**
	 * JRE vendor URL.
	 */
	@TreeConstant
	private static final String java_vendor_url = "java.vendor";

	/**
	 * JRE version number.
	 */
	@TreeConstant
	private static final String java_version = "java.version";

	@TreeConstant
	private static final String java_version_date = "java.version.date";

	@TreeConstant
	private static final String java_vm_name = "java.vm.name";

	@TreeConstant
	private static final String java_vm_specification_version = "java.vm.specification.version";

	@TreeConstant
	private static final String java_vm_version = "java.vm.version";

	@TreeConstant
	private static final String jdk_debug = "jdk.debug";

	/**
	 * Sequence used by operating system to separate lines in text files.
	 */
	@TreeConstant
	private static final String line_separator = "line.separator";

	/**
	 * Operating system architecture.
	 */
	@TreeConstant
	private static final String os_arch = "os.arch";

	/**
	 * Operating system name.
	 */
	@TreeConstant
	private static final String os_name = "os.name";

	/**
	 * Operating system version.
	 */
	@TreeConstant
	private static final String os_version = "os.version";

	/**
	 * Path separator character used in {@code java.class.path}.
	 */
	@TreeConstant
	private static final String path_separator = "path_separator";

	@TreeConstant
	private static final String sun_boot_library_path = "sun.boot.library.path";

	@TreeConstant
	private static final String sun_cpu_endian = "sun.cpu.endian";

	@TreeConstant
	private static final String sun_java_command = "sun.java.command";

	@TreeConstant
	private static final String sun_java_launcher = "sun.java.launcher";

	@TreeConstant
	private static final String sun_jnu_encoding = "sun.jnu.encoding";

	@TreeConstant
	private static final String user_country = "user.country";

	/**
	 * User working directory.
	 */
	@TreeConstant
	private static final String user_dir = "user.dir";

	/**
	 * User home directory.
	 */
	@TreeConstant
	private static final String user_home = "user.home";

	@TreeConstant
	private static final String user_language = "user.language";

	/**
	 * User account name.
	 */
	@TreeConstant
	private static final String user_name = "user.name";

	@TreeConstant
	private static final String user_timezone = "user.timezone";

}
