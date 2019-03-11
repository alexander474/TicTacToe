package no.ab.tictactoev02.Board

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.TextView
import no.ab.tictactoev02.Player
import no.ab.tictactoev02.R

class BoardOLD(val view: View, var buttons: Array<Button>, var player1: Player, var player2: Player, var startingPlayer: Int = 1) {

    var activePlayer = startingPlayer
    var winner = -1
    var hintedButtonCellID = -1
    val timer = Timer()
    var gameStarted = false
    var isDraw = false
    var gameStatus: TextView = view.findViewById(R.id.gameStatus)

    fun move(clickedButton: Button){
        if(!gameStarted) timer.startTimer()
        gameStarted = true
        var cellID = 0
        when(clickedButton.id){
            R.id.bu1 -> cellID = 1
            R.id.bu2 -> cellID = 2
            R.id.bu3 -> cellID = 3
            R.id.bu4 -> cellID = 4
            R.id.bu5 -> cellID = 5
            R.id.bu6 -> cellID = 6
            R.id.bu7 -> cellID = 7
            R.id.bu8 -> cellID = 8
            R.id.bu9 -> cellID = 9
        }
        buttonLogic(cellID, clickedButton)
    }


    fun botMove(cellID: Int){
        buttonLogic(cellID, buttons[cellID-1])
    }

    fun restartBoard(){
        gameStarted = false
        timer.restartTimer()
        player1.fields.clear()
        player2.fields.clear()
        updateGameStatusText("")
        activePlayer = 1
        winner = -1
        buttons.forEach { button ->
            button.isEnabled = true
            button.setBackgroundColor(Color.WHITE)
            button.text = ""
        }
    }

    private fun buttonLogic(cellID: Int, clickedButton: Button) {
        if(activePlayer == 1){
            clickedButton.text = player1.playerChar
            clickedButton.setBackgroundColor(Color.parseColor("#009193"))
            player1.fields.add(cellID)
            activePlayer = 2
        } else{
            clickedButton.text = player2.playerChar
            clickedButton.setBackgroundColor(Color.parseColor("#FF9300"))
            player2.fields.add(cellID)
            activePlayer = 1
        }


        clickedButton.isEnabled = false
        if(checkForWinner()){
            endGame("Winner: ${getWinningPlayer().name}")
        }else if(checkForFullBoard()){
            isDraw = true
            endGame("It's a draw")
        }
    }

    private fun getWinningPlayer(): Player {
        if(winner == 1){
            return player1
        }else{
            return player2
        }
    }

    private fun getLoosingPlayer(): Player {
        if(activePlayer == 1){
            return player2
        }else{
            return player1
        }
    }


    private fun updateGameStatusText(message: String){
        gameStatus.text = ""
        gameStatus.append(message)
    }

    private fun endGame(message: String) {
        buttons.forEach { b -> b.isEnabled = false }
        updateGameStatusText(message)
        //writeToDB()

    }
/**
    private fun writeToDB(){
        val dbHandler = DatabaseHandler(view.context)
        if(isDraw){
            dbHandler.insertUser(User(null, player1.name, 0,1,0))
            dbHandler.insertUser(User(null, player2.name,0, 1, 0))
        }else{
            dbHandler.insertUser(User(null, getWinningPlayer().name, 1, 0, 0))
            dbHandler.insertUser(User(null, getLoosingPlayer().name, 0, 0, 1))
        }
    }**/



    private fun checkForWinner():Boolean{
        if(checkFields(player1.fields)){
            winner = 1
            return true
        }
        else if(checkFields(player2.fields)){
            winner = 2
            return true
        }
        return false
    }



    private fun checkFields(playerFields: ArrayList<Int>): Boolean{
        var isWinner = false
        if(playerFields.size<3) return false
        else if(playerFields.contains(1) && playerFields.contains(2) && playerFields.contains(3)) isWinner = true
        else if(playerFields.contains(4) && playerFields.contains(5) && playerFields.contains(6)) isWinner = true
        else if(playerFields.contains(7) && playerFields.contains(8) && playerFields.contains(9)) isWinner = true
        else if(playerFields.contains(1) && playerFields.contains(4) && playerFields.contains(7)) isWinner = true
        else if(playerFields.contains(2) && playerFields.contains(5) && playerFields.contains(8)) isWinner = true
        else if(playerFields.contains(3) && playerFields.contains(6) && playerFields.contains(9)) isWinner = true
        else if(playerFields.contains(1) && playerFields.contains(5) && playerFields.contains(9)) isWinner = true
        else if(playerFields.contains(3) && playerFields.contains(5) && playerFields.contains(7)) isWinner = true
        return isWinner
    }

    fun checkForFullBoard(): Boolean {
        var isFull = false
        val selectedButtons = player1.fields + player2.fields
        if(selectedButtons.size >= 9) isFull = true
        return isFull
    }

    fun getHint(cellID: Int) {
        hintedButtonCellID = cellID-1
        var button = buttons[hintedButtonCellID]
        button.setBackgroundColor(Color.YELLOW)
    }

    fun removeHint(){
        if(hintedButtonCellID != -1){
            buttons[hintedButtonCellID].setBackgroundColor(Color.WHITE)
            hintedButtonCellID = -1
        }
    }

}