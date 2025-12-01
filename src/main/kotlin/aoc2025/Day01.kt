package de.sschellhoff.aoc2025

import de.sschellhoff.utils.Day
import kotlin.math.abs
import kotlin.math.sign

class Day01: Day<Int, Int>(1, 2025, 3, 6) {
    override fun part1(input: String, isTest: Boolean): Int {
        var counter = 0
        var position = 50
        input.lines().forEach { line ->
            position = move(position, line).mod(100)
            if (position == 0) {
                counter++
            }
        }
        return counter
    }

    override fun part2(input: String, isTest: Boolean): Int {
        var counter = 0
        var position = 50
        input.lines().forEach { line ->
            val move = getMove(line)
            repeat(abs(move)) {
                position += move.sign
                position = position.mod(100)
                if (position == 0) {
                    counter++
                }
            }
        }
        return counter
    }

    private fun getMove(line: String): Int {
        val prefix = line.first()
        val distance = line.drop(1)
        return when (prefix) {
            'R' -> distance.toInt()
            'L' -> -distance.toInt()
            else -> throw IllegalArgumentException()
        }
    }

    private fun move(position: Int, line: String): Int = position + getMove(line)
}