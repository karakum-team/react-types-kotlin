package karakum.cesium

import karakum.common.GENERATOR_COMMENT
import karakum.common.Suppress
import karakum.common.fileSuppress
import karakum.common.writeCode
import java.io.File

internal fun generateKotlinDeclarations(
    engineDefinitionsFile: File,
    widgetsDefinitionsFile: File,
    sourceDir: File,
) {
    generate(
        declarations = parseDeclarations(engineDefinitionsFile)
            .plus(DefaultEvent)
            .plus(CameraOrientation),
        pkg = "cesium.engine",
        sourceDir = sourceDir.resolve("cesium/engine"),
    )

    generate(
        declarations = parseDeclarations(widgetsDefinitionsFile)
            .filter { it.name != "ContextOptions" }
            .filter { it.name != "WebGLOptions" },
        pkg = "cesium.widgets",
        defaultImports = "import cesium.engine.*",
        sourceDir = sourceDir.resolve("cesium/widgets"),
    )
}

private fun generate(
    declarations: List<Declaration>,
    pkg: String,
    defaultImports: String = "",
    sourceDir: File,
) {
    sourceDir.mkdirs()

    val moduleDeclaration = """@file:JsModule("@${pkg.replace(".", "/")}")"""

    for (declaration in declarations.sortedBy(Declaration::name)) {
        val file = sourceDir.resolve("${declaration.name}.kt")
        val body = declaration.toCode()
        if (!file.exists()) {
            val isRuntime = hasRuntimeDeclarations(body)

            val suppresses = mutableListOf<Suppress>()
            if (declaration is TypeBase)
                suppresses += declaration.suppresses()

            val annotations = when {
                suppresses.isNotEmpty()
                -> fileSuppress(*suppresses.toTypedArray())

                else -> ""
            }

            val content = sequenceOf(
                "// $GENERATOR_COMMENT",
                moduleDeclaration.takeIf { isRuntime } ?: "",
                annotations,
                "package $pkg",
                defaultImports,
                body,
            ).filter { it.isNotEmpty() }
                .joinToString("\n\n")

            file.writeCode(content)
        } else {
            // for functions with union type parameters
            file.appendText("\n\n" + body)
        }
    }
}

private fun hasRuntimeDeclarations(code: String): Boolean {
    if ("\nexternal " !in code && "\nsealed external " !in code)
        return false

    if ("\nsealed external interface " in code)
        return "companion object" in code

    if (code.count("\nexternal ") == code.count("\nexternal interface"))
        return "companion object" in code

    return true
}
