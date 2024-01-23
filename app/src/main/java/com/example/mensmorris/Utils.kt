package com.example.mensmorris

val <E> List<E>.toTriple: Triple<E, E, E>
    get() {
        require(size == 3)
        return Triple(this[0], this[1], this[2])
    }
