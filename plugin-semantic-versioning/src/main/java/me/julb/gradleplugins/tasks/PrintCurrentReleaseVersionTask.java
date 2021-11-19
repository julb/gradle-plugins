/**
 * MIT License
 *
 * Copyright (c) 2017-2021 Julb
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.julb.gradleplugins.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import me.julb.gradleplugins.constants.Constants;

/**
 * A default task to print the release project version.
 * <br>
 *
 * @author Julb.
 */
public class PrintCurrentReleaseVersionTask extends DefaultTask {

    /**
     * Default constructor.
     */
    public PrintCurrentReleaseVersionTask() {
        setDescription("Prints the current release version of the project");
        setGroup(Constants.SEMANTIC_VERSIONING_GROUP_NAME);
    }

    /**
     * Action to execute.
     */
    @TaskAction
    @SuppressWarnings("java:S106")
    public void execute() {
        System.out.println(getProject().getProperties().get(Constants.PROJECT_PROPERTY_RELEASE_VERSION).toString());
    }
}
