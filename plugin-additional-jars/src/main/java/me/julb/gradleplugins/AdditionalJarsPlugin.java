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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.bundling.Jar;

import org.gradle.util.GradleVersion;


/**
 * The plugin to generate additional jars for a project.
 * <br>
 *
 * @author Julb.
 */
public class AdditionalJarsPlugin implements Plugin<Project> {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(Project project) {
        project.getGradle().projectsEvaluated(gradle -> {

            if (project.getPlugins().hasPlugin(JavaPlugin.class)) {
		        project.getLogger().info("[Julb] Applying options for Additional jars plugin for project {}.", project.getName());
                
                JavaPluginExtension javaPluginExtension = project.getExtensions().getByType(JavaPluginExtension.class);
                javaPluginExtension.withSourcesJar();
                javaPluginExtension.withJavadocJar();

                // Add dependency for gradle.
                project.getTasks().withType(Jar.class).getByName("sourcesJar").dependsOn(project.getTasks().withType(JavaCompile.class));

                project.getTasks().withType(Jar.class).all(jarTask -> {
                    jarTask.manifest(manifest -> {
                    
                        // organization name
                        String organizationName = "";
                        if (project.hasProperty("organizationName")) {
                            organizationName = (String) project.getProperties().get("organizationName");
                        }
                        
                        // organization url
                        String organizationUrl = "";
                        if (project.hasProperty("organizationUrl")) {
                            organizationUrl = (String) project.getProperties().get("organizationUrl");
                        }
    
                        // hostname
                        String hostName = "";
                        try {
                            hostName = InetAddress.getLocalHost().getHostName();
                        } catch(UnknownHostException e) {
                            project.getLogger().error("Unable to get hostname", e);
                        }
    
                        Map<String, Object> attributes = new HashMap<>();
                        attributes.put("Implementation-Title", project.getGroup() + ":" + project.getName());
                        attributes.put("Implementation-Version", project.getVersion());
                        attributes.put("Implementation-Vendor", organizationName);
                        attributes.put("Implementation-URL", organizationUrl);
            
                        attributes.put("Specification-Title", project.getGroup() + ":" + project.getName());
                        attributes.put("Specification-Version", project.getVersion());
                        attributes.put("Specification-Vendor", organizationName);
            
                        attributes.put("Created-By", "Gradle " + GradleVersion.current().getVersion());
                        attributes.put("Built-Date", new Date());
                        attributes.put("Built-By", System.getProperty("user.name"));
                        attributes.put("Built-JDK", System.getProperty("java.version"));
                        attributes.put("Built-Host", hostName);
                        
                        attributes.put("Source-Compatibility", javaPluginExtension.getSourceCompatibility());
                        attributes.put("Target-Compatibility", javaPluginExtension.getTargetCompatibility());
                        
                        manifest.attributes(attributes);
                    });
                });
	        } else {
	        	project.getLogger().info("[Julb] Skipping applying options for Additional jars plugin for project {} as it is not a Java project", project.getName());
        	}
        });
    }
}
