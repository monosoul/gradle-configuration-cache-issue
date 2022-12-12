package dev.monosoul.gradle.configuration.cache.issue

import org.gradle.api.DefaultTask
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

@CacheableTask
open class FilePrintingTask @Inject constructor(
    objectFactory: ObjectFactory,
    projectLayout: ProjectLayout,
    providerFactory: ProviderFactory,
) : DefaultTask() {

    @InputFile
    @PathSensitive(PathSensitivity.RELATIVE)
    val someFile = objectFactory.fileProperty().convention(
        projectLayout.projectDirectory.file("src/main/resources/some_file.txt")
    )

    @Input
    val maybeSomeFileContent = objectFactory.property<String>().convention(
        providerFactory.fileContents(someFile).asText
    )

    @TaskAction
    fun runTask() {
        logger.info("someFile content:\n${someFile.get().asFile.readText()}")
        logger.info("maybeSomeFileContent content:\n${maybeSomeFileContent.get()}")
    }
}
