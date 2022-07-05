plugins {
    id("declarations")
}

tasks.named("generateDeclarations") {
    doLast {
        val coreDefinitionsFile = rootProject.buildDir
            .resolve("js/node_modules/@tanstack/table-core/build/types/index.d.ts")
        val definitionsFile = rootProject.buildDir
            .resolve("js/node_modules/@tanstack/react-table/build/types/index.d.ts")

        val sourceDir = projectDir.resolve("src/main/kotlin")

        delete(sourceDir)

        karakum.table.generateKotlinDeclarations(
            coreDefinitionsFile = coreDefinitionsFile,
            definitionsFile = definitionsFile,
            sourceDir = sourceDir,
        )
    }
}
