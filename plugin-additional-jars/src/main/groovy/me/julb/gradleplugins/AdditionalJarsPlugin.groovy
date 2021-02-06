/**
 *  MIT License
 *  
 *  Copyright (c) 2021 Julb
 *  
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package me.julb.gradleplugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.util.GradleVersion
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.publish.maven.MavenPublication


/**
 * The plugin to generate additional jars for a project. <P>
 *
 * @author Julb.
 */
class AdditionalJarsPlugin implements Plugin<Project> {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(Project project) {
    	project.gradle.projectsEvaluated {
    	
        	if(project.plugins.hasPlugin(JavaPlugin)) {
        	
		        project.logger.info('[Julb] Applying options for Additional jars plugin for project ' + project.name)
		        
		        // Default organization name.
		        def organizationName = ''
		        if(project.hasProperty('organizationName')) {
		            organizationName = project.organizationName
		        }
		        
		        // Default organization URL.
		        def organizationUrl = ''
		        if(project.hasProperty('organizationUrl')) {
		            organizationUrl = project.organizationUrl
		        }
	            
	            project.java {
	            	withSourcesJar()
	            	withJavadocJar()
	            }
	
	            project.jar {
	                manifest { 
	                	attributes(
			                "Implementation-Title": project.group + ':' + project.name,
				            "Implementation-Version": project.version,
				            "Implementation-Vendor": organizationName,
				            "Implementation-URL": organizationUrl,
				
				            "Specification-Title": project.group + ':' + project.name,
				            "Specification-Version": project.version,
				            "Specification-Vendor": organizationName,
				
				            "Created-By": 'Gradle ' + GradleVersion.current().version,
				            "Built-Date": new Date(),
				            "Built-By": System.getProperty('user.name'),
				            "Built-JDK": System.getProperty('java.version'),
				            "Built-Host": InetAddress.localHost.hostName,
		                	
		                	"Source-Compatibility": project.sourceCompatibility,
		                	"Target-Compatibility": project.targetCompatibility
		                )
	                }
	            }
	        } else {
	        	project.logger.info('[Julb] Skipping Additional jars plugin for project ' + project.name + ' as it is not a Java project')
        	}
        }
    }
}