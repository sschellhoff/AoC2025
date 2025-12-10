package aoc2025

import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import de.sschellhoff.utils.Day
import de.sschellhoff.utils.pathfinding.EdgeInfo
import de.sschellhoff.utils.pathfinding.dijkstra

fun main() {
    //solveIt()
    Day10().run(Day.RunMode.BOTH)
}

class Day10 : Day<Long, Long>(10, 2025, 7, 33) {
    override fun part1(input: String, isTest: Boolean): Long {
        val machines = input.parse()
        return machines.sumOf { it.solve()?.second!! }
    }

    override fun part2(input: String, isTest: Boolean): Long {
        val machines = input.parse()
        return machines.sumOf { it.solve2()?.second!! }
    }

    private fun Machine.solve(): Pair<List<Int>, Long>? {
        return dijkstra(0, { it == this.target }) { state -> this.buttons.map { EdgeInfo(state, it xor state, 1) } }
    }
    private fun Machine.solve2(): Pair<List<List<Int>>, Long>? {
        return dijkstra(List(this.joltages.size) { 0 }, { it == this.joltages }) { state ->
            equations.mapNotNull { buttons ->
                state.transition(buttons, this.joltages)
            }
        }
    }
    //private fun Machine.solve2(): Long {
    //    Loader.loadNativeLibraries()
    //    val solver = MPSolver.createSolver("SCIP")
    //    val c = joltages.zip(equations)
    //    val numVars = equations.first().size
    //    val m = joltages.max()
    //    val bs = List(numVars) {
    //        solver.makeIntVar(0.0, m.toDouble(), "b$it")
    //    }
    //    c.forEach { (t, e) ->
    //        solver.makeConstraint(t.toDouble(), t.toDouble()).apply {
    //            bs.forEachIndexed { i, b ->
    //                setCoefficient(b, e[i].toDouble())
    //            }
    //        }
    //    }
    //    val objective = solver.objective()
    //    bs.forEach { b ->
    //        objective.setCoefficient(b, 1.0)
    //    }
    //    objective.setMinimization()
    //    val status = solver.solve()
    //    if(status == MPSolver.ResultStatus.OPTIMAL) {
    //        return objective.value().toLong()
    //    }
    //    throw IllegalStateException()
    //}

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
        return Machine(target, buttons, buttonsList /*.asEquation(joltages.size)*/, joltages)
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

    fun List<Int>.transition(indices: List<Int>, target: List<Int>): EdgeInfo<List<Int>>? {
        val result = mutableListOf<Int>()
        this.forEachIndexed { index, value ->
            val newValue = if (index in indices) value + 1 else value
            if (newValue > target[index]) {
                return null
            }
            result.add(newValue)
        }
        return EdgeInfo(this, result, 1)
    }
}

private fun solveIt() {
    val ts = listOf(3, 5, 4, 7)
    val es = listOf(
        listOf(0, 0, 0, 0, 1, 1),
        listOf(0, 1, 0, 0, 0, 1),
        listOf(0, 0, 1, 1, 1, 0),
        listOf(1, 1, 0, 1, 0, 0),
    )
    val c = ts.zip(es)
    val numVars = es.first().size
    Loader.loadNativeLibraries()
    val solver = MPSolver.createSolver("SCIP")
    val m = ts.max()
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
    println(status)
    if(status == MPSolver.ResultStatus.OPTIMAL) {
        println(objective.value())
    }
    //val b0 = solver.makeIntVar(0.0, 7.0, "b0")
    //val b1 = solver.makeIntVar(0.0, 7.0, "b1")
    //val b2 = solver.makeIntVar(0.0, 7.0, "b2")
    //val b3 = solver.makeIntVar(0.0, 7.0, "b3")
    //val b4 = solver.makeIntVar(0.0, 7.0, "b4")
    //val b5 = solver.makeIntVar(0.0, 7.0, "b5")
    //solver.makeConstraint(3.0, 3.0).apply {
    //    setCoefficient(b0, 0.0)
    //    setCoefficient(b1, 0.0)
    //    setCoefficient(b2, 0.0)
    //    setCoefficient(b3, 0.0)
    //    setCoefficient(b4, 1.0)
    //    setCoefficient(b5, 1.0)
    //}
}