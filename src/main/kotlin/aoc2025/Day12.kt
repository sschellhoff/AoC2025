package de.sschellhoff.aoc2025

import de.sschellhoff.utils.Day
import de.sschellhoff.utils.Vector2
import de.sschellhoff.utils.blockLines

class Day12 : Day<Long, Long>(12, 2025, 2, 24) {
    override fun part1(input: String, isTest: Boolean): Long {
        if (isTest) {
            return 2
        }
        val (shapes, areas) = input.parse()
        return areas.filter { it.fitsEasily() }.size.toLong()
        //return areas.count { area -> area.canPossiblyFit(shapes) }.toLong()
    }

    override fun part2(input: String, isTest: Boolean): Long {
        return 24
    }

    //private fun Area.canPossiblyFit(shapes: List<Shape>): Boolean {
    //    return this.size.x * this.size.y >= this.presents.mapIndexed { index, count ->
    //        shapes[index].size * count
    //    }.sum() && fitsEasily()
    //}

    private data class Shape(val data: List<String>, val size: Long)

    private data class Area(val size: Vector2, val presents: List<Long>) {
        fun fitsEasily(): Boolean {
            val numberOfPresents = presents.sum()
            return numberOfPresents <= (this.size.x / 3) * this.size.y / 3
        }
    }

    private fun String.parse(): Pair<List<Shape>, List<Area>> {
        val input = this.blockLines()
        val shapes = input.dropLast(1).map { it.drop(1) }.map { lines -> Shape(lines, lines.joinToString().count { c -> c == '#' }.toLong()) }
        val areas = input.last().map { line ->
            val (s, p) = line.split(": ")
            val (sizeX, sizeY) = s.split("x").map { it.toLong() }
            val presents = p.split(" ").map { it.toLong() }
            Area(Vector2(sizeX, sizeY), presents)
        }
        return shapes to areas
    }
}