package com.gillyobeast.solver.jumps

import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.builder.GraphBuilder

class JumpsSolver(private val jumps: Int) {
    fun run() {
        val map = generateSequence()
            .take(jumps)

        val graph: DefaultDirectedGraph<Int, DefaultEdge> =
            DefaultDirectedGraph(DefaultEdge::class.java)

        val builder = GraphBuilder(graph)
        map.forEach { builder.addJumps(it.first, it.second) }
        graph.vertexSet().sorted().forEach(::println)

    }

    private fun GraphBuilder<Int, DefaultEdge, DefaultDirectedGraph<Int, DefaultEdge>>.addJumps(
        n: Int,
        jump: Int
    ) {
        addEdge(n, n + jump)
        if (n > jump) {
            addEdge(n, n - jump)
        }
    }

    private fun generateSequence() =
        generateSequence(1 to 1, this::nextPair)

    private fun nextPair(it: Pair<Int, Int>): Pair<Int, Int> {
        val next = it.first + 1
        return next to (next + 1) / 2
    }
}

fun main() {
    JumpsSolver(100).run()
}
