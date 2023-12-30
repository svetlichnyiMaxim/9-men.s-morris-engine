class BoardPosition(val collection: MutableList<Position>)

class Position(val xyz: Triple<Int, Int, Int>, val piece: Piece)