package main.kotlin

infix operator fun Triple<Int, Int, Int>.plus(second: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
    return Triple(this.first + second.first, this.second + second.second, this.third + second.third)
}

infix operator fun Triple<Int, Int, Int>.minus(second: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
    return Triple(this.first - second.first, this.second - second.second, this.third - second.third)
}

fun piece.oppositeColor(): piece {
    return when (this) {
        piece.GREEN -> {
            piece.BLUE
        }

        piece.BLUE -> {
            piece.GREEN
        }

        else -> {
            piece.EMPTY
        }
    }
}