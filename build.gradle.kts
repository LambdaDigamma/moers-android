/**
 * The extra object can be used for custom properties and makes them available to all
 * modules in the project.
 * The following are only a few examples of the types of properties you can define.
 */
extra["sdkVersion"] = 31
extra["minSdkVersion"] = 21
extra["targetSdkVersion"] = 31

extra["appVersion"] = "0.0.1"
extra["appVersionCode"] = 1

var kotlinVersion = "1.5.31"
var gradleVersion = "7.0.4"

extra["kotlinVersion"] = kotlinVersion
extra["gradleVersion"] = gradleVersion
extra["composeVersion"] = "1.0.5"
extra["datastoreVersion"] = "1.0.0"
extra["protobufVersion"] = "3.19.0"

extra["roomVersion"] = "2.4.1"
extra["gsonVersion"] = "2.8.6"

/**
 * The buildscript block is where you configure the repositories and
 * dependencies for Gradle itself — meaning, you should not include dependencies
 * for your modules here. For example, this block includes the Android plugin for
 * Gradle as a dependency because it provides the additional instructions Gradle
 * needs to build Android app modules.
 */
buildscript {

    val kotlinVersion: String by rootProject.extra
    val gradleVersion: String by rootProject.extra


    /**
     * The repositories block configures the repositories Gradle uses to
     * search or download the dependencies. Gradle pre-configures support for remote
     * repositories such as JCenter, Maven Central, and Ivy. You can also use local
     * repositories or define your own remote repositories. The code below defines
     * JCenter as the repository Gradle should use to look for its dependencies.
     */
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    /**
     * The dependencies block configures the dependencies Gradle needs to use
     * to build your project. The following line adds Android plugin for Gradle
     * version 7.0.4 as a classpath dependency.
     */
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}