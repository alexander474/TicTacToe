package no.ab.tictactoev02.Board.BOT

class MiniMaxHard{
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

    /**
     * Having a lot of checks just to make sure that the move will be the best and it's a valid move considering the state
     * of the board. This algorithm wil be improved afterwards
     */
    fun run(fields: Array<Int>): Int{
        val value = miniMax(fields, BOT)
        val isTaken = checkIfMoveIsTaken(fields,value)
        if(isTaken){
            val valueSecond = miniMax(fields,BOT)
            val isTakenSecond = checkIfMoveIsTaken(fields,valueSecond)
            if(isTakenSecond){
                val valueThirdCheckPlayer = miniMax(fields, PLAYER)
                val isTakenThirdCheckPlayer = checkIfMoveIsTaken(fields,valueThirdCheckPlayer)
                if(!isTakenThirdCheckPlayer){
                    return valueThirdCheckPlayer
                }
            }else{ return valueSecond}
        }else{return value}

        return getValidMove(fields)
    }

    private fun getValidMove(fields: Array<Int>): Int {
        var checkMove = (0..8).random()
        while(checkIfMoveIsTaken(fields,checkMove)){
            checkMove = (0..8).random()
        }
        return checkMove
    }

    /**
     * Checks if the move the player has made is taken
     * @return false false if the field is not taken/empty
     */
    private fun checkIfMoveIsTaken(fields: Array<Int>, cellID: Int): Boolean{
        //checks if the move is outside the board
        if(cellID<0 || cellID>=9) return true
        return fields[cellID] != 0
    }


    private fun miniMax(fields: Array<Int>, playerID: Int):Int{
        val winner = checkFieldsForWinner(fields)
        if(winner != -1){
            return score(winner)
        }

        val bestBotMove = calculateMove(fields, playerID, Int.MIN_VALUE)
        if(bestBotMove != 0) return bestBotMove
        return -1
    }

    private fun calculateMove(fields: Array<Int>, playerID: Int, maxVal: Int): Int{
        var bestVal = maxVal
        var bestSpot = 0
        for(i in 0 until fields.size){
            if(fields[i] != 0) continue
            fields[i] = playerID
            var value = miniMax(fields, playerID)
            if(calculateValue(value, bestVal, maxVal)){
                bestVal = value
                bestSpot = i
            }
            fields[i] = 0
        }
        return bestSpot
    }

    private fun calculateValue(value: Int, bestVal: Int, maxVal: Int): Boolean{
        if(maxVal>0){
            if(value < bestVal){
                return true
            }
        }else{
            if(value > bestVal){
                return true
            }
        }
        return false
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