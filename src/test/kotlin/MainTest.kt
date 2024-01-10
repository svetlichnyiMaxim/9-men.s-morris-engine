import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainTest {
    @Suppress("IncorrectFormatting")
    private val examplePosition1 = Position(
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
    private val examplePosition1S = Position(
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
        //examplePosition.generatePositions(Piece.GREEN).forEach { it.display() }
        val result = examplePosition1.solve(1)
        assertEquals(result.first, Int.MIN_VALUE)
        assertEquals(result.second.size, 2)
        assertEquals(result.second[1], examplePosition1)
        assertEquals(result.second[0].positions[1].sortedBy { it }, examplePosition1S.positions[1].sortedBy { it })
        assertEquals(result.second[0].freePieces, examplePosition1S.freePieces)
        assertEquals(result.second[0].pieceToMove, examplePosition1S.pieceToMove)
    }

    @Suppress("IncorrectFormatting")
    private val examplePosition2 = Position(
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
    private val examplePosition2S = Position(
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
        val result = examplePosition2.solve(1)
        assertEquals(result.first, Int.MIN_VALUE)
        assertEquals(result.second.size, 2)
        assertEquals(result.second[1], examplePosition2)
        assertEquals(result.second[0].positions[0].sortedBy { it }, examplePosition2S.positions[0].sortedBy { it })
        assertEquals(result.second[0].freePieces, examplePosition2S.freePieces)
        assertEquals(result.second[0].pieceToMove, examplePosition2S.pieceToMove)
    }
}