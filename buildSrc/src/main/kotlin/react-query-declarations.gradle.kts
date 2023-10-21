plugins {
    id("declarations")
}

tasks.named("generateDeclarations") {
    doLast {
        val tanstackDir = nodeModules.resolve("@tanstack")

        val coreTypesDir = tanstackDir
            .resolve("query-core/build/lib")

        val reactTypesDir = tanstackDir
            .resolve("react-query/build/lib")

        val sourceDir = projectDir.resolve("src/jsMain/kotlin")

        delete(sourceDir)

        karakum.query.generateKotlinDeclarations(
            coreTypesDir = coreTypesDir,
            reactTypesDir = reactTypesDir,
            sourceDir = sourceDir,
        )
    }
}
