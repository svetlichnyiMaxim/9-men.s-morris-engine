package com.example.mensmorris

/**
 * converts list to triple (only when it's size = 3)
 * @throws IllegalArgumentException when size != 3
 */
val <E> List<E>.toTriple: Triple<E, E, E>
    get() {
        require(size == 3)
        return Triple(this[0], this[1], this[2])
    }
