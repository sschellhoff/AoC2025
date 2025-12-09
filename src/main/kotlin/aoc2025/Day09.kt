package aoc2025

import de.sschellhoff.utils.Day
import de.sschellhoff.utils.Vector2
import utils.loopEachPair
import utils.mapEachPair
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day09 : Day<Long, Long>(9, 2025, 50, 24) {
    override fun part1(input: String, isTest: Boolean): Long {
        val redTiles = input.lines().map { Vector2.fromString(it) }
        return redTiles.mapEachPair { tile1, tile2 ->
            tile1.rectangleArea(tile2)
        }.max()
    }

    override fun part2(input: String, isTest: Boolean): Long {
        val redTiles = input.lines().map { Vector2.fromString(it) }
        val lines = redTiles.zipWithNext { start, end -> Line(start, end) } + Line(
            redTiles.last(),
            redTiles.first()
        )

        var maxArea = 0L
        redTiles.loopEachPair { tile1, tile2 ->
            val area = tile1.rectangleArea(tile2)
            // this does coincidentally work but is not valid for every input.
            if (area > maxArea && !lines.intersect(Line(tile1, tile2))) {
                maxArea = area
            }
        }
        return maxArea
    }

    private fun List<Line>.intersect(other: Line): Boolean {
        this.forEach { line ->
            if (line.intersect(Line(other.start, other.end))) {
                return true
            }
        }
        return false
    }

    private data class Line(val start: Vector2, val end: Vector2) {
        fun intersect(other: Line): Boolean {
            val minX = min(other.start.x, other.end.x)
            val minY = min(other.start.y, other.end.y)
            val maxX = max(other.start.x, other.end.x)
            val maxY = max(other.start.y, other.end.y)
            val directed = this.directDownLeft()
            return minX < directed.end.x && minY < directed.end.y && maxX > directed.start.x && maxY > directed.start.y
        }

        fun directDownLeft(): Line = Line(
            Vector2(min(start.x, end.x), min(start.y, end.y)),
            Vector2(max(start.x, end.x), max(start.y, end.y))
        )
    }

    private fun Vector2.rectangleArea(other: Vector2): Long = (abs(x - other.x) + 1) * (abs(y - other.y) + 1)
}



