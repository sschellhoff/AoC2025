package de.sschellhoff.aoc2025

import de.sschellhoff.utils.Day

class Day11 : Day<Long, Long>(11, 2025, 5, 2, testInputSuffixPart2 = "_2") {
    override fun part1(input: String, isTest: Boolean): Long {
        val graph = input.parse()
        return graph.countWays("you", "out", dac = true, fft = true)
    }

    override fun part2(input: String, isTest: Boolean): Long {
        val graph = input.parse()
        return graph.countWays("svr", "out", dac = false, fft = false)
    }

    private data class MemoKey(val source: String, val dac: Boolean, val fft: Boolean)

    private fun Map<String, List<String>>.countWays(source: String, target: String, dac: Boolean, fft: Boolean, memo: MutableMap<MemoKey, Long> = mutableMapOf()): Long {
        if (source == target) {
            return if (fft && dac) 1 else 0
        }

        val memoKey = MemoKey(source, dac = dac, fft = fft)
        val memoized = memo[memoKey]
        if (memoized != null) {
            return memoized
        }

        return this[source]!!.sumOf { next ->
            this.countWays(next, target, dac || next == "dac", fft || next == "fft", memo)
        }.also {
            memo[memoKey] = it
        }
    }

    private fun String.parse(): Map<String, List<String>> = this.lines().associate { line ->
        val (source, targetsString) = line.split(": ")
        val targets = targetsString.split(" ")
        source to targets
    }
}