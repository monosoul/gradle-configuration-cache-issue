package dev.monosoul.gradle.configuration.cache.issue

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * A simple functional test for the 'dev.monosoul.gradle.configuration.cache.issue.greeting' plugin.
 */
class GradleConfigurationCacheIssuePluginFunctionalTest {
    @get:Rule val tempFolder = TemporaryFolder()

    private fun getProjectDir() = tempFolder.root
    private fun getBuildFile() = getProjectDir().resolve("build.gradle")
    private fun getSettingsFile() = getProjectDir().resolve("settings.gradle")

    @Test fun `can run task`() {
        // Setup the test build
        getSettingsFile().writeText("")
        getBuildFile().writeText("""
plugins {
    id('dev.monosoul.gradle.configuration.cache.issue.greeting')
}
""")

        // Run the build
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("greeting")
        runner.withProjectDir(getProjectDir())
        val result = runner.build();

        // Verify the result
        assertTrue(result.output.contains("Hello from plugin 'dev.monosoul.gradle.configuration.cache.issue.greeting'"))
    }
}
