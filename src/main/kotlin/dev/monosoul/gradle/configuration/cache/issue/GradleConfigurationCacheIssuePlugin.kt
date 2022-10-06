package dev.monosoul.gradle.configuration.cache.issue

import org.gradle.api.Plugin
import org.gradle.api.Project


class GradleConfigurationCacheIssuePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Register a task
        project.tasks.register("greeting") {
            doLast {
                println("Hello from plugin 'dev.monosoul.gradle.configuration.cache.issue.greeting'")
            }
        }
    }
}
