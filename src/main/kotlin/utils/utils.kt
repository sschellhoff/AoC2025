package de.sschellhoff.utils

import kotlin.collections.reduce

fun Long.toIntOrThrow(): Int {
    check(this in (Int.MIN_VALUE..Int.MAX_VALUE))
    return toInt()
}

fun Collection<Int>.product(): Long = this.map { it.toLong() }.reduce { acc, i -> acc * i }