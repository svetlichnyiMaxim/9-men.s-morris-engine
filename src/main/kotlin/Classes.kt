package main.kotlin

class BoardPosition(val collection: MutableList<Position>)

class Position(val xyz: Triple<Int, Int, Int>, val piece: piece)

class PositionWithPlaceAble(var placeAble: Pair<Int, Int>, val pos: BoardPosition)