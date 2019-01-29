package games.gameOfFifteen

import board.Cell
import board.Direction
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val permutation = initializer.initialPermutation
        var k = 0
        for (i in 1..board.width) {
            for (j in 1..board.width) {
                board[board.getCell(i, j)] = if (k < permutation.size) permutation[k++] else null
            }
        }
    }

    override fun canMove() = true

    override fun hasWon(): Boolean {
        var won = true
        var k = 1
        for (i in 1..board.width) {
            for (j in 1..board.width) {
                if (get(i, j) != k && k != board.width * board.width) {
                    won = false
                    break
                }
                k++
            }
        }
        return won && get(board.width, board.width) == null
    }

    override fun processMove(direction: Direction) {
        val emptyCell = board.find { it == null }!!
        val neighbouringCell: Cell
        board.apply { neighbouringCell = emptyCell.getNeighbour(direction.reversed())!! }
        board[emptyCell] = board[neighbouringCell]
        board[neighbouringCell] = null
    }

    override fun get(i: Int, j: Int): Int? = board[board.getCell(i, j)]
}