package dev.monosoul.gradle.configuration.cache.issue

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import strikt.api.expect
import strikt.assertions.contains
import java.io.File

class GradleConfigurationCacheIssuePluginFunctionalTest {

    @TempDir
    private lateinit var projectDir: File

    private val buildFile get() = projectDir.resolve("build.gradle")
    private val settingsFile get() = projectDir.resolve("settings.gradle")
    private val someFileTxt
        get() = projectDir.resolve("src/main/resources/some_file.txt").also {
            it.parentFile.mkdirs()
        }

    @Test
    fun `should log identical content when runs without configuration cache`() {
        `should log identical content`("printSomeFile", "--info")
    }

    @Test
    fun `should log identical content when runs with configuration cache`() {
        `should log identical content`("printSomeFile", "--info", "--configuration-cache")
    }

    private fun `should log identical content`(vararg args: String) {
        // Setup the test build
        settingsFile.writeText("")

        // language=gradle
        buildFile.writeText(
            """
                plugins {
                    id('dev.monosoul.gradle.configuration.cache.issue')
                }
            """.trimIndent()
        )

        someFileTxt.writeText(
            """
                firstLine
                secondLine
            """.trimIndent()
        )

        // Run the build first time
        val firstRunResult = runBuild(*args)

        // Run the build second time
        someFileTxt.writeText(
            """
                firstLineOnly
            """.trimIndent()
        )
        val secondRunResult = runBuild(*args)

        // Verify the result
        expect {
            that(firstRunResult.output) {
                contains(
                    """
                        someFile content:
                        firstLine
                        secondLine
                    """.trimIndent()
                )
                contains(
                    """
                        maybeSomeFileContent content:
                        firstLine
                        secondLine
                    """.trimIndent()
                )
            }

            that(secondRunResult.output) {
                contains(
                    """
                        someFile content:
                        firstLineOnly
                    """.trimIndent()
                )
                contains(
                    """
                        maybeSomeFileContent content:
                        firstLineOnly
                    """.trimIndent()
                )
            }
        }
    }

    private fun runBuild(vararg args: String) = GradleRunner.create()
        .forwardOutput()
        .withPluginClasspath()
        .withArguments(*args)
        .withProjectDir(projectDir)
        .build()
}
