/**
 * MIT License
 *
 * Copyright (c) 2011-2021 Julb
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

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.jvm.toolchain.JavaLanguageVersion;

/**
 * The plugin to apply Java 11 toolchain. <P>
 *
 * @author Julb.
 */
public class Java11Plugin implements Plugin<Project> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(Project project) {
        project.getGradle().projectsEvaluated(gradle -> {

            if (project.getPlugins().hasPlugin(JavaPlugin.class)) {
		        project.getLogger().info("[Julb] Applying Java 11 toolchain for project {}.", project.getName());
                
                JavaPluginExtension javaPluginExtension = project.getExtensions().getByType(JavaPluginExtension.class);
                javaPluginExtension.getToolchain().getLanguageVersion().set(JavaLanguageVersion.of(11));
	        } else {
	        	project.getLogger().info("[Julb] Skipping applying Java 11 toolchain for project {} as it is not a Java project", project.getName());
        	}
        });
    }
}
