package de.sschellhoff.aoc2025

import de.sschellhoff.utils.Day
import kotlin.math.max
import kotlin.math.min

class Day05: Day<Long, Long>(5, 2025, 3, 14) {
    override fun part1(input: String, isTest: Boolean): Long {
        val (ingredientIDRanges, availableIngredientIDs) = input.parse()
        return availableIngredientIDs.count { id ->
            id in ingredientIDRanges
        }.toLong()
    }

    override fun part2(input: String, isTest: Boolean): Long {
        return input.parse().first.combined().sumOf { 1 + it.last - it.first }
    }

    private operator fun List<LongRange>.contains(id: Long): Boolean = this.any { it.contains(id) }

    private fun String.parse(): Pair<List<LongRange>, List<Long>> {
        val (rangesString, availableIDsString) = this.split("\n\n")
        val ingredientIDRanges = rangesString.lines().map {
            val (from, to) = it.split("-")
            from.toLong()..to.toLong()
        }
        val availableIngredientIDs = availableIDsString.lines().map { it.toLong() }
        return ingredientIDRanges to availableIngredientIDs
    }

    private fun List<LongRange>.combined(): List<LongRange> {
        val result = mutableListOf<LongRange>()
        this.forEach { range ->
            result.combine(range)
        }
        return result
    }

    private fun MutableList<LongRange>.combine(range: LongRange) {
        val oldStart = this.firstOrNull() { it.overlaps(range) }
        if (oldStart == null) {
            this.add(range)
            this.sortBy { it.first }
            return
        }
        val oldEnd = this.last { it.overlaps(range) }
        val indexStart = this.indexOf(oldStart)
        val indexEnd = this.indexOf(oldEnd)

        val newFirst = min(range.first, oldStart.first)
        val newLast = max(range.last, oldEnd.last)

        this[indexStart] = newFirst..newLast
        if (indexStart != indexEnd) {
            val rangesToDelete = indexEnd - indexStart
            repeat(rangesToDelete) {
                this.removeAt(indexStart + 1)
            }
        }
    }

    private fun LongRange.overlaps(other: LongRange): Boolean =
        this.contains(other.first)
                || this.contains(other.last)
                || other.contains(this.first)
                || other.contains(this.last)
}