@file:Suppress("UNUSED_PARAMETER")
package mmcs.assignment2

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, elem: E): Matrix<E> {
    if (height <= 0 )
        throw IllegalArgumentException("Matrix.height can't be less or equal 0")
    if (width <= 0 )
        throw IllegalArgumentException("Matrix.width can't be less or equal 0")
    var res = MatrixImpl<E>(height, width)
    for (i in 0 until height)
        for (j in 0 until width)
            res.elems.add(elem);

    return res
}

/**
 * Реализация интерфейса "матрица"
 */

@Suppress("EqualsOrHashCode")
class MatrixImpl<E>(override val height: Int, override val width: Int) : Matrix<E> {

    var elems: MutableList<E> = mutableListOf<E>()
    override fun get(row: Int, column: Int): E = elems[(row) * width + column]

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        elems[(row) * width + column] = value
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?): Boolean {
        if (this === other||other !is Matrix<*>||this.height != other.height||this.width != other.width) return true
        for (row in 0 until height) {
            for (column in 0 until width) {
                if (get(row, column) != other[row, column]) return false
            }
        }
        return true
    }
    override fun toString(): String {
        var m = ""
        for (row in 0 until height) {
            for (column in 0 until width) {
                print("${get(row, column)} ")
            }
            print("\n")
        }
        return m
    }
}