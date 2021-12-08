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

import com.github.zafarkhaja.semver.Version;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Properties;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import me.julb.gradleplugins.constants.ChangeVersionType;
import me.julb.gradleplugins.constants.Constants;

/**
 * Updates the version of the project.
 * <br>
 *
 * @author Julb.
 */
public abstract class AbstractChangeProjectVersionTask extends DefaultTask {

    /**
     * The gradle properties file name.
     */
    private static final String GRADLE_PROPERTIES_FILE_NAME = "gradle.properties";

    /**
     * The change version type.
     */
    private ChangeVersionType changeVersionType;

    /**
     * Default constructor.
     * @param changeVersionType the type of version change required.
     */
    protected AbstractChangeProjectVersionTask(ChangeVersionType changeVersionType) {
        this.changeVersionType = changeVersionType;
        setDescription(String.format("Changes the project version using %s method.", changeVersionType.capitalizedName()));
        setGroup(Constants.SEMANTIC_VERSIONING_GROUP_NAME);
    }

    /**
     * Action to execute.
     */
    @TaskAction
    @SuppressWarnings({"java:S106", "java:S2629", "java:S3776"})
    public void execute() {
        // Work on the root project
        Project rootProject = getProject().getRootProject();

        // Try to locate the gradle file.
        File gradlePropertiesFile = new File(getProject().getRootProject().getProjectDir(), GRADLE_PROPERTIES_FILE_NAME);
        if (gradlePropertiesFile.exists()) {
            // Log that we will update the version.
            getProject().getLogger().info("[Julb] Asked to update version with parameter <{}>.", this.changeVersionType.capitalizedName());

            // Compute the new version.
            String newVersion = "";
            if (ChangeVersionType.CUSTOM.equals(this.changeVersionType)) {
                if(!rootProject.hasProperty(Constants.PROJECT_PROPERTY_SET_CUSTOM_VERSION)) {
                    throw new GradleException(String.format("Missing %s property.", Constants.PROJECT_PROPERTY_SET_CUSTOM_VERSION));
                }
                newVersion = rootProject.getProperties().get(Constants.PROJECT_PROPERTY_SET_CUSTOM_VERSION).toString();
            } else if(ChangeVersionType.RELEASE.equals(this.changeVersionType)) {
                newVersion = rootProject.getProperties().get(Constants.PROJECT_PROPERTY_RELEASE_VERSION).toString();
            } else {
                Version semverVersion = Version.valueOf(rootProject.getVersion().toString());
                if (ChangeVersionType.MAJOR.equals(this.changeVersionType)) {
                    semverVersion = semverVersion.incrementMajorVersion(Constants.SNAPSHOT_SUFFIX);
                } else if(ChangeVersionType.MINOR.equals(this.changeVersionType)) {
                    semverVersion = semverVersion.incrementMinorVersion(Constants.SNAPSHOT_SUFFIX);
                } else if(ChangeVersionType.PATCH.equals(this.changeVersionType)) {
                    semverVersion = semverVersion.incrementPatchVersion(Constants.SNAPSHOT_SUFFIX);
                }
                newVersion = semverVersion.toString();
            }

            // Change project version.
            rootProject.setVersion(newVersion);
            System.out.println(String.format("[Julb] New project version is %s.", rootProject.getVersion().toString()));

            // Update gradle.properties
            Properties properties = new Properties();

            // Load file.
            try (FileInputStream fis = new FileInputStream(gradlePropertiesFile)) {
                properties.load(fis);
            } catch(IOException e) {
                getProject().getLogger().error(String.format("Fail to read %s file.", GRADLE_PROPERTIES_FILE_NAME), e);
                throw new GradleException(String.format("Fail to read %s file.", GRADLE_PROPERTIES_FILE_NAME), e);
            }

            // Update version property.
            properties.setProperty("version", newVersion);

            // Serialize properties file.
            try (FileOutputStream fos = new FileOutputStream(gradlePropertiesFile)) {
                properties.store(fos, String.format("Updated by julb-semantic-version Gradle plugin at %s.", Instant.now().toString()));
            } catch(IOException e) {
                getProject().getLogger().error(String.format("Fail to write properties to %s file.", GRADLE_PROPERTIES_FILE_NAME), e);
                throw new GradleException(String.format("Fail to write properties to %s file.", GRADLE_PROPERTIES_FILE_NAME), e);
            }
        }
    }
}
