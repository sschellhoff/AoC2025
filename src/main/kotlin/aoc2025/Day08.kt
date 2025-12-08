package aoc2025

import de.sschellhoff.utils.Day
import de.sschellhoff.utils.Day.RunMode
import de.sschellhoff.utils.product
import utils.Vector3i
import utils.euclideanDistance
import utils.mapEachPairIndexed

fun main() {
    Day08().run(RunMode.BOTH)
}

class Day08 : Day<Long, Long>(8, 2025, 40, 25272) {
    override fun part1(input: String, isTest: Boolean): Long {
        val numberOfConnections = if (isTest) 10 else 1000
        val nodes = input.parse()
        val edges = nodes.edges().sortedBy { it.cost }
        val connections = mutableMapOf<Int, Set<Int>>()
        edges.take(numberOfConnections).forEach { edge ->
            connections.insert(edge)
        }

        val t = connections.values.toSet().sortedBy { it.size }
        val b = t.takeLast(3)
        val r = b.map { it.size }.product()
        return r
    }

    override fun part2(input: String, isTest: Boolean): Long {
        val nodes = input.parse()
        val edges = nodes.edges().sortedBy { it.cost }
        val connections = mutableMapOf<Int, Set<Int>>()
        edges.forEach { edge ->
            connections.insert(edge)
            if (connections.values.toSet().size == 1 && connections.values.first().size == nodes.size) {
                val a = nodes[edge.a]
                val b = nodes[edge.b]
                return a.x.toLong() * b.x.toLong()
            }
        }
        throw IllegalStateException()
    }

    private fun String.parse(): List<Vector3i> = this.lines().map {
        it.split(",").let { (x, y, z) ->
            Vector3i(x.toInt(), y.toInt(), z.toInt())
        }
    }

    private fun List<Vector3i>.edges(): List<Edge> = this.mapEachPairIndexed { indexA, a, indexB, b ->
        Edge(indexA, indexB, a.euclideanDistance(b))
    }

    private fun MutableMap<Int, Set<Int>>.insert(edge: Edge) {
        val connectedWithA = this.getOrDefault(edge.a, setOf(edge.a))
        val connectedWithB = this.getOrDefault(edge.b, setOf(edge.b))
        val union = connectedWithA.union(connectedWithB)
        union.forEach { n ->
            this[n] = union
        }
    }

    private data class Edge(val a: Int, val b: Int, val cost: Double)
}