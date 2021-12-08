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

import org.ajoberstar.grgit.Commit;
import org.ajoberstar.grgit.Grgit;
import org.ajoberstar.grgit.gradle.GrgitPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtraPropertiesExtension;

import me.julb.gradleplugins.constants.Constants;
import me.julb.gradleplugins.tasks.ChangeProjectVersionCustomTask;
import me.julb.gradleplugins.tasks.ChangeProjectVersionMajorTask;
import me.julb.gradleplugins.tasks.ChangeProjectVersionMinorTask;
import me.julb.gradleplugins.tasks.ChangeProjectVersionPatchTask;
import me.julb.gradleplugins.tasks.ChangeProjectVersionReleaseTask;
import me.julb.gradleplugins.tasks.PrintAllVersionsTask;
import me.julb.gradleplugins.tasks.PrintCurrentBuildVersionTask;
import me.julb.gradleplugins.tasks.PrintCurrentReleaseVersionTask;
import me.julb.gradleplugins.tasks.PrintCurrentVersionTask;

/**
 * The plugin to handle semantic versioning for a project.
 * <br>
 *
 * @author Julb.
 */
public class SemanticVersioningPlugin implements Plugin<Project> {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(Project project) {
        // Trace in
        project.getLogger().info("[Julb] Applying options for Semantic Versioning Plugin for project {}", project.getName());

        Project rootProject = project.getRootProject();

        // Apply GrGit plugin
        rootProject.getPlugins().apply(GrgitPlugin.class);

		// Get commit information
		Grgit grgit = (Grgit) rootProject.getProperties().get("grgit");
		Commit head = grgit.head();

        // Get some git info
        String releaseVersionValue = rootProject.getVersion().toString().replace("-" + Constants.SNAPSHOT_SUFFIX, "");

        ExtraPropertiesExtension ext = rootProject.getExtensions().findByType(ExtraPropertiesExtension.class);
        ext.set(Constants.PROJECT_PROPERTY_RELEASE_VERSION, releaseVersionValue);
        ext.set(Constants.PROJECT_PROPERTY_BUILD_VERSION, String.format("%s.%s", releaseVersionValue, head.getAbbreviatedId()));
        ext.set(Constants.PROJECT_PROPERTY_GIT_REVISION, head.getId());
        ext.set(Constants.PROJECT_PROPERTY_GIT_SHORT_REVISION, head.getAbbreviatedId());
        ext.set(Constants.PROJECT_PROPERTY_GIT_AUTHOR, String.format("%s <%s>", head.getAuthor().getName(),head.getAuthor().getEmail()));
        ext.set(Constants.PROJECT_PROPERTY_GIT_AUTHOR_NAME, head.getAuthor().getName());
        ext.set(Constants.PROJECT_PROPERTY_GIT_AUTHOR_EMAIL, head.getAuthor().getEmail());
		ext.set(Constants.PROJECT_PROPERTY_GIT_SHORT_MESSAGE, head.getShortMessage());
		ext.set(Constants.PROJECT_PROPERTY_GIT_FULL_MESSAGE, head.getFullMessage());
		ext.set(Constants.PROJECT_PROPERTY_GIT_REVISION_DATE, head.getDateTime().toInstant().toString());

        rootProject.getGradle().projectsEvaluated(gradle -> {
            rootProject.getTasks().create(Constants.SEMANTIC_VERSIONING_TASK_ALL_VERSIONS_NAME, PrintAllVersionsTask.class);
            rootProject.getTasks().create(Constants.SEMANTIC_VERSIONING_TASK_CURRENT_VERSION_NAME, PrintCurrentVersionTask.class);
            rootProject.getTasks().create(Constants.SEMANTIC_VERSIONING_TASK_CURRENT_RELEASE_VERSION_NAME, PrintCurrentReleaseVersionTask.class);
            rootProject.getTasks().create(Constants.SEMANTIC_VERSIONING_TASK_CURRENT_BUILD_VERSION_NAME, PrintCurrentBuildVersionTask.class);
            rootProject.getTasks().create(Constants.SEMANTIC_VERSIONING_TASK_CHANGE_VERSION_MAJOR_NAME, ChangeProjectVersionMajorTask.class);
            rootProject.getTasks().create(Constants.SEMANTIC_VERSIONING_TASK_CHANGE_VERSION_MINOR_NAME, ChangeProjectVersionMinorTask.class);
            rootProject.getTasks().create(Constants.SEMANTIC_VERSIONING_TASK_CHANGE_VERSION_PATCH_NAME, ChangeProjectVersionPatchTask.class);
            rootProject.getTasks().create(Constants.SEMANTIC_VERSIONING_TASK_CHANGE_VERSION_RELEASE_NAME, ChangeProjectVersionReleaseTask.class);
            rootProject.getTasks().create(Constants.SEMANTIC_VERSIONING_TASK_CHANGE_VERSION_CUSTOM_NAME, ChangeProjectVersionCustomTask.class);
        });        
    }
}
