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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import me.julb.gradleplugins.constants.Constants;

/**
 * The task to generate shell bootstrap file.
 * <br>
 *
 * @author Julb.
 */
public abstract class GenerateShellBootstrapTask extends DefaultTask {

    /**
     * The boostrap file name.
     */
    private static final String SHELL_BOOTSTRAP_FILE_NAME = "bootstrap";
    
    /**
     * Gets the native executable file.
     * @return the native executable file.
     */
    @InputFile
    public abstract RegularFileProperty getNativeExecutableFile();

    /**
     * The output directory.
     * @return the output directory.
     */
    @OutputDirectory
    public abstract DirectoryProperty getOutputDirectory();

    /**
     * The output directory.
     * @return the output directory.
     */
    @OutputFile
    public abstract RegularFileProperty getOutputFile();
    
    /**
     * Default constructor.
     */
    @SuppressWarnings("java:S5993")
    public GenerateShellBootstrapTask() {
        setDescription("Generate a Shell Bootstrap file needed by AWS Lambda.");
        setGroup(Constants.SPRING_NATIVE_AWS_LAMBDA_GROUP_NAME);
        DirectoryProperty buildDir = getProject().getLayout().getBuildDirectory();
        Provider<Directory> outputDir = buildDir.dir("shell/native");
        getOutputDirectory().convention(outputDir);
        getOutputFile().convention(outputDir.map(dir -> dir.file(SHELL_BOOTSTRAP_FILE_NAME)));
    }

    /**
     * Action to execute.
     */
    @TaskAction
    public void execute() {
        // Create output.
        File outputDirectory = getOutputDirectory().getAsFile().get();
        outputDirectory.mkdirs();
        
        // Create output file.
        File outputFile = getOutputFile().getAsFile().get();

        // Trace.
        this.getLogger().info("[Julb] Generating bootstrap file into {}.", outputFile.getPath());

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            StringBuilder sb = new StringBuilder();
            sb.append("#!/bin/sh").append(System.lineSeparator());
            sb.append("set -euo pipefail").append(System.lineSeparator());
            sb.append("cd ${LAMBDA_TASK_ROOT:-.}").append(System.lineSeparator());
            sb.append("./").append(getNativeExecutableFile().get().getAsFile().getName()).append(System.lineSeparator());
            fos.write(sb.toString().getBytes());

            this.getLogger().info("[Julb] Setting 0755 permissions on generated shell file.");
            Files.setPosixFilePermissions(outputFile.toPath(), Set.of(
                PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE, 
                PosixFilePermission.GROUP_READ, PosixFilePermission.GROUP_WRITE, PosixFilePermission.GROUP_EXECUTE, 
                PosixFilePermission.OTHERS_READ, PosixFilePermission.OTHERS_EXECUTE)
            );
        } catch(IOException e) {
            throw new GradleException("Unable to generate a bootstrap file.", e);
        }

        this.getLogger().info("[Julb] Bootstrap file generated successfully.");
    }
}
