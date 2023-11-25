// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
//        classpath("com.squareup.sqldelight:gradle-plugin:1.5.4")
        classpath("com.android.tools.build:gradle:7.0.4")
    }
}