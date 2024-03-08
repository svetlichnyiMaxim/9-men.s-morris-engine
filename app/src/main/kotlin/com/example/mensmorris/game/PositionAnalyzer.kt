/*
package com.example.mensmorris.game

import java.util.PriorityQueue

fun Position.solver(
    depth: UByte
): Pair<Pair<Int, Int>, MutableList<Movement>?> {
    if (depth == 0.toUByte() || gameEnded()) {
        return Pair(evaluate(depth), mutableListOf())
    }
    val comparator: Comparator<Triple<UByte, Position, MutableList<Movement>>> = compareBy { it.first }
    val bestLine = listOf<Movement>()
    var bestEvaluation = Int.MIN_VALUE
    val priorityQueue: PriorityQueue<Triple<UByte, Position, MutableList<Movement>>> = PriorityQueue(comparator)
    priorityQueue.add(Triple(depth, this, mutableListOf()))
    while (priorityQueue.isNotEmpty()) {
        priorityQueue.first().let { pos ->
            if (pos.first == 0) {
                return evaluate()
            }
            // for all possible positions, we try to solve them
            val positions = generateMoves(depth).map {
                val moves = pos.third
                moves.add(it)
                val result = it.producePosition(this)
                Triple((depth - 1u).toUByte(), result, moves)
            }
            priorityQueue.addAll(positions)
        }
    }
    // for all possible positions, we try to solve them
    val positions = (generateMoves(depth).map {
        val result = it.producePosition(this).solve((depth - 1u).toUByte())
        if (result.second != null) {
            result.apply { this.second!!.add(it) }
        }
        result
    }.filter { it.second != null })
    if (positions.isEmpty()) {
        // if we can't make a move, we lose
        return Pair(
            if (!pieceToMove) {
                Pair(LOST_GAME_COST, Int.MAX_VALUE)
            } else {
                Pair(Int.MAX_VALUE, LOST_GAME_COST)
            }, null
        )
    }
    return positions.maxBy {
        it.first[pieceToMove]
    }
}*/
