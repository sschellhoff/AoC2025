package de.sschellhoff.aoc2025

import de.sschellhoff.utils.Day

class Day03: Day<Long, Long>(3, 2025, 357, 3121910778619) {
    override fun part1(input: String, isTest: Boolean): Long =
        input.lines().sumOf { it.toBank().findLargestPossibleJoltage(2) }

    override fun part2(input: String, isTest: Boolean): Long =
        input.lines().sumOf { it.toBank().findLargestPossibleJoltage(12) }

    private tailrec fun List<Int>.findLargestPossibleJoltage(size: Int, acc: Long = 0): Long {
        if (size == 0) {
            return acc
        }
        val current = this.dropLast(size - 1).max()
        return this.drop(this.indexOf(current) + 1).findLargestPossibleJoltage(size - 1, acc * 10 + current)
    }

    private fun String.toBank(): List<Int> = this.toCharArray().map { it.digitToInt() }
}