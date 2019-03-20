package no.ab.tictactoev02.Board.BOT

class HardBot : Bot{

    override fun run(fields: Array<Int>): Int {
        return MiniMax().run(fields)
    }

}