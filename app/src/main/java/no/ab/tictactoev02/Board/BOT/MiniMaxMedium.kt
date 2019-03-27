package no.ab.tictactoev02.Board.BOT

class MiniMaxMedium{

    val posibleWinningCoordinates: Array<Array<Int>> = arrayOf(
        arrayOf(0,1,2),
        arrayOf(3,4,5),
        arrayOf(6,7,8),
        arrayOf(0,3,6),
        arrayOf(2,5,8),
        arrayOf(1,4,7),
        arrayOf(2,4,6),
        arrayOf(0,4,8)
    )

    companion object {
        const val WIN = 10
        const val LOSE = -10
        const val DRAW = 0
        const val PLAYER = 1
        const val BOT = 2
    }

    fun run(fields: Array<Int>): Int{
        return miniMax(fields, BOT)
    }


    private fun miniMax(fields: Array<Int>, playerID: Int):Int{
        val winner = checkFieldsForWinner(fields)
        if(winner != -1){
            return score(winner)
        }

        if(playerID == BOT){
            var bestVal = Int.MIN_VALUE
            var bestSpot = 0
            for(i in 0 until fields.size){
                if(fields[i] != 0) continue
                fields[i] = playerID
                var value = miniMax(fields, playerID)
                if(value > bestVal){
                    bestVal = value
                    bestSpot = i
                }
                fields[i] = 0
            }
            return bestSpot
        }
        else if(playerID == PLAYER){
            var bestVal = Int.MAX_VALUE
            var bestSpot = 0
            for(i in 0 until fields.size){
                if(fields[i] != 0) continue
                fields[i] = playerID
                var value = miniMax(fields, playerID)
                if(value < bestVal){
                    bestVal = value
                    bestSpot = i
                }
                fields[i] = 0
            }
            return bestSpot
        }
        return -1
    }


    private fun score(winner: Int): Int {
        when(winner){
            BOT -> return WIN
            PLAYER -> return LOSE
        }
        return DRAW
    }

    private fun checkFieldsForWinner(fields: Array<Int>): Int{
        for(posible in posibleWinningCoordinates){
            if(checkRow(fields,
                    BOT, posible[0], posible[1], posible[2])) return BOT
        }
        for(posible in posibleWinningCoordinates){
            if(checkRow(fields,
                    PLAYER, posible[0], posible[1], posible[2])) return PLAYER
        }
        return -1
    }

    /**
     * Checking each row for a user having all three fields
     */
    private fun checkRow(fields: Array<Int>, playerID: Int, valueOne: Int, valueTwo: Int, valueThree: Int): Boolean{
        return fields[valueOne] == playerID && fields[valueTwo] == playerID && fields[valueThree] == playerID
    }


}