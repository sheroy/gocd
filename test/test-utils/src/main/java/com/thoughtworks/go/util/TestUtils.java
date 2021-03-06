/*************************GO-LICENSE-START*********************************
 * Copyright 2014 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.go.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.thoughtworks.go.util.ExceptionUtils.bomb;

public class TestUtils {
    public static void extractZipToDir(File repoZip, File repoDir) throws IOException {
        try (ZipInputStream is = new ZipInputStream(new FileInputStream(repoZip))) {
            ZipEntry entry = null;
            while ((entry = is.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    FileUtils.forceMkdir(new File(repoDir, entry.getName()));
                } else {
                    File file = new File(repoDir, entry.getName());
                    FileUtils.forceMkdir(file.getParentFile());
                    try (FileOutputStream entryOs = new FileOutputStream(file)) {
                        IOUtils.copy(is, entryOs);
                    }
                }
            }
        }
    }

    protected TestUtils() {

    }

    public static TypeSafeMatcher<File> isSamePath(final File fileToMatch) {
        return new TypeSafeMatcher<File>() {
            public String actual;
            public String expected;

            public boolean matchesSafely(File o) {
                actual = o.getPath();
                expected = fileToMatch.getPath();
                return actual.equals(expected);
            }

            public void describeTo(Description description) {
                description.appendText("The actual path: [" + actual + "] does not match the expected path [" + expected + "]");
            }
        };
    }

    public static Matcher<? super String> isSameAsPath(final String expected) {
        final String expectedPath = expected.replace('/', File.separatorChar);

        return new TypeSafeMatcher<String>() {
            @Override
            protected boolean matchesSafely(String actual) {
                return expectedPath.equals(actual);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(expectedPath);
            }
        };
    }

    public static TypeSafeMatcher<String> contains(final String toMatch) {
        return new TypeSafeMatcher<String>() {
            public boolean matchesSafely(String underTest) {
                return underTest.contains(toMatch);
            }

            public void describeTo(Description description) {
                description.appendText("The actual string does not contain the expected string.");
            }
        };
    }

    public static void copyAndClose(InputStream fileInputStream, OutputStream fileOutputStream) {
        try {
            IOUtils.copy(fileInputStream, fileOutputStream);
        } catch (IOException e) {
            bomb(e);
        } finally {
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    public static void copyAndClose(String inputFileName, String outputFileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(inputFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFileName);
        copyAndClose(fileInputStream, fileOutputStream);
    }

    public static void sleepQuietly(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
