infix operator fun Triple<Int, Int, Int>.plus(second: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
    return Triple(this.first + second.first, this.second + second.second, this.third + second.third)
}

infix operator fun Triple<Int, Int, Int>.minus(second: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
    return Triple(this.first - second.first, this.second - second.second, this.third - second.third)
}

fun Piece.oppositeColor(): Piece {
    return when (this) {
        Piece.GREEN -> {
            Piece.BLUE
        }

        Piece.BLUE -> {
            Piece.GREEN
        }

        else -> {
            Piece.EMPTY
        }
    }
}