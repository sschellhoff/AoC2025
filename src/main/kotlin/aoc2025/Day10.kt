package de.sschellhoff.aoc2025

import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import de.sschellhoff.utils.Day
import de.sschellhoff.utils.pathfinding.EdgeInfo
import de.sschellhoff.utils.pathfinding.dijkstra

class Day10 : Day<Long, Long>(10, 2025, 7, 33) {
    override fun part1(input: String, isTest: Boolean): Long {
        val machines = input.parse()
        return machines.sumOf { it.solve()?.second!! }
    }

    override fun part2(input: String, isTest: Boolean): Long {
        val machines = input.parse()
        return machines.sumOf { it.solve2() }
    }

    private fun Machine.solve(): Pair<List<Int>, Long>? {
        return dijkstra(0, { it == this.target }) { state -> this.buttons.map { EdgeInfo(state, it xor state, 1) } }
    }

    private fun Machine.solve2(): Long {
        Loader.loadNativeLibraries()
        val solver = MPSolver.createSolver("BOP")
        val c = joltages.zip(equations)
        val numVars = equations.first().size
        val m = joltages.max()
        val bs = List(numVars) {
            solver.makeIntVar(0.0, m.toDouble(), "b$it")
        }
        c.forEach { (t, e) ->
            solver.makeConstraint(t.toDouble(), t.toDouble()).apply {
                bs.forEachIndexed { i, b ->
                    setCoefficient(b, e[i].toDouble())
                }
            }
        }
        val objective = solver.objective()
        bs.forEach { b ->
            objective.setCoefficient(b, 1.0)
        }
        objective.setMinimization()
        val status = solver.solve()
        if(status == MPSolver.ResultStatus.OPTIMAL) {
            return objective.value().toLong()
        }
        throw IllegalStateException()
    }

    private fun String.parse(): List<Machine> = this.lines().map { it.parseMachine() }

    private fun String.parseMachine(): Machine {
        val target = this.split("]").first().drop(1).mapIndexedNotNull { index, ch -> if (ch == '#') index else null }.asBits()
        val joltages = this.split("{").last().dropLast(1).split(",").map { it.toInt() }
        val buttonsList = "\\([0-9,]*\\)".toRegex().findAll(this).map {
            it.value.let { match ->
                match.substring(1, match.length - 1).split(",").map { num -> num.toInt() }
            }
        }.toList()
        val buttons = buttonsList.map { it.asBits() }.toList()
        return Machine(target, buttons, buttonsList.asEquation(joltages.size), joltages)
    }

    private data class Machine(val target: Int, val buttons: List<Int>, val equations: List<List<Int>>, val joltages: List<Int>)

    private fun List<Int>.asBits(): Int {
        var result = 0
        for (index in this) {
            result = result or (1 shl index)
        }
        return result
    }

    private fun List<List<Int>>.asEquation(numberOfEquations: Int): List<List<Int>> = List(numberOfEquations) { equation ->
        this.map { value ->
            if (this.find { equation in value } != null) 1 else 0
        }
    }
}