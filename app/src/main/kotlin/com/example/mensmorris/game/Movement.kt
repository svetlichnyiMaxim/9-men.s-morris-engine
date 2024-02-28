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
        if (endIndex != null) {
            // this happens either when we move a piece or place it
            copy.positions[endIndex].isGreen = copy.pieceToMove
            copy.removalCount = copy.removalAmount(this)
        } else {
            // this happens only when we remove smth
            copy.removalCount--
            if (copy.positions[startIndex!!].isGreen!!) {
                copy.greenPiecesAmount--
            } else {
                copy.bluePiecesAmount--
            }
        }
        if (startIndex == null) {
            // this happens when we place smth
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
            copy.positions[startIndex].isGreen = null
        }
        if (copy.removalCount == 0.toUByte()) {
            copy.pieceToMove = !copy.pieceToMove
        }
        return copy
    }

    override fun equals(other: Any?): Boolean {
        if (other is Movement) {
            return this.startIndex == other.startIndex && this.endIndex == other.endIndex
        }
        return super.equals(other)
    }

    override fun toString(): String {
        return "Movement($startIndex, $endIndex)"
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
