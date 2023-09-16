package main.kotlin

/*
    This file contains every function, connected with player-computer interactions
 */

fun interpretation(): Triple<PositionWithPlaceAble, piece, Int> {
    println("Enter search depth (4 takes a lot of time)")
    val depth = getSearchDepth()
    println("Depth is $depth now")
    println("Choose current move piece color (blue or green)")
    val color = getStartColor()
    println("Color is $color now")
    println("Enter position (24 ints (empty - empty, green - green, blue - blue), use the following wierd pattern, to lazy to fix it")
    emptyFull.display1()
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

fun getStartColor(): piece {
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
        return getStartColor()
    }
}

fun getPieceColor(): piece {
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
        return getPieceColor()
    }
}

fun getPosition(): BoardPosition {
    try {
        var list: BoardPosition = BoardPosition(mutableListOf<Position>())
        val listOfColors = mutableListOf<piece>()
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

// it is used for displayed board look better
fun processLength(piece1: Int) {
    val dif = 5 - piece1.toString().length
    print("${piece1}${" ".repeat(dif)} ")
}

// selects color for a piece
fun processColor(piece1: piece) {
    val blue = "\u001b[34m"
    val nothing = "\u001b[90m"
    val green = "\u001b[32m"
    var else1 = ""
    else1 += when (piece1) {
        piece.BLUE -> {
            blue
        }

        piece.GREEN -> {
            green
        }

        else -> {
            nothing
        }
    }
    val postfix = if (piece1 == piece.BLUE) " " else ""
    print("$else1${piece1.name}$postfix ")
}