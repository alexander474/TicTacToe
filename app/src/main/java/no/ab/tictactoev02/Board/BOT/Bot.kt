package no.ab.tictactoev02.Board.BOT

interface Bot{

    /**
     * @param fields the board state
     * @return The best move to make
     */
    fun run(fields: Array<Int>): Int
}