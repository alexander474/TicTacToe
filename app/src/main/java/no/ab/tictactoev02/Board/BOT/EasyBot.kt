package no.ab.tictactoev02.Board.BOT

import no.ab.tictactoev02.Board.Board

class EasyBot(): Bot{

    private lateinit var fields: Array<Int>
    private val USERID = 1
    private val BOTID = 2
    private var move = 0
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


    override fun run(fields: Array<Int>): Int{
        this.fields = fields
        move++
        var tries = 0
        val limit = 1000
        var move = getBestChoice(tries,limit)

        //While the move is taken or not valid
        while(checkIfMoveIsTaken(move)){
            move = getBestChoice(tries,limit)
            tries++
        }
        return move
    }

    /**
     * Checks if the move the player has made is taken
     * @return false false if the field is not taken/empty
     */
    private fun checkIfMoveIsTaken(cellID: Int): Boolean{
        //checks if the move is outside the board
        if(cellID<0 || cellID>=9) return true
        return fields[cellID] != 0
    }

    /**
     * @return the best move for the bot to make, if none return a random number between 0..8
     */
    private fun getBestChoice(tries: Int, limit: Int): Int {
        if(tries <= limit){

            //Checks if it's the first move
            if(move == 1) {
                val checkFirstMove = chechMoveOne(USERID)

                // if theres is any good starting move for bot take it
                if (checkFirstMove != -1) return checkFirstMove
            }


            val checkBotForWin = checkForWin(BOTID)
            val checkPlayerForWin = checkForWin(USERID)

            //if bot can win, take it
            if(checkBotForWin != -1) return checkBotForWin

            //if user can win, block it
            if(checkPlayerForWin != -1) return checkPlayerForWin

            val checkBotGoodPick = goodPick(BOTID)
            val checkPlayerGoodPick = goodPick(USERID)

            //if there is any good move for bot, take it
            if(checkBotGoodPick != -1) return checkBotGoodPick

            // if there is any good move for player, block it
            if(checkPlayerGoodPick != -1) return checkPlayerGoodPick

        }
        // if the limit of tries is meet then draw a random move on the board
        return (0..8).random()
    }

    private fun checkForWin(playerID: Int): Int{
        for(posible in posibleWinningCoordinates){
            if(checkRowWithTwo(playerID, posible[0], posible[1])) return posible[2]
            else if(checkRowWithTwo(playerID, posible[1], posible[2])) return posible[0]
            else if(checkRowWithTwo(playerID, posible[0], posible[2])) return posible[1]
        }
        return -1
    }

    private fun chechMoveOne(playerID: Int): Int{
        if(checkRowWithOne(playerID, 0)) return 4
        if(checkRowWithOne(playerID, 2)) return 4
        if(checkRowWithOne(playerID, 6)) return 4
        if(checkRowWithOne(playerID, 8)) return 4
        if(checkRowWithOne(playerID, 4)) return 2
        if(checkRowWithOne(playerID, 1)) return 4
        if(checkRowWithOne(playerID, 3)) return 4
        if(checkRowWithOne(playerID, 5)) return 4
        if(checkRowWithOne(playerID, 7)) return 4
        return -1
    }


    private fun goodPick(playerID: Int): Int {
        if(checkRowWithOne(playerID, 0)) return 1
        else if(checkRowWithOne(playerID, 1)) return 2
        else if(checkRowWithOne(playerID, 2)) return 3
        else if(checkRowWithOne(playerID, 3)) return 4
        else if(checkRowWithOne(playerID, 4)) return 5
        else if(checkRowWithOne(playerID, 5)) return 6
        else if(checkRowWithOne(playerID, 6)) return 7
        else if(checkRowWithOne(playerID, 7)) return 8
        else if(checkRowWithOne(playerID, 8)) return 7
        else if(checkRowWithOne(playerID, 7)) return 6
        else if(checkRowWithOne(playerID, 6)) return 5
        else if(checkRowWithOne(playerID, 5)) return 4
        else if(checkRowWithOne(playerID, 4)) return 3
        else if(checkRowWithOne(playerID, 3)) return 2
        else if(checkRowWithOne(playerID, 2)) return 1
        else if(checkRowWithOne(playerID, 1)) return 0
        else if(checkRowWithOne(playerID, 0)) return 4
        else if(checkRowWithOne(playerID, 4)) return 8
        else if(checkRowWithOne(playerID, 8)) return 4
        else if(checkRowWithOne(playerID, 4)) return 0
        else if(checkRowWithOne(playerID, 2)) return 4
        else if(checkRowWithOne(playerID, 4)) return 6
        else if(checkRowWithOne(playerID, 6)) return 4
        else if(checkRowWithOne(playerID, 4)) return 2
        return -1
    }


    /**
     * Checking each row for a user having two fields
     */
    private fun checkRowWithTwo(playerID: Int, valueOne: Int, valueTwo: Int): Boolean{
        return fields[valueOne] == playerID && fields[valueTwo] == playerID
    }

    /**
     * Checking each row for a user having one fields
     */
    private fun checkRowWithOne(playerID: Int, valueOne: Int): Boolean{
        return fields[valueOne] == playerID
    }

}