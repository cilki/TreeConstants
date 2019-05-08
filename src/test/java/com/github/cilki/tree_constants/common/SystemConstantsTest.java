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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SystemConstantsTest {

	@Test
	@DisplayName("Check constant values")
	void test_1() {
		assertEquals("awt.toolkit", SystemConstant.awt.toolkit);
		assertEquals("file.encoding", SystemConstant.file.encoding);
		assertEquals("file.separator", SystemConstant.file.separator);
		assertEquals("java.class.path", SystemConstant.java.class_path);
		assertEquals("java.class.version", SystemConstant.java.class_version);
		assertEquals("java.home", SystemConstant.java.home);
		assertEquals("java.io.tmpdir", SystemConstant.java.io.tmpdir);
		assertEquals("java.library.path", SystemConstant.java.library.path);
		assertEquals("java.runtime.name", SystemConstant.java.runtime.name);
		assertEquals("java.specification.vendor", SystemConstant.java.specification.vendor);
		assertEquals("java.specification.version", SystemConstant.java.specification.version);
		assertEquals("java.vendor", SystemConstant.java.vendor);
		assertEquals("java.vendor.url", SystemConstant.java.vendor_url);
		assertEquals("java.version", SystemConstant.java.version);
		assertEquals("java.version.date", SystemConstant.java.version_date);
		assertEquals("java.vm.info", SystemConstant.java.vm.info);
		assertEquals("java.vm.name", SystemConstant.java.vm.name);
		assertEquals("java.vm.specification.version", SystemConstant.java.vm.specification.version);
		assertEquals("java.vm.version", SystemConstant.java.vm.version);
		assertEquals("jdk.debug", SystemConstant.jdk.debug);
		assertEquals("line.separator", SystemConstant.line.separator);
		assertEquals("os.arch", SystemConstant.os.arch);
		assertEquals("os.name", SystemConstant.os.name);
		assertEquals("os.version", SystemConstant.os.version);
		assertEquals("path.separator", SystemConstant.path.separator);
		assertEquals("sun.boot.library.path", SystemConstant.sun.boot.library.path);
		assertEquals("sun.cpu.endian", SystemConstant.sun.cpu.endian);
		assertEquals("sun.java.command", SystemConstant.sun.java.command);
		assertEquals("sun.java.launcher", SystemConstant.sun.java.launcher);
		assertEquals("sun.jnu.encoding", SystemConstant.sun.jnu.encoding);
		assertEquals("user.country", SystemConstant.user.country);
		assertEquals("user.dir", SystemConstant.user.dir);
		assertEquals("user.home", SystemConstant.user.home);
		assertEquals("user.language", SystemConstant.user.language);
		assertEquals("user.name", SystemConstant.user.name);
		assertEquals("user.timezone", SystemConstant.user.timezone);
	}
}
