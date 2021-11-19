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

import org.asciidoctor.gradle.jvm.AsciidoctorJPlugin;
import org.asciidoctor.gradle.jvm.AsciidoctorTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.bundling.Zip;

/**
 * The plugin to handle AsciiDoc generation for a project. <br>
 *
 * @author Julb.
 */
public class AsciidoctorPlugin implements Plugin<Project> {

     /**
     * {@inheritDoc}
     */
    @Override
    public void apply(Project project) {
        project.getLogger().info("[Julb] Applying AsciiDoctor Plugin for project {}.", project.getName());
        
        project.getPlugins().apply(AsciidoctorJPlugin.class);

        project.getGradle().projectsEvaluated(gradle -> {
            AsciidoctorTask asciidoctorTask = project.getTasks().withType(AsciidoctorTask.class).getByName("asciidoctor");

            project.getLogger().info("[Julb] AsciidoctorZip will use {} as input.", asciidoctorTask.getOutputDir().getPath());
            
            Zip zipTask = project.getTasks().create("asciidoctorZip", Zip.class);
            zipTask.setDependsOn(Arrays.asList(asciidoctorTask));
            zipTask.from(asciidoctorTask.getOutputDir().getPath());
            zipTask.getArchiveClassifier().set("asciidoc");
        });
    }
}
