package com.gillyobeast.solver.jumps

import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.builder.GraphBuilder
import kotlin.system.measureTimeMillis

class JumpsSolver(private val target: Int, private val initialSize: Int = target * 10) {

    fun run() {
        val (graph) = generateGraph()

        val pathFinder = DijkstraShortestPath(graph)

        val sp = pathFinder.getPath(0, target)

        println("Jumps from 0 to $target: ${sp?.length ?: "unknown"}")
    }

    fun generateGraph(): Pair<DefaultDirectedGraph<Int, DefaultEdge>,
            GraphBuilder<Int, DefaultEdge, DefaultDirectedGraph<Int, DefaultEdge>>> {
        val map = generateSequence()
//            .onEach (::println)
            .take(initialSize + 1)

        val graph = DefaultDirectedGraph<Int, DefaultEdge>(DefaultEdge::class.java)
        val builder = GraphBuilder(graph)

        map.forEach { it.addTo(builder) }

        return graph to builder


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
    measureTimeMillis {
        for (n in 1..10_000) {
            JumpsSolver(target = n).run()
        }
    }.also { println("${it}ms") }
}
