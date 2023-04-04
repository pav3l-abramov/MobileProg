
import kotlin.random.Random

enum class Cell(var y: Int, var x: Int) {
    A1(0, 0), A2(0, 1), A3(0, 2), A4(0, 3), A5(0, 4), A6(0, 5), A7(0, 6), A8(0, 7),
    B1(1, 0), B2(1, 1), B3(1, 2), B4(1, 3), B5(1, 4), B6(1, 5), B7(1, 6), B8(1, 7),
    C1(2, 0), C2(2, 1), C3(2, 2), C4(2, 3), C5(2, 4), C6(2, 5), C7(2, 6), C8(2, 7),
    D1(3, 0), D2(3, 1), D3(3, 2), D4(3, 3), D5(3, 4), D6(3, 5), D7(3, 6), D8(3, 7),
    E1(4, 0), E2(4, 1), E3(4, 2), E4(4, 3), E5(4, 4), E6(4, 5), E7(4, 6), E8(4, 7),
    F1(5, 0), F2(5, 1), F3(5, 2), F4(5, 3), F5(5, 4), F6(5, 5), F7(5, 6), F8(5, 7),
    G1(6, 0), G2(6, 1), G3(6, 2), G4(6, 3), G5(6, 4), G6(6, 5), G7(6, 6), G8(6, 7),
    H1(7, 0), H2(7, 1), H3(7, 2), H4(7, 3), H5(7, 4), H6(7, 5), H7(7, 6), H8(7, 7);
}

class Checkers() {
    val rows = 8
    val cols = 8
    var desk = Array(rows) { IntArray(cols) }
    var deskFull = Array(rows) { IntArray(cols) }

    val posBlack: MutableList<Cell> = mutableListOf(
        Cell.C1,
    )

    val posWhite: MutableList<Cell> = mutableListOf(
        Cell.B8, Cell.D8,
        Cell.F8, Cell.H8,
    )

    init {
        updateBoard()
    }

    fun updateBoard() {
        desk = Array(rows) { IntArray(cols) }

        posWhite.forEach {
            desk[it.x][it.y] = 1
        }
        posBlack.forEach {
            desk[it.x][it.y] = 2
        }
        for (i in 0..7) {
            for (j in 0..7) {
                if (deskFull[i][j] != 0) desk[i][j] = deskFull[i][j]
            }
        }
    }

    fun showBoard() {
        println("   A  B  C  D  E  F  G  H")
        var i = 1
        for (row in desk) {
            println("$i ${ row.contentToString() }")
            i++
        }
        println()
    }

    fun makeTurn(player: Int, cell: Cell): Boolean {
        var r = Random.nextInt(0, 10)
        if (r < 5) {
            while (true) {
                if (player<0) {
                     if (cell.y + player in 0..7 &&
                         cell.x + player in 0..7 &&
                         desk[cell.x+ player][cell.y + player] == 0) {
                            cell.x += player
                            cell.y += player
                            updateBoard()
                             break
                    }
                     else if (cell.y - player in 0..7 &&
                         cell.x + player in 0..7 &&
                         desk[cell.x+ player][cell.y - player] == 0) {
                         cell.y -= player
                         cell.x += player
                         updateBoard()
                         break
                     }
                     else if (cell.y + 2*player in 0..7 &&
                         cell.x + 2*player in 0..7 &&
                         desk[cell.x+ 2*player][cell.y + 2*player] == 0) {
                         cell.y += 2*player
                         cell.x += 2*player
                         updateBoard()
                         break
                     }
                     else if (cell.y - 2*player in 0..7 &&
                         cell.x + 2*player in 0..7 &&
                         desk[cell.x+ 2*player][cell.y - 2*player] == 0) {
                         cell.y -= 2*player
                         cell.x += 2*player
                         updateBoard()
                         break
                     }
                         else return false
                }
                if (player>0) {
                    if (cell.y + player in 0..7 &&
                        cell.x + player in 0..7 &&
                        desk[cell.x+ player][cell.y + player] == 0) {
                        cell.x += player
                        cell.y += player
                        updateBoard()
                        break
                    } else if (cell.y + player in 0..7 &&
                        cell.x - player in 0..7 &&
                        desk[cell.x- player][cell.y + player] == 0) {
                        cell.y += player
                        cell.x -= player
                        updateBoard()
                        break
                    } else return false
                }
            }
        }else {
            while(true) {
                if (player<0) {
                    if (cell.y - player in 0..7 &&
                        cell.x + player in 0..7 &&
                        desk[cell.x+ player][cell.y - player] == 0) {
                        cell.y -= player
                        cell.x += player
                        updateBoard()
                        break
                    }
                    else if (cell.y + player in 0..7 &&
                        cell.x + player in 0..7 &&
                        desk[cell.x+ player][cell.y + player] == 0) {
                        cell.x += player
                        cell.y += player
                        updateBoard()
                        break

                    }
                    else if (cell.y - 2*player in 0..7 &&
                        cell.x + 2*player in 0..7 &&
                        desk[cell.x+ 2*player][cell.y - 2*player] == 0) {
                        cell.y -= 2*player
                        cell.x += 2*player
                        updateBoard()
                        break
                    }
                    else if (cell.y + 2*player in 0..7 &&
                        cell.x + 2*player in 0..7 &&
                        desk[cell.x+ 2*player][cell.y + 2*player] == 0) {
                        cell.y += 2*player
                        cell.x += 2*player
                        updateBoard()
                        break
                    }

                    else return false
                }
                if (player>0) {
                    if (cell.y - player in 0..7 &&
                        cell.x + player in 0..7 &&
                        desk[cell.x+ player][cell.y - player] == 0) {
                        cell.y -= player
                        cell.x += player
                        updateBoard()
                        break
                    }
                    else if (cell.y - player in 0..7 &&
                        cell.x - player in 0..7 &&
                        desk[cell.x- player][cell.y - player] == 0) {
                        cell.y -= player
                        cell.x -= player
                        updateBoard()
                        break
                    }else return false
                }
            }
        }
        println()
        return true
    }

    fun checkCell(player: Int, cell: Cell) {
        var isMove = false

        if (player > 0) {
            if (cell.y - player in 0..7 &&
                cell.x + player in 0..7 &&
                desk[cell.x+ player][cell.y - player] == 0) {
                isMove = true
            } else if (cell.y + player in 0..7 &&
                cell.x + player in 0..7 &&
                desk[cell.x+ player][cell.y + player] == 0) {
                isMove = true
            }
            else if (cell.y + player in 0..7 &&
                cell.x - player in 0..7 &&
                desk[cell.x- player][cell.y + player] == 0) {
                isMove = true
            }
            else if (cell.y - player in 0..7 &&
                cell.x - player in 0..7 &&
                desk[cell.x- player][cell.y - player] == 0) {
                isMove = true
            }
        } else if (player < 0) {
                if (cell.y + player in 0..7 &&
                    cell.x + player in 0..7 &&
                    desk[cell.x+ player][cell.y + player] == 0) {
                    isMove = true

                }
                else if (cell.y - player in 0..7 &&
                    cell.x + player in 0..7 &&
                    desk[cell.x+ player][cell.y - player] == 0) {
                    isMove = true
                }
                else if (cell.y + 2*player in 0..7 &&
                    cell.x + 2*player in 0..7 &&
                    desk[cell.x+ 2*player][cell.y + 2*player] == 0) {
                    isMove = true
                }
                else if (cell.y - 2*player in 0..7 &&
                    cell.x + 2*player in 0..7 &&
                    desk[cell.x+ 2*player][cell.y - 2*player] == 0) {
                    isMove = true
                }

        }
        if (!isMove){
            if (player < 0 ) {
                posWhite.remove(cell)
                deskFull[cell.x][cell.y] = 1
            } else if (player > 0 ) {
                posBlack.remove(cell)
                deskFull[cell.x][cell.y] = 2
            }
        }

    }

    fun checkMove(player: Int, cell: Cell): Boolean {
        var isMove = false
        if (player > 0) {
            if (cell.y - player in 0..7 &&
                cell.x + player in 0..7 &&
                desk[cell.x+ player][cell.y - player] == 0) {
                isMove = true
            } else if (cell.y + player in 0..7 &&
                cell.x + player in 0..7 &&
                desk[cell.x+ player][cell.y + player] == 0) {
                isMove = true
            }
            else if (cell.y + player in 0..7 &&
                cell.x - player in 0..7 &&
                desk[cell.x- player][cell.y + player] == 0) {
                isMove = true
            }
            else if (cell.y - player in 0..7 &&
                cell.x - player in 0..7 &&
                desk[cell.x- player][cell.y - player] == 0) {
                isMove = true
            }

        } else if (player < 0) {
            if (cell.y - player in 0..7 &&
                cell.x + player in 0..7 &&
                desk[cell.x+ player][cell.y - player] == 0) {
                isMove = true
            } else if (cell.y + player in 0..7 &&
                cell.x + player in 0..7 &&
                desk[cell.x+ player][cell.y + player] == 0) {
                isMove = true
            }
            else if (cell.y + 2*player in 0..7 &&
                cell.x + 2*player in 0..7 &&
                desk[cell.x+ 2*player][cell.y + 2*player] == 0) {
                isMove = true
            }
            else if (cell.y - 2*player in 0..7 &&
                cell.x + 2*player in 0..7 &&
                desk[cell.x+ 2*player][cell.y - 2*player] == 0) {
                isMove = true
            }
        }
        return isMove
    }

    fun turn(player: Int) {
        if (player < 0) {
                var turnSeed: Int
                var tryCount = 0
                while (true) {
                    turnSeed = Random.nextInt(0, posWhite.count { true })
                    if (checkMove(player, posWhite[turnSeed])) {
                        while(makeTurn(player, posWhite[turnSeed])) { break }
                        checkCell(player, posWhite[turnSeed])
                        break
                    }
                    tryCount++
                    if (tryCount > 5) {
                        turnSeed=0
                        while (turnSeed<posWhite.count { true }) {
                            if (checkMove(player, posWhite[turnSeed])) {
                                while(makeTurn(player, posWhite[turnSeed])) { break }
                                checkCell(player, posWhite[turnSeed])
                                break
                            }

                            turnSeed ++
                        }
                        break
                    }
                }
            } else if (player > 0) {
                var turnSeed: Int
                var tryCount = 0
                while (true) {
                    turnSeed = Random.nextInt(0, posBlack.count { true })
                    if (checkMove(player, posBlack[turnSeed])) {
                        while(makeTurn(player, posBlack[turnSeed])) { break }
                        checkCell(player, posBlack[turnSeed])
                        break
                    }
                    tryCount++
                    if (tryCount > 5) {
                        turnSeed=0
                        while (turnSeed<posBlack.count { true }) {
                            if (checkMove(player, posBlack[turnSeed])) {
                                while(makeTurn(player, posBlack[turnSeed])) { break }
                                checkCell(player, posBlack[turnSeed])
                                break
                            }
                            turnSeed ++
                        }
                        break
                    }
                }
            }
        }
    }

fun main(args: Array<String>) {

    var isGameEnded = false
    val checkers = Checkers()
    checkers.showBoard()

    var move = 1
    var p = -1
    while(!isGameEnded) {
        println("Move: $move, player: ${if (p < 0) "white" else "black"}")
        checkers.turn(p)
        checkers.showBoard()
        move++
        p *=-1
        if (move > 1000) { println("\nSomething is going wrong...")
            break }

        if (checkers.posWhite.isEmpty()) {
            isGameEnded = true
            println("\nBlack player win!\nThe hunters (White player) did not find the wolf (Black player).")
        }
        if (checkers.posBlack.isEmpty()) {
            isGameEnded = true
            println("\nWhite player win!\n The hunters (White player) find the wolf (Black player).")
        }
    }

}
