import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import kotlin.test.assertTrue

typealias Player = String

typealias X = Player
typealias O = Player
typealias Empty = Player

typealias Cell = Pair<Int, Int>

typealias Board = List<List<Player?>>

const val x: X = "X"
const val o: O = "O"
const val empty: Empty = "Empty"

fun newBoard() = arrayListOf<ArrayList<Player?>>(
    arrayListOf(empty, empty, empty),
    arrayListOf(empty, empty, empty),
    arrayListOf(empty, empty, empty)
)

data class Game(
    var nextPlayer: Player = empty,
    var board: Board = newBoard()
)

fun new() = Game()

fun turn(game: Game, cell: Cell, player: Player): Game {
    val updatedGame = setCell(game, cell, player)
    updatedGame.nextPlayer = getNextPlayer(updatedGame.nextPlayer, player)
    return game
}

fun setCell(game: Game, cell: Cell, player: Player): Game {
    game.board.getOrNull(cell.first)?.let {
        (it as ArrayList).add(cell.second, player)
    }
    return game
}

fun getNextPlayer(lastPlayer: Player, currentPlayer: Player) = if (lastPlayer == empty) nextPlayer(currentPlayer) else nextPlayer(lastPlayer)
fun nextPlayer(player: Player) = if (player == x) o else x

class TestOxyOzys {

    private val game = Game()
    private val cell = 0 to 3
    private val player = x

    @Test
    fun `should return game with initial state`() {
        val result = new()

        assertThat(result, `is`(notNullValue()))
    }

    @Test
    fun `should have next player as null initially`() {
        val result = new()

        assertThat(result.nextPlayer, `is`(nullValue()))
    }

    @Test
    fun `should have empty game board initially`() {
        val result = new()

        assertThat(result.board.size, `is`(3))
        result.board.forEach {
            assertTrue {
                it.all { cell -> cell == null }
            }
        }
    }

    @Test
    fun `should update game board when player takes turn`() {
        val result = turn(game, cell, player)

        assertThat(result.board[cell.first][cell.second], `is`(player))
    }

    @Test
    fun `should set last player to be current player after first turn`() {
        val result = turn(game, cell, player)

        assertThat(result.nextPlayer, `is`(o))
    }
}