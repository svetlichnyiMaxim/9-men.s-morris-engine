const val version: Double = 1.0

val emptyList = BoardPosition(
    mutableListOf(
        Position(Triple(0, 0, 0), piece.EMPTY),
        Position(Triple(0, 0, 1), piece.EMPTY),
        Position(Triple(0, 0, 2), piece.EMPTY),
        Position(Triple(0, 1, 0), piece.EMPTY),
        Position(Triple(0, 1, 1), piece.EMPTY),
        Position(Triple(0, 1, 2), piece.EMPTY),
        Position(Triple(0, 2, 0), piece.EMPTY),
        Position(Triple(0, 2, 1), piece.EMPTY),
        Position(Triple(0, 2, 2), piece.EMPTY),
        Position(Triple(1, 0, 0), piece.EMPTY),
        Position(Triple(1, 0, 1), piece.NOT),
        Position(Triple(1, 0, 2), piece.EMPTY),
        Position(Triple(1, 1, 0), piece.EMPTY),
        Position(Triple(1, 1, 1), piece.NOT),
        Position(Triple(1, 1, 2), piece.EMPTY),
        Position(Triple(1, 2, 0), piece.EMPTY),
        Position(Triple(1, 2, 1), piece.NOT),
        Position(Triple(1, 2, 2), piece.EMPTY),
        Position(Triple(2, 0, 0), piece.EMPTY),
        Position(Triple(2, 0, 1), piece.EMPTY),
        Position(Triple(2, 0, 2), piece.EMPTY),
        Position(Triple(2, 1, 0), piece.EMPTY),
        Position(Triple(2, 1, 1), piece.EMPTY),
        Position(Triple(2, 1, 2), piece.EMPTY),
        Position(Triple(2, 2, 0), piece.EMPTY),
        Position(Triple(2, 2, 1), piece.EMPTY),
        Position(Triple(2, 2, 2), piece.EMPTY)
    )
)
val emptyFull = PositionWithPlaceAble(Pair(0, 0), emptyList)

fun main() {
    println("Starting engine, version - $version")
    val data = interpritate()
    val currentPos = data.first

    println("NOTE: if advantage is positive - blue are winning")
    val depth = data.third
    val miniMax = minimax(depth, data.second, Node(currentPos), depth)
    println(miniMax.first)
    miniMax.third!!.display()
    //miniMax.second.print2()
}

fun interpritate(): Triple<PositionWithPlaceAble, piece, Int> {
    println("Enter search depth (4 takes a lot of time)")
    val depth = getDepth()
    println("Depth is $depth now")
    println("Choose current move piece color (blue or green)")
    val color = getColor()
    println("Color is $color now")
    println("Enter position (24 ints (empty - empty, green - green, blue - blue), use the following wierd pattern, to lazy to fix it")
    emptyFull.display1()
    val position = getPosition()
    PositionWithPlaceAble(Pair(0, 0), position).display()
    println("Got position")
    println("Enter available green pieces")
    val placeAbleGreen = getPlaceAble()
    println("Enter available blue pieces")
    val placeAbleBlue = getPlaceAble()
    println("Got available pieces (green - $placeAbleGreen, blue - $placeAbleBlue)")
    return Triple(PositionWithPlaceAble(Pair(placeAbleBlue, placeAbleGreen), position), color, depth)
}

fun getDepth(): Int {
    try {
        return readln().toInt()
    } catch (test: IllegalArgumentException) {
        println("error, it isn't a number")
        return getDepth()
    }
}

fun getPlaceAble(): Int {
    try {
        return readln().toInt()
    } catch (test: IllegalArgumentException) {
        println("error, it isn't a number")
        return getDepth()
    }
}

fun getColor(): piece {
    try {
        return when (readln().lowercase()) {
            "blue" -> {
                piece.BLUE
            }

            "green" -> {
                piece.GREEN
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
    } catch (test: IllegalArgumentException) {
        println("error, it isn't a walid string")
        return getColor()
    }
}

fun getColor1(): piece {
    val depthString = readln()
    try {
        when (depthString.lowercase()) {
            "blue" -> {
                return piece.BLUE
            }

            "green" -> {
                return piece.GREEN
            }

            "empty" -> {
                return piece.EMPTY
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
    } catch (test: IllegalArgumentException) {
        println("error, it isn't a walid string")
        return getColor1()
    }
}

fun getPosition(): BoardPosition {
    try {
        var list: BoardPosition = BoardPosition(mutableListOf<Position>())
        val listOfColors = mutableListOf<piece>()
        for (i in 1..24) {
            listOfColors.add(getColor1())
        }
        var lastUsedPiece = 0
        for (x in 0..2) {
            for (y in 0..2) {
                for (z in 0..2) {
                    if (x != 1 || z != 1) {
                        list.collection.add(Position(Triple(x, y, z), listOfColors[lastUsedPiece]))
                        lastUsedPiece++
                    } else {
                        list.collection.add(Position(Triple(x, y, z), piece.NOT))
                    }
                }
            }
        }
        return list
    } catch (test: IllegalArgumentException) {
        println("error, it isn't a number")
        return getPosition()
    }
}

enum class piece {
    GREEN, BLUE, EMPTY, NOT
}