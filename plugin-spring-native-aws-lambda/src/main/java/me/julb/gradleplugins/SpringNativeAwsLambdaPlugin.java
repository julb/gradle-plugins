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
package me.julb.gradleplugins;

import java.util.Arrays;

import org.graalvm.buildtools.gradle.NativeImagePlugin;
import org.graalvm.buildtools.gradle.tasks.BuildNativeImageTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Zip;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.springframework.aot.gradle.SpringAotGradlePlugin;

import me.julb.gradleplugins.constants.Constants;
import me.julb.gradleplugins.tasks.GenerateShellBootstrapTask;

/**
 * The plugin to handle AsciiDoc generation for a project. <br>
 *
 * @author Julb.
 */
public class SpringNativeAwsLambdaPlugin implements Plugin<Project> {

     /**
     * {@inheritDoc}
     */
    @Override
    public void apply(Project project) {
        project.getGradle().projectsEvaluated(gradle -> {
            if (project.getPlugins().hasPlugin(SpringAotGradlePlugin.class) && project.getPlugins().hasPlugin(NativeImagePlugin.class)) {
                project.getLogger().info("[Julb] Applying SpringNativeAwsLambdaPlugin Plugin for project {}.", project.getName());
                
                // Get native executable name.
                BuildNativeImageTask buildNativeImageTask = project.getTasks().withType(BuildNativeImageTask.class).getByName(NativeImagePlugin.NATIVE_COMPILE_TASK_NAME);
                
                // We need to write a bootstrap file.
                TaskProvider<GenerateShellBootstrapTask> generateShellBootstrapFileTaskProvider = project.getTasks().register(Constants.SPRING_NATIVE_AWS_LAMBDA_TASK_GENERATE_SHELL_BOOTSTRAP_NAME, GenerateShellBootstrapTask.class, task -> {
                    task.setDependsOn(Arrays.asList(buildNativeImageTask));
                    task.getNativeExecutableFile().convention(buildNativeImageTask.getOutputFile());
                });

                // Zip task to package all things.
                TaskProvider<Zip> zipTaskProvider = project.getTasks().register(Constants.SPRING_NATIVE_AWS_LAMBDA_TASK_ZIP_NAME, Zip.class, zipTask -> {
                    zipTask.setGroup(Constants.SPRING_NATIVE_AWS_LAMBDA_GROUP_NAME);
                    zipTask.setDependsOn(Arrays.asList(buildNativeImageTask, generateShellBootstrapFileTaskProvider));
                    zipTask.from(buildNativeImageTask.getOutputFile(), generateShellBootstrapFileTaskProvider.map(GenerateShellBootstrapTask::getOutputFile));
                    zipTask.getArchiveClassifier().set("aws-native");
                });
                
                // Add as part of the build.
                project.getArtifacts().add("archives", zipTaskProvider);
                project.getTasks().getByName(LifecycleBasePlugin.BUILD_TASK_NAME).dependsOn(zipTaskProvider);
            } else {
                project.getLogger().info("[Julb] Skipping SpringNativeAwsLambdaPlugin Plugin for project {} as it has not Gradle Spring AOT plugin applied.", project.getName());
            }
        });
    }
}
