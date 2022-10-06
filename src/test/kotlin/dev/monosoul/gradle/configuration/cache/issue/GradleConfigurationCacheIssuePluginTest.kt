package dev.monosoul.gradle.configuration.cache.issue

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * A simple unit test for the 'dev.monosoul.gradle.configuration.cache.issue.greeting' plugin.
 */
class GradleConfigurationCacheIssuePluginTest {
    @Test fun `plugin registers task`() {
        // Create a test project and apply the plugin
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("dev.monosoul.gradle.configuration.cache.issue.greeting")

        // Verify the result
        assertNotNull(project.tasks.findByName("greeting"))
    }
}
