package de.sschellhoff.aoc2025

import de.sschellhoff.utils.Day
import de.sschellhoff.utils.Grid
import de.sschellhoff.utils.Vector2i

class Day04: Day<Long, Long>(4, 2025, 13, 43) {
    override fun part1(input: String, isTest: Boolean): Long {
        val grid = Grid.fromString(input) {
            it
        }
        return grid.getAccessible().size.toLong()
    }

    override fun part2(input: String, isTest: Boolean): Long {
        val grid = Grid.fromString(input) {
            it
        }
        var count = 0
        while(true) {
            when(val n = grid.step()) {
                0 -> break
                n -> count += n
            }
        }
        return count.toLong()
    }

    private fun Grid<Char>.step(): Int {
        val toRemove = this.getAccessible()
        toRemove.forEach { pos ->
            this.set(pos, 'x')
        }
        return toRemove.size
    }

    private fun Grid<Char>.getAccessible(): List<Vector2i> {
        val result = mutableListOf<Vector2i>()
        this.forEachIndexedI { x, y, _ ->
            if (this.isAccessible(x, y)) {
                result.add(Vector2i(x, y))
            }
        }
        return result
    }

    private fun Grid<Char>.isAccessible(x: Int, y: Int): Boolean {
        val position = Vector2i(x, y)
        if (this.get(position) != '@') {
            return false
        }
        val neighbouringRolls = this.getNeighboursMoore(position) {
            this.get(it) == '@'
        }
        return neighbouringRolls.size < 4
    }
}