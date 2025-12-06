package de.sschellhoff.aoc2025

import de.sschellhoff.utils.Day

class Day06: Day<Long, Long>(6, 2025, 4277556, 3263827) {
    override fun part1(input: String, isTest: Boolean): Long {
        val operands = input.lines().map { line ->
            "\\d+".toRegex().findAll(line).map { it.value.toLong() }.toList()
        }.filter { it.isNotEmpty() }
        val operators = "[+*]".toRegex().findAll(input.lines().last()).map{ it.value }.toList()
        return operators.mapIndexed { index, operator ->
            val (a, b, c) = operands
            when (operator) {
                "+" -> a[index] + b[index] + c[index] + if (isTest) 0 else operands.last()[index]
                "*" -> a[index] * b[index] * c[index] * if (isTest) 1 else operands.last()[index]
                else -> throw IllegalArgumentException()
            }
        }.sum()
    }

    override fun part2(input: String, isTest: Boolean): Long {
        val lines = input.lines()
        var result = 0L
        val numbers = mutableListOf<Long>()
        (0 until (lines.maxOf { it.length })).reversed().forEach { index ->
            val s = StringBuffer()
            lines.forEach { line ->
                when (val c = if (index in line.indices) line[index] else ' ') {
                    '+' -> {
                        numbers.add(s.toString().toLong())
                        result += numbers.sum()
                        numbers.clear()
                        s.delete(0, s.length)
                    }
                    '*' -> {
                        numbers.add(s.toString().toLong())
                        result += numbers.fold(1, Long::times)
                        numbers.clear()
                        s.delete(0, s.length)
                    }
                    ' ' -> {}
                    else -> s.append(c)
                }
            }
            if (s.isNotEmpty()) {
                numbers.add(s.toString().toLong())
            }
        }
        return result
    }
}