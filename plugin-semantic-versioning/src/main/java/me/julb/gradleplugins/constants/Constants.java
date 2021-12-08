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

package me.julb.gradleplugins.constants;

/**
 * The constants used by the plugin.
 * <br>
 *
 * @author Julb.
 */
public class Constants {

    /**
     * The group name.
     */
    public static final String SEMANTIC_VERSIONING_GROUP_NAME = "Semantic Versioning";

    /**
     * The task name for listing all versions.
     */
    public static final String SEMANTIC_VERSIONING_TASK_ALL_VERSIONS_NAME = "allVersions";

    /**
     * The task name for printing the current version.
     */
    public static final String SEMANTIC_VERSIONING_TASK_CURRENT_VERSION_NAME = "currentVersion";

    /**
     * The task name for printing the current release version.
     */
    public static final String SEMANTIC_VERSIONING_TASK_CURRENT_RELEASE_VERSION_NAME = "currentReleaseVersion";

    /**
     * The task name for printing the current build version.
     */
    public static final String SEMANTIC_VERSIONING_TASK_CURRENT_BUILD_VERSION_NAME = "currentBuildVersion";

    /**
     * The task name for upgrading to the next major version.
     */
    public static final String SEMANTIC_VERSIONING_TASK_CHANGE_VERSION_MAJOR_NAME = "changeVersionMajor";

    /**
     * The task name for upgrading to the next minor version.
     */
    public static final String SEMANTIC_VERSIONING_TASK_CHANGE_VERSION_MINOR_NAME = "changeVersionMinor";

    /**
     * The task name for upgrading to the next patch version.
     */
    public static final String SEMANTIC_VERSIONING_TASK_CHANGE_VERSION_PATCH_NAME = "changeVersionPatch";

    /**
     * The task name for upgrading to the release version.
     */
    public static final String SEMANTIC_VERSIONING_TASK_CHANGE_VERSION_RELEASE_NAME = "changeVersionRelease";

    /**
     * The task name for upgrading to set a custom version.
     */
    public static final String SEMANTIC_VERSIONING_TASK_CHANGE_VERSION_CUSTOM_NAME = "changeVersionCustom";

    /**
     * The release version property.
     */
    public static final String PROJECT_PROPERTY_RELEASE_VERSION = "releaseVersion";

    /**
     * The build version property.
     */
    public static final String PROJECT_PROPERTY_BUILD_VERSION = "buildVersion";

    /**
     * The git revision property.
     */
    public static final String PROJECT_PROPERTY_GIT_REVISION = "gitRevision";

    /**
     * The git short revision property.
     */
    public static final String PROJECT_PROPERTY_GIT_SHORT_REVISION = "gitShortRevision";

    /**
    * The git author property.
    */
    public static final String PROJECT_PROPERTY_GIT_AUTHOR = "gitAuthor";

     /**
     * The git author name property.
     */
    public static final String PROJECT_PROPERTY_GIT_AUTHOR_NAME = "gitAuthorName";

     /**
     * The git author email property.
     */
    public static final String PROJECT_PROPERTY_GIT_AUTHOR_EMAIL = "gitAuthorEmail";

     /**
     * The git short message property.
     */
    public static final String PROJECT_PROPERTY_GIT_SHORT_MESSAGE = "gitShortMessage";

     /**
     * The git full message property.
     */
    public static final String PROJECT_PROPERTY_GIT_FULL_MESSAGE = "gitFullMessage";

     /**
     * The git revision date property.
     */
    public static final String PROJECT_PROPERTY_GIT_REVISION_DATE = "gitRevisionDate";

    /**
     * The snapshot suffix.
     */
    public static final String SNAPSHOT_SUFFIX = "SNAPSHOT";

    /**
     * The property which contains the new version when changing using custom.
     */
    public static final String PROJECT_PROPERTY_SET_CUSTOM_VERSION = "newVersion";

    /**
     * Default constructor.
     */
    private Constants() {}
    
}
