package de.peekandpoke.app.utils

import org.w3c.dom.CENTER
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.CanvasTextAlign
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.math.round
import kotlin.random.Random

fun createAvatarImage(name: String, size: Int = 120): String {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D

    // Extract the first two initials
    val letters = name.split(" ")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .map { it[0] }
        .take(2)

    // Generate a random seed from the given name, so the color will always be the same for the same name.
    val random = Random(name.sumBy { it.toInt() })

    val color = "rgb(${random.nextDouble() * 255}, ${random.nextDouble() * 255}, ${random.nextDouble() * 255})"

    // Set canvas with & height
    canvas.width = size
    canvas.height = size

    // Select a font family to support different language characters
    // like Arial
    context.font =  "${round(canvas.width / 2.0)}px Arial"
    context.textAlign = CanvasTextAlign.CENTER

    // Setup background and front color
    context.fillStyle = color
    context.fillRect(0.toDouble(), 0.toDouble(), canvas.width.toDouble(), canvas.height.toDouble())
    context.fillStyle = "#FFF"
    context.fillText(letters.joinToString("").toUpperCase(), size / 2.0, size / 1.5)

    // Set image representation in default format (png)
    return canvas.toDataURL()
}
