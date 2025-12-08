package utils

import kotlin.math.abs
import kotlin.math.sqrt

data class Vector3(val x: Long, val y: Long, val z: Long)

data class Vector3i(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Vector3i) = Vector3i(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector3i) = Vector3i(x - other.x, y - other.y, z - other.z)
}

fun Vector3i.manhattanDistance(other: Vector3i): Int = (this - other).let { fromTo ->
    abs(fromTo.x) + abs(fromTo.y) + abs(fromTo.z)
}

fun Vector3i.euclideanDistance(other: Vector3i): Double = (this - other).let { fromTo ->
    sqrt((fromTo.x.toLong() * fromTo.x.toLong() + fromTo.y.toLong() * fromTo.y.toLong() + fromTo.z.toLong() * fromTo.z.toLong()).toDouble())
}