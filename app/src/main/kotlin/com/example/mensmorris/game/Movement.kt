package com.example.mensmorris.game

/**
 * used to store movement
 * @param startIndex index of place a piece moves from
 * @param endIndex index of place a piece moves to
 */
class Movement(val startIndex: Int?, val endIndex: Int?) {
    /**
     * @param pos position we have a more for
     * @return position after specified move
     */
    fun producePosition(pos: Position): Position {
        val copy = pos.copy()
        if (startIndex == null) {
            when (copy.pieceToMove) {
                true -> {
                    copy.freePieces =
                        Pair((copy.freePieces.first - 1u).toUByte(), copy.freePieces.second)
                }

                false -> {
                    copy.freePieces =
                        Pair(copy.freePieces.first, (copy.freePieces.second - 1u).toUByte())
                }
            }
        } else {
            if (copy.positions[startIndex].isGreen!!) {
                copy.greenPiecesAmount--
            } else {
                copy.bluePiecesAmount--
            }
            copy.positions[startIndex].isGreen = null
            copy.removalCount--
        }
        if (endIndex != null) {
            copy.positions[endIndex].isGreen = copy.pieceToMove
            copy.removalCount = copy.removalAmount(this)
        }
        if (copy.removalCount == 0.toUByte()) {
            copy.pieceToMove = !copy.pieceToMove
        }
        return copy
    }
}

/*
0-----------------1-----------------2
|                 |                 |
|     3-----------4-----------5     |
|     |           |           |     |
|     |     6-----7-----8     |     |
|     |     |           |     |     |
9-----10----11          12----13----14
|     |     |           |     |     |
|     |     15----16----17    |     |
|     |           |           |     |
|     18----------19----------20    |
|                 |                 |
21----------------22----------------23
 */
