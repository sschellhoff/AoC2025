package de.sschellhoff.aoc2025

import de.sschellhoff.utils.Day
import de.sschellhoff.utils.Grid
import de.sschellhoff.utils.Vector2i

class Day04: Day<Long, Long>(4, 2025, 13, 43) {
    override fun part1(input: String, isTest: Boolean): Long {
        val grid = Grid.fromString(input) {
            it
        }
        var count = 0
        grid.forEachIndexedI { x, y, _ ->
            count += if (grid.isAccessible(x, y)) 1 else 0
        }
        return count.toLong()
    }

    override fun part2(input: String, isTest: Boolean): Long {
        val grid = Grid.fromString(input) {
            it
        }
        var count = 0
        while(true) {
            val nodesToRemove = mutableListOf<Vector2i>()
            grid.forEachIndexedI { x, y, _ ->
                if (grid.isAccessible(x, y)) {
                    nodesToRemove.add(Vector2i(x, y))
                    count += 1
                }
            }
            if (nodesToRemove.isEmpty()) {
                break
            }
            nodesToRemove.forEach { pos ->
                grid.set(pos, 'x')
            }
        }
        return count.toLong()
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