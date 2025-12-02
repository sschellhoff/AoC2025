package de.sschellhoff.aoc2025

import de.sschellhoff.utils.Day

class Day02: Day<Long, Long>(2, 2025, 1227775554, 4174379265) {
    override fun part1(input: String, isTest: Boolean): Long =
        input.split(",").map { it.parseRange() }.getInvalids(Mode.PART_1).sum()

    override fun part2(input: String, isTest: Boolean): Long =
        input.split(",").map { it.parseRange() }.getInvalids(Mode.PART_2).sum()

    private fun String.parseRange(): LongRange {
        val (start, end) = this.split("-")
        return start.toLong()..end.toLong()
    }

    private fun Iterable<LongRange>.getInvalids(mode: Mode): Set<Long> = this.flatMap { it.getInvalids(mode) }.toSet()

    private fun LongRange.getInvalids(mode: Mode): Set<Long> = this.filter { it.isInvalid(mode) }.toSet()

    private fun Long.isInvalid(mode: Mode): Boolean = when (mode) {
        Mode.PART_1 -> this.isInvalidPart1()
        Mode.PART_2 -> this.isInvalidPart2()
    }

    private fun Long.isInvalidPart1(): Boolean {
        val asString = this.toString()
        if (asString.length % 2 == 1) {
            return false
        }
        return asString.startsWith(asString.substring(asString.length / 2))
    }

    private fun Long.isInvalidPart2(): Boolean {
        val s = this.toString()
        return (1..s.length/2).any { s.repeats(it) }
    }

    private fun String.repeats(subLength: Int): Boolean {
        if (this.length % subLength != 0) {
            return false
        }
        return this.take(subLength).repeat(this.length / subLength) == this
    }

    private enum class Mode {
        PART_1,
        PART_2
    }
}