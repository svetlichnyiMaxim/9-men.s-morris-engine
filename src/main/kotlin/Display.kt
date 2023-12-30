/**
 * This file contains every function, connected with player-computer interactions
 */

fun startingHint(): Triple<PositionWithPlaceAble, Piece, Int> {
    println("Enter search depth (4 takes a lot of time)")
    val depth = getSearchDepth()
    println("Depth is $depth now")
    println("Choose current move piece color (blue or green)")
    val color = getStartColor()
    println("Color is $color now")
    println("Enter position (24 inputs (empty/green/blue), use the following wierd pattern, to lazy to fix it")
    emptyFull.hint()
    val position = getPosition()
    PositionWithPlaceAble(Pair(0, 0), position).display()
    println("Got position")
    println("Enter available blue pieces")
    val placeAbleBlue = getPlaceAblePieces()
    println("Enter available green pieces")
    val placeAbleGreen = getPlaceAblePieces()
    println("Got available pieces (green - $placeAbleGreen, blue - $placeAbleBlue)")
    return Triple(PositionWithPlaceAble(Pair(placeAbleBlue, placeAbleGreen), position), color, depth)
}

fun getSearchDepth(): Int {
    try {
        return readln().toInt()
    } catch (test: IllegalArgumentException) {
        println("error, it isn't a number")
        return getSearchDepth()
    }
}

fun getPlaceAblePieces(): Int {
    try {
        return readln().toInt()
    } catch (test: IllegalArgumentException) {
        println("error, it isn't a number")
        return getSearchDepth()
    }
}

fun getStartColor(): Piece {
    try {
        return when (readln().lowercase()) {
            "blue" -> {
                Piece.BLUE
            }

            "green" -> {
                Piece.GREEN
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
    } catch (test: IllegalArgumentException) {
        println("error, it isn't a walid string")
        return getStartColor()
    }
}

fun getPieceColor(): Piece {
    val depthString = readln()
    try {
        when (depthString.lowercase()) {
            "blue" -> {
                return Piece.BLUE
            }

            "green" -> {
                return Piece.GREEN
            }

            "empty" -> {
                return Piece.EMPTY
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
    } catch (test: IllegalArgumentException) {
        println("error, it isn't a valid string")
        return getPieceColor()
    }
}

fun getPosition(): BoardPosition {
    try {
        var list: BoardPosition = BoardPosition(mutableListOf<Position>())
        val listOfColors = mutableListOf<Piece>()
        for (i in 1..24) {
            listOfColors.add(getPieceColor())
        }
        var lastUsedPiece = 0
        for (x in 0..2) {
            for (y in 0..2) {
                for (z in 0..2) {
                    if (x != 1 || z != 1) {
                        list.collection.add(Position(Triple(x, y, z), listOfColors[lastUsedPiece]))
                        lastUsedPiece++
                    } else {
                        list.collection.add(Position(Triple(x, y, z), Piece.NOT))
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

/**
 * it is used to make displayed board look better
 */
fun processLength(piece1: Int) {
    val dif = 5 - piece1.toString().length
    print("${piece1}${" ".repeat(dif)} ")
}

/**
 * selects color for a piece
 */
fun processColor(piece1: Piece) {
    val blue = "\u001b[34m"
    val nothing = "\u001b[90m"
    val green = "\u001b[32m"
    var else1 = ""
    else1 += when (piece1) {
        Piece.BLUE -> {
            blue
        }

        Piece.GREEN -> {
            green
        }

        else -> {
            nothing
        }
    }
    val postfix = if (piece1 == Piece.BLUE) " " else ""
    print("$else1${piece1.name}$postfix ")
}