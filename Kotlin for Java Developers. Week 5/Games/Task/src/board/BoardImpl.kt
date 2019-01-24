package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

open class SquareBoardImpl(override val width: Int): SquareBoard {
    private val cells by lazy { initCells() }

    private fun initCells(): MutableList<Cell> {
        val c = mutableListOf<Cell>()
        for (i in 1..width) {
            for (j in 1..width) {
                c.add(Cell(i, j))
            }
        }
        return c
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

class GameBoardImpl<T>(override val width: Int): SquareBoardImpl(width), GameBoard<T> {
    private val cellValues = mutableMapOf<Cell, T?>()

    init {
        for (i in 1..width) {
            for (j in 1..width) {
                cellValues[Cell(i, j)] = null
            }
        }
    }

    override fun get(cell: Cell): T? {
        return cellValues[cell]
    }

    override fun set(cell: Cell, value: T?) {
        cellValues[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellValues.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return cellValues.filterValues(predicate).keys.first()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cellValues.filterValues(predicate).count() > 0
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return cellValues.filterValues(predicate).count() == width * width
    }
}
