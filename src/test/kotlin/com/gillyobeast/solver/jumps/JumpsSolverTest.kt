package com.gillyobeast.solver.jumps

import org.junit.jupiter.api.Test

class JumpsSolverTest {
    private val edgesNotToMatch =
        listOf(102, 105, 108, 111, 114, 117, 120, 123, 126, 129, 132, 135, 138, 141, 144, 147, 150)

    @Test
    fun `generateGraph() returns the right vertices`() {
        val graph = JumpsSolver(jumps = 100).generateGraph()

        for (n: Int in 0..150) {
            if (n in edgesNotToMatch)
                assert(!graph.containsVertex(n)) { "contained $n" }
            else
                assert(graph.containsVertex(n)) { "didn't contain $n" }
        }
    }
}
