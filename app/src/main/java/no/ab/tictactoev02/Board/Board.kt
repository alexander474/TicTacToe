package no.ab.tictactoev02.Board

/**
 * @param startingPlayer: PlayerID of the player starting the game, default = 1
 */
class Board(private val startingPlayer: Int = 1){

    var fields = Array(9) {0}
    private val timer = Timer()
    var activePlayer = startingPlayer
    var gameStarted = false
    var isFullBoard = false
    var isDraw = false
    var isWinner = false
    var winner = -1
    var looser = -1


    fun move(cellID: Int){
        if(!gameStarted) timer.startTimer()

        if(!isWinner && !isDraw && !isFullBoard && !checkIfMoveIsTaken(cellID)) {
            moveLogic(cellID)
        }
    }

    private fun checkIfMoveIsTaken(cellID: Int): Boolean{
        if(cellID<=0 || cellID>=9) return false
        return fields[cellID] != 0
    }


    private fun moveLogic(cellID: Int){
        if (activePlayer == 1) {
            fields[cellID] = activePlayer
            fullResultCheck()
            activePlayer = 2
        } else {
            fields[cellID] = activePlayer
            fullResultCheck()
            activePlayer = 1
        }
        print("\n[")
        fields.forEach { e -> print("${e}, ") }
        print("]")
    }

    fun restartBoard(){
        fields = Array(9) {0}
        timer.stopTimer()
        timer.restartTimer()
        activePlayer = startingPlayer
        gameStarted = false
        isFullBoard = false
        isDraw = false
        isWinner = false
        winner = -1
        looser = -1
    }

    private fun checkForWinner(): Boolean{
        checkFieldsForWinner(1)
        checkFieldsForWinner(2)
        return isWinner
    }

    private fun checkForFullBoard(): Boolean{
        if(getPlayerFieldsSize(1)+getPlayerFieldsSize(2)>=9) isFullBoard = true
        return isFullBoard
    }

    private fun checkForDraw(): Boolean{
        if(!isWinner && isFullBoard) isDraw = true
        return isDraw
    }

    private fun fullResultCheck(){
        checkForWinner()
        checkForFullBoard()
        checkForDraw()
    }



    private fun checkFieldsForWinner(playerID: Int){
        if(getPlayerFieldsSize(1)>=3 || getPlayerFieldsSize(2)>=3) {
            if(checkRow(playerID, 0,1,2)) setResultFields(playerID)
            else if(checkRow(playerID, 3,4,5)) setResultFields(playerID)
            else if(checkRow(playerID, 6,7,8)) setResultFields(playerID)
            else if(checkRow(playerID, 0,3,6)) setResultFields(playerID)
            else if(checkRow(playerID, 1,4,7)) setResultFields(playerID)
            else if(checkRow(playerID, 2,5,8)) setResultFields(playerID)
            else if(checkRow(playerID, 0,4,8)) setResultFields(playerID)
            else if(checkRow(playerID, 2,4,6)) setResultFields(playerID)
        }
    }

    /**
     * Checking each row for a user having all three fields
     */
    private fun checkRow(playerID: Int, valueOne: Int, valueTwo: Int, valueThree: Int): Boolean{
        return fields[valueOne] == playerID && fields[valueTwo] == playerID && fields[valueThree] == playerID
    }

    private fun setResultFields(winPlayer: Int){
        isWinner = true
        winner = winPlayer
        if(winner == 1) looser = 2 else looser = 1
    }


    /**
     * @return the size of fields a player have
     */
    private fun getPlayerFieldsSize(playerID: Int): Int{
        return fields.filter { e -> e.equals(playerID) }.size
    }


}