package no.ab.tictactoev02.Board

class Bot(board: Board){

    private val fields = board.fields
    private val USERID = 1
    private val BOTID = 2

    fun drawNewField(): Int{
        var tries = 0
        val limit = 1000
        var choice = getBestChoice(tries,limit)
        while(checkIfChoiceIsTaken(choice)){
            choice = getBestChoice(tries,limit)
            tries++
        }
        return choice
    }

    /**
     * Checks if the move is already taken
     */
    private fun checkIfChoiceIsTaken(choice: Int): Boolean{
        if(choice<=0 || choice>=9) return false
        return fields[choice] != 0
    }

    private fun getBestChoice(tries: Int, limit: Int): Int {
        if(tries <= limit){
            val checkBotForWin = checkForWin(BOTID)
            val checkPlayerForWin = checkForWin(USERID)

            // check if there is any posible way that next pick is not win
            if(checkBotForWin == -1 && checkPlayerForWin == -1) {
                val checkBotGoodPick = goodPick(BOTID)
                val checkPlayerGoodPick = goodPick(USERID)

                // if a god pick is found for both players and it's the same pick
                if (checkBotGoodPick != -1 && checkPlayerGoodPick != -1 &&
                    checkBotGoodPick == checkPlayerGoodPick) return checkBotGoodPick

                //if bot found a good pick
                else if (checkBotGoodPick != -1) return checkBotGoodPick

                // if player has a good pick
                else if (checkPlayerGoodPick != -1) return checkPlayerGoodPick
            }
            // if bot can win in next pick
            else if(checkBotForWin != -1) return checkBotForWin
            // if player can win in next pick
            else if(checkPlayerForWin != -1) return checkPlayerForWin
        }
        // if the limit of tries is meet then draw a random move on the board
        return (0..8).random()
    }

    private fun checkForWin(playerID: Int): Int{
        if(checkRowWithTwo(playerID, 0,1)) return 2
        else if(checkRowWithTwo(playerID, 1,2)) return 0
        else if(checkRowWithTwo(playerID, 0,2)) return 1
        else if(checkRowWithTwo(playerID, 3,4)) return 5
        else if(checkRowWithTwo(playerID, 4,5)) return 3
        else if(checkRowWithTwo(playerID, 3,5)) return 4
        else if(checkRowWithTwo(playerID, 6,7)) return 8
        else if(checkRowWithTwo(playerID, 7,8)) return 6
        else if(checkRowWithTwo(playerID, 6,8)) return 7
        else if(checkRowWithTwo(playerID, 0,8)) return 4
        else if(checkRowWithTwo(playerID, 4,8)) return 0
        else if(checkRowWithTwo(playerID, 0,4)) return 8
        else if(checkRowWithTwo(playerID, 2,4)) return 6
        else if(checkRowWithTwo(playerID, 2,6)) return 4
        else if(checkRowWithTwo(playerID, 4,6)) return 2
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