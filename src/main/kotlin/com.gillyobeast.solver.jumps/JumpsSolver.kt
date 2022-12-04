package com.gillyobeast.solver.jumps

import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.builder.GraphBuilder

class JumpsSolver(private val jumps: Int, private val target: Int = jumps) {

    fun run() {
        val graph: DefaultDirectedGraph<Int, DefaultEdge> =
            generateGraph()

//        graph.vertexSet().sorted().forEach(::println)
//        graph.edgeSet().forEach(::println)

    }

    fun generateGraph(): DefaultDirectedGraph<Int, DefaultEdge> {
        val map = generateSequence()
            .take(jumps + 1)
        //        map.forEach(::println)

        val graph: DefaultDirectedGraph<Int, DefaultEdge> =
            DefaultDirectedGraph(DefaultEdge::class.java)
        val builder = GraphBuilder(graph)

        map.forEach { it.addTo(builder) }
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

fun main() {
    JumpsSolver(jumps = 100, target = 150).run()
}
