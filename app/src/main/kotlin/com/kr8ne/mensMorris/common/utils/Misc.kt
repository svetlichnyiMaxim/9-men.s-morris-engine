package com.kr8ne.mensMorris.common.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kr8ne.mensMorris.common.game.Movement
import com.kr8ne.mensMorris.common.game.Position
import com.kr8ne.mensMorris.common.game.utils.gameStartPosition
import kotlin.random.Random

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

/**
 * adds a basic background
 * @param function everything ui-related that happens inside of the app
 */
@Composable
inline fun AppTheme(function: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7E7E7E))
    ) {
        function()
    }
}

/**
 * used for providing pos to game end screen
 * TODO: remove this
 */
var positionToNuke: Position = gameStartPosition
