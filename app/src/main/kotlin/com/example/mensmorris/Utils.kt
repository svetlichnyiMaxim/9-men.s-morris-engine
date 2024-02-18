package com.example.mensmorris

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.mensmorris.ui.mainActivity

/**
 * converts list to triple (only when it's size = 3)
 * @throws IllegalArgumentException when size != 3
 */
val <E> List<E>.toTriple: Triple<E, E, E>
    get() {
        require(size == 3)
        return Triple(this[0], this[1], this[2])
    }

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
