package no.ab.tictactoev02.Board.BOT

/**
 * Will draw a move that is based on a bot with difficulty of medium
 */
class MediumBot : Bot{

    override fun run(fields: Array<Int>): Int {
        return MiniMaxMedium().run(fields)
    }

}