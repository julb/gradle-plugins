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
import org.gradle.api.GradleException
import org.gradle.api.tasks.Copy

/**
 * The plugin to aggregate SpringDoc OpenApi docs from all subprojects. <P>
 *
 * @author Julb.
 */
class AggregateSpringDocOpenApiDocsPlugin implements Plugin<Project> {
    
    /**
     * Task name to aggregate OpenApi docs.
     */
    private static final String AGGREGATE_SPRINGDOCOPENAPIDOCS_TASK_NAME = 'aggregateSpringDocOpenApiDocs'

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(Project project) {
        project.logger.info('[Julb] Applying options for Aggregate OpenApi docs Plugin for project ' + project.name)
        
        def extension = project.extensions.create(AGGREGATE_SPRINGDOCOPENAPIDOCS_TASK_NAME, AggregateSpringDocOpenApiDocsPluginExtension)
        Project rootProject = project.rootProject
        rootProject.gradle.projectsEvaluated {
        	// Ensure project is correctly configured.
            validateExtension(extension)
            
            // Get subprojects to aggregate.
            Set<Project> springDocOpenApiDocsSubprojects = getOpenApiDocsSubprojects(rootProject, project, extension)
            
            // Proceed to the task.
            if (!springDocOpenApiDocsSubprojects.isEmpty()) {
                project.task(AGGREGATE_SPRINGDOCOPENAPIDOCS_TASK_NAME, type: Copy) {
					dependsOn springDocOpenApiDocsSubprojects.generateOpenApiDocs
                    into extension.outputDir
                    
                    springDocOpenApiDocsSubprojects.each { subproject ->
                        from ("${subproject.generateOpenApiDocs.outputDir.get()}/${subproject.generateOpenApiDocs.outputFileName.get()}") {
                            rename '.*', "${subproject.name}.json"
                        }
                    }
                }
            }
        }
    }

	/**
	 * Lists the subprojects for which to copy OpenApi docs.
	 * @param rootProject the root project.
	 * @param extension the plugin configuration.
	 */
    private static Set<Project> getOpenApiDocsSubprojects(Project rootProject, Project configuredProject, AggregateSpringDocOpenApiDocsPluginExtension extension) {
        if (extension.include != null) {
            // We have a set of projects the user wants to include. Make sure each has the OpenApi docs plugin and proceed
            def includes = new LinkedHashSet(Arrays.asList(extension.include))
            def projects = rootProject.subprojects.findAll { subproject -> includes.remove(subproject.name) }
            projects.forEach { subproject ->
                if (!subproject.plugins.hasPlugin('org.springdoc.openapi-gradle-plugin')) {
                    throw new GradleException("$AGGREGATE_SPRINGDOCOPENAPIDOCS_TASK_NAME-included project '$subproject.name' does not have the SpringDoc OpenApiGradlePlugin, so files cannot be copied" )
                }
            }

            // It is easy to rename a project and forget to add it to the includes. Accordingly, throw if not all includes
            // matched with an actual project
            if (!includes.empty) {
                throw new GradleException("No projects found matching $AGGREGATE_SPRINGDOCOPENAPIDOCS_TASK_NAME includes: " + includes)
            }

            return projects
        } else {
            // By default use all projects with the OpenApiGradlePlugin, but if excludes are defined, apply those
            def projects = rootProject.subprojects.findAll { subproject -> subproject.plugins.hasPlugin('org.springdoc.openapi-gradle-plugin') }
            if (extension.exclude != null) {
                def excludes = new LinkedHashSet(Arrays.asList(extension.exclude))
                projects.removeIf { subproject -> excludes.remove(subproject.name) }

                // It is easy to rename a project and forget to add it to the excludes. Accordingly, throw if not all excludes
                // matched with an actual project
                if (!excludes.empty) {
                    throw new GradleException("No projects found matching $AGGREGATE_SPRINGDOCOPENAPIDOCS_TASK_NAME excludes: " + excludes)
                }
            }

            return projects
        }
    }

	/**
	 * Checks the configuration of the plugin.
	 * @param extension the plugin configuration.
	 */
    private static void validateExtension(AggregateSpringDocOpenApiDocsPluginExtension extension) {
        if (extension.include != null && extension.exclude != null) {
            throw new GradleException("If defining includes, excludes cannot also be defined")
        }
    }
}
