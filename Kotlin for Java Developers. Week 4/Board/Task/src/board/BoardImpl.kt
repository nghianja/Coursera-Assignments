package board

import board.Direction.*
import java.lang.IllegalArgumentException

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

class SquareBoardImpl(override val width: Int): SquareBoard {
    private val cells = mutableListOf<Cell>()

    init {
        for (i in 1..width) {
            for (j in 1..width) {
                cells.add(Cell(i, j))
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return cells.find { it.i == i && it.j == j }
    }

    override fun getCell(i: Int, j: Int): Cell {
        val cell: Cell? = cells.find { it.i == i && it.j == j }
        if (cell == null)
            throw IllegalArgumentException()
        else
            return cell
    }

    override fun getAllCells(): Collection<Cell> {
        return cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return if (jRange.first <= jRange.last)
            cells.filter { i == it.i && jRange.contains(it.j) }
        else
            cells.filter { i == it.i && jRange.contains(it.j) }.reversed()
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return if (iRange.first <= iRange.last)
            cells.filter { iRange.contains(it.i) && j == it.j }
        else
            cells.filter { iRange.contains(it.i) && j == it.j }.reversed()
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when(direction) {
            UP -> cells.find { this.i - 1 == it.i && this.j == it.j }
            LEFT -> cells.find { this.i == it.i && this.j - 1 == it.j }
            DOWN -> cells.find { this.i + 1 == it.i && this.j == it.j }
            RIGHT -> cells.find { this.i == it.i && this.j + 1 == it.j }
        }
    }
}

class GameBoardImpl<T>(override val width: Int): GameBoard<T> {
    private val stores = mutableMapOf<Cell, T?>()
    private val squares = createSquareBoard(width)

    init {
        for (i in 1..width) {
            for (j in 1..width) {
                stores[Cell(i, j)] = null
            }
        }
    }

    override fun get(cell: Cell): T? {
        return stores[cell]
    }

    override fun set(cell: Cell, value: T?) {
        stores[cell] = value!!
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return stores.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return stores.filterValues(predicate).keys.first()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return stores.filterValues(predicate).count() > 0
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return stores.filterValues(predicate).count() == width * width
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return squares.getCellOrNull(i, j)
    }

    override fun getCell(i: Int, j: Int): Cell {
        return squares.getCell(i, j)
    }

    override fun getAllCells(): Collection<Cell> {
        return squares.getAllCells()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return squares.getRow(i, jRange)
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return squares.getColumn(iRange, j)
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}