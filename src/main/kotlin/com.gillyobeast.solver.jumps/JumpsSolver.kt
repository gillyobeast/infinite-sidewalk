package com.gillyobeast.solver.jumps

import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.builder.GraphBuilder
import kotlin.system.measureTimeMillis

class JumpsSolver(private val initialSize: Int) {

    private val graph = generateGraph()
    private val pathFinder = DijkstraShortestPath(graph)

    fun run(target: Int): Int? {
        return pathFinder.getPath(0, target)?.length
    }

    fun generateGraph(): DefaultDirectedGraph<Int, DefaultEdge> {
        val seq = generateSequence()
//            .onEach (::println)
            .take(initialSize + 1)

        val graph = DefaultDirectedGraph<Int, DefaultEdge>(DefaultEdge::class.java)
        val builder = GraphBuilder(graph)

        seq.forEach { it.addTo(builder) }

        return graph


    }

    private fun Pair<Int, Int>.addTo(
        builder: GraphBuilder<Int, DefaultEdge, DefaultDirectedGraph<Int, DefaultEdge>>
    ) {
        val (n, jump) = this
        builder.addEdge(n, n + jump)
        if (n > jump)
            builder.addEdge(n, n - jump)
    }

    private fun generateSequence() =
        generateSequence(0 to 1, this::nextPair)

    private fun nextPair(it: Pair<Int, Int>): Pair<Int, Int> {
        val next = it.first + 1
        return next to (next / 2) + 1
    }
}

private operator fun Pair<Int, Int>.inc(): Pair<Int, Int> = this.first + 1 to this.second + 1

fun main() {
    {
        val runs = 1000
        val start = 100 * runs
        val solver = JumpsSolver(initialSize = 10 * start)
        for (n in start.rangeTo(start + runs)) {
            {
                val sp = solver.run(target = n)
                print("Jumps from 0 to $n: ${sp ?: "unknown"}")
            }.printTime()
        }
    }.printTime()
}

private fun (() -> Unit).printTime() {
    measureTimeMillis(this).also { println(" - took ${it}ms") }
}
