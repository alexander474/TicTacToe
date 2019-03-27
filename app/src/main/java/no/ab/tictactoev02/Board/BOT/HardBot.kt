package no.ab.tictactoev02.Board.BOT

/**
 * Will draw a move that is based on a bot with difficulty of hard
 */
class HardBot : Bot{

    override fun run(fields: Array<Int>): Int {
        return MiniMaxHard().run(fields)
    }

}