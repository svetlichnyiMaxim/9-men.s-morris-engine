package com.example.mensmorris.common

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.mensmorris.mainActivity

/**
 * provides a quicker way for setting current windows
 * uses mainActivity
 * @param function our composable function we want to render (usually we set screens)
 */
fun render(function: @Composable () -> Unit) {
    mainActivity.setContent {
        function()
    }
}


/**
 * provides a way to get an element from pair
 * fancy way
 * @param T any type
 * @param index index of the required element
 */
operator fun <T> Triple<T, T, T>.get(index: Int): T {
    return when (index) {
        0 -> {
            first
        }

        1 -> {
            second
        }

        2 -> {
            third
        }

        else -> {
            throw IllegalArgumentException("Illegal index when getting triple element")
        }
    }
}

/**
 * converts all movements to positions
 */
@Suppress("unused")
fun List<Movement>.toPositions(startPos: Position): List<Position> {
    if (this.isEmpty()) {
        return mutableListOf()
    }
    var startPosCopy = startPos
    val result: MutableList<Position> = mutableListOf(startPos)
    this@toPositions.asReversed().forEach {
        startPosCopy = it.producePosition(startPosCopy)
        result.add(startPosCopy)
    }
    return result
}


/**
 * provides a quick way to display all movements as positions
 */
@Suppress("unused")
fun MutableList<Movement>.displayAsPositions(startPos: Position) {
    var startPosCopy = startPos
    val result: MutableList<Position> = mutableListOf(startPos)
    this@displayAsPositions.asReversed().forEach {
        startPosCopy = it.producePosition(startPosCopy)
        result.add(startPosCopy)
    }
    result.forEach {
        println(it.freePieces)
        println(it.removalCount)
        println(it.evaluate())
        println("${it.bluePiecesAmount} ${it.greenPiecesAmount}")
        it.display()
    }
    println()
    println("finished output")
    println()
}
