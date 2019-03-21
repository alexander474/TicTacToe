package no.ab.tictactoev02.Board

/**
 * @param startingPlayer: PlayerID of the player starting the game, default = 1
 */
class Board(private val startingPlayer: Int = 1){

    var fields = Array(9) {0}
    var activePlayer = startingPlayer
    var gameStarted = false
    var isFullBoard = false
    var isDraw = false
    var isWinner = false
    var winner = -1
    var looser = -1

    // Posible ways of winning the game
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


    /**
     * Moves/takes a field in the game
     */
    fun move(cellID: Int){
        gameStarted = true
        if(!isWinner && !isDraw && !isFullBoard && !checkIfMoveIsTaken(cellID)) {
            moveLogic(cellID)
        }
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
     * Adds the move, checks the results and changes player
     */
    private fun moveLogic(cellID: Int){
        if (activePlayer == 1) {
            fields[cellID] = activePlayer
            activePlayer = 2
        } else {
            fields[cellID] = activePlayer
            activePlayer = 1
        }
        fullResultCheck()
        print("\n[")
        fields.forEach { e -> print("${e}, ") }
        print("]")
    }

    fun restartBoard(){
        fields = Array(9) {0}
        //activePlayer = startingPlayer
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
            for(posible in posibleWinningCoordinates){
                if(checkRow(playerID, posible[0], posible[1], posible[2])) setResultFields(playerID)
            }
        }
    }

    /**
     * Checking each row for a user having all three fields
     */
    private fun checkRow(playerID: Int, valueOne: Int, valueTwo: Int, valueThree: Int): Boolean{
        return fields[valueOne] == playerID && fields[valueTwo] == playerID && fields[valueThree] == playerID
    }

    /**
     * Sets the winner to true, sets winner to right playerID and looser to right playerID
     */
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