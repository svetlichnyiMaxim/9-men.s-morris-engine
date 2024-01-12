import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Suppress("IncorrectFormatting")
    private val examplePosition1v0 = Position(
        mutableListOf(
            Piece.BLUE_,                           Piece.EMPTY,                           Piece.EMPTY,
                         Piece.EMPTY,              Piece.GREEN,              Piece.EMPTY,
                                      Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
            Piece.BLUE_, Piece.EMPTY, Piece.GREEN,              Piece.EMPTY, Piece.EMPTY, Piece.BLUE_,
                                      Piece.EMPTY, Piece.GREEN, Piece.EMPTY,
                         Piece.EMPTY,              Piece.EMPTY,              Piece.EMPTY,
            Piece.EMPTY,                           Piece.BLUE_,                           Piece.EMPTY
        ),
        pieceToMove = Piece.BLUE_
    )

    @Suppress("IncorrectFormatting")
    private val examplePosition1v1 = Position(
        mutableListOf(
            Piece.BLUE_,                           Piece.EMPTY,                           Piece.EMPTY,
                         Piece.EMPTY,              Piece.GREEN,              Piece.EMPTY,
                                      Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
            Piece.BLUE_, Piece.EMPTY, Piece.GREEN,              Piece.EMPTY, Piece.EMPTY, Piece.BLUE_,
                                      Piece.EMPTY, Piece.GREEN, Piece.EMPTY,
                         Piece.EMPTY,              Piece.EMPTY,              Piece.EMPTY,
            Piece.BLUE_,                           Piece.EMPTY,                           Piece.EMPTY
        ),
        pieceToMove = Piece.GREEN
    )

    @Test
    fun test1() {
        val result = examplePosition1v0.solve(1)
        assertEquals(result.first, Pair(Int.MIN_VALUE, Int.MAX_VALUE))
        assertEquals(result.second.size, 2)
        assertEquals(result.second[1], examplePosition1v0)
        assertEquals(result.second[0].positions[1U].sortedBy { it }, examplePosition1v1.positions[1U].sortedBy { it })
        assertEquals(result.second[0].freePieces, examplePosition1v1.freePieces)
        assertEquals(result.second[0].pieceToMove, examplePosition1v1.pieceToMove)
    }

    @Suppress("IncorrectFormatting")
    private val examplePosition2v0 = Position(
        mutableListOf(
            Piece.BLUE_,                           Piece.EMPTY,                           Piece.EMPTY,
                         Piece.EMPTY,              Piece.EMPTY,              Piece.EMPTY,
                                      Piece.GREEN, Piece.EMPTY, Piece.EMPTY,
            Piece.BLUE_, Piece.GREEN, Piece.EMPTY,              Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
                                      Piece.GREEN, Piece.EMPTY, Piece.EMPTY,
                         Piece.EMPTY,              Piece.EMPTY,              Piece.EMPTY,
            Piece.EMPTY,                           Piece.BLUE_,                           Piece.EMPTY
        ),
        pieceToMove = Piece.GREEN
    )

    @Suppress("IncorrectFormatting")
    private val examplePosition2v1 = Position(
        mutableListOf(
            Piece.BLUE_,                           Piece.EMPTY,                           Piece.EMPTY,
                         Piece.EMPTY,              Piece.EMPTY,              Piece.EMPTY,
                                      Piece.GREEN, Piece.EMPTY, Piece.EMPTY,
            Piece.BLUE_, Piece.EMPTY, Piece.GREEN,              Piece.EMPTY, Piece.EMPTY, Piece.BLUE_,
                                      Piece.GREEN, Piece.EMPTY, Piece.EMPTY,
                         Piece.EMPTY,              Piece.EMPTY,              Piece.EMPTY,
            Piece.EMPTY,                           Piece.BLUE_,                           Piece.EMPTY
        ),
        pieceToMove = Piece.BLUE_
    )

    @Test
    fun test2() {
        val result = examplePosition2v0.solve(1)
        assertEquals(result.first, Pair(Int.MAX_VALUE, Int.MIN_VALUE))
        assertEquals(result.second.size, 2)
        assertEquals(result.second[1], examplePosition2v0)
        assertEquals(result.second[0].positions[0U].sortedBy { it }, examplePosition2v1.positions[0U].sortedBy { it })
        assertEquals(result.second[0].freePieces, examplePosition2v1.freePieces)
        assertEquals(result.second[0].pieceToMove, examplePosition2v1.pieceToMove)
    }

    @Suppress("IncorrectFormatting")
    private val examplePosition3v0 = Position(
        mutableListOf(
            Piece.BLUE_,                           Piece.EMPTY,                           Piece.EMPTY,
                         Piece.EMPTY,              Piece.EMPTY,              Piece.EMPTY,
                                      Piece.GREEN, Piece.GREEN, Piece.EMPTY,
            Piece.BLUE_, Piece.GREEN, Piece.EMPTY,              Piece.GREEN, Piece.EMPTY, Piece.EMPTY,
                                      Piece.GREEN, Piece.EMPTY, Piece.EMPTY,
                         Piece.EMPTY,              Piece.EMPTY,              Piece.EMPTY,
            Piece.EMPTY,                           Piece.BLUE_,                           Piece.EMPTY
        ),
        pieceToMove = Piece.BLUE_
    )

    @Suppress("IncorrectFormatting")
    private val examplePosition3v1 = Position(
        mutableListOf(
            Piece.BLUE_,                           Piece.EMPTY,                           Piece.EMPTY,
                         Piece.EMPTY,              Piece.EMPTY,              Piece.EMPTY,
                                      Piece.EMPTY, Piece.GREEN, Piece.EMPTY,
            Piece.BLUE_, Piece.GREEN, Piece.EMPTY,              Piece.GREEN, Piece.EMPTY, Piece.EMPTY,
                                      Piece.GREEN, Piece.EMPTY, Piece.EMPTY,
                         Piece.EMPTY,              Piece.EMPTY,              Piece.EMPTY,
            Piece.BLUE_,                           Piece.EMPTY,                           Piece.EMPTY
        ),
        pieceToMove = Piece.GREEN
    )

    @Test
    fun test3() {
        val result = examplePosition3v0.solve(2)
        assertEquals(result.first, Pair(1, -1))
        assertEquals(result.second.size, 3)
        assertEquals(result.second[2], examplePosition3v0)
        assertEquals(result.second[1].positions[1U].sortedBy { it }, examplePosition3v1.positions[1U].sortedBy { it })
        assertEquals(result.second[1].freePieces, examplePosition3v1.freePieces)
        assertEquals(result.second[1].pieceToMove, examplePosition3v1.pieceToMove)
    }
}
