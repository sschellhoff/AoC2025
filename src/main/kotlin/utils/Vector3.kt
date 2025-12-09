package utils

import kotlin.math.abs
import kotlin.math.sqrt

data class Vector3(val x: Long, val y: Long, val z: Long) {
    companion object {
        fun fromString(s: String): Vector3 = s.split(",").let { (x, y, z) -> Vector3(x.toLong(), y.toLong(), z.toLong()) }
    }
}

data class Vector3i(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Vector3i) = Vector3i(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector3i) = Vector3i(x - other.x, y - other.y, z - other.z)

    companion object {
        fun fromString(s: String): Vector3i = s.split(",").let { (x, y, z) -> Vector3i(x.toInt(), y.toInt(), z.toInt()) }
    }
}

fun Vector3i.manhattanDistance(other: Vector3i): Int = (this - other).let { fromTo ->
    abs(fromTo.x) + abs(fromTo.y) + abs(fromTo.z)
}

fun Vector3i.euclideanDistance(other: Vector3i): Double = (this - other).let { fromTo ->
    sqrt((fromTo.x.toLong() * fromTo.x.toLong() + fromTo.y.toLong() * fromTo.y.toLong() + fromTo.z.toLong() * fromTo.z.toLong()).toDouble())
}