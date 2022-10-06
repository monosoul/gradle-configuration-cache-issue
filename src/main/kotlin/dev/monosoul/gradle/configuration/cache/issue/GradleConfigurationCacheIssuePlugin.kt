package dev.monosoul.gradle.configuration.cache.issue

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register


class GradleConfigurationCacheIssuePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register<FilePrintingTask>("printSomeFile")
    }
}
