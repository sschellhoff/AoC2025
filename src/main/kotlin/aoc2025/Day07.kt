package de.sschellhoff.aoc2025

import de.sschellhoff.utils.Day
import de.sschellhoff.utils.Vector2i
import kotlin.text.indexOf

class Day07: Day<Long, Long>(7, 2025, 21, 40) {
    override fun part1(input: String, isTest: Boolean): Long {
        val (diagram, start) = input.parse()
        return countSplits(beams = setOf(start), diagram = diagram)
    }

    override fun part2(input: String, isTest: Boolean): Long {
        val (diagram, start) = input.parse()
        return countTimelines(beamIndex = start, diagram = diagram)
    }

    private fun String.parse(): Pair<List<String>, Int> = this.lines().let { diagram ->
        diagram to diagram.first().indexOf('S')
    }

    private fun countSplits(beams: Set<Int>, diagram: List<String>, layerIndex: Int = 1, numberOfSplits: Long = 0): Long {
        if (layerIndex == diagram.size) {
            return numberOfSplits
        }
        val layer = diagram[layerIndex]
        val nextBeams = mutableSetOf<Int>()
        var newNumberOfSplits = numberOfSplits
        beams.forEach { beamIndex ->
            if (layer[beamIndex] == '^') {
                nextBeams.add(beamIndex - 1)
                nextBeams.add(beamIndex + 1)
                newNumberOfSplits += 1
            } else {
                nextBeams.add(beamIndex)
            }
        }
        return countSplits(nextBeams, diagram, layerIndex + 1, newNumberOfSplits)
    }

    private fun countTimelines(beamIndex: Int, diagram: List<String>, layerIndex: Int = 1, memo: MutableMap<Vector2i, Long> = mutableMapOf()): Long {
        val result = memo[Vector2i(beamIndex, layerIndex)]
        if (result != null) {
            return result
        }
        if (layerIndex == diagram.size) {
            memo.insert(beamIndex, layerIndex, 1)
            return 1
        }
        val layer = diagram[layerIndex]
        return if (layer[beamIndex] == '^') {
            countTimelines(beamIndex - 1, diagram, layerIndex + 1, memo) + countTimelines(beamIndex + 1, diagram, layerIndex + 1, memo)
        } else {
            countTimelines(beamIndex, diagram, layerIndex + 1, memo)
        }.also {
            memo.insert(beamIndex, layerIndex, it)
        }
    }

    private fun MutableMap<Vector2i, Long>.insert(beamIndex: Int, layerIndex: Int, count: Long) {
        this[Vector2i(beamIndex, layerIndex)] = count
    }
}