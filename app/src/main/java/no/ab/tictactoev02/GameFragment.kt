package no.ab.tictactoev02

import android.graphics.Color
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import no.ab.tictactoev02.Board.Board
import no.ab.tictactoev02.Board.Bot
import no.ab.tictactoev02.IO.UserEntity
import no.ab.tictactoev02.IO.UserModel
import java.util.ArrayList

class GameFragment : Fragment() {

    lateinit var board: Board
    lateinit var bot: Bot
    lateinit var gameStatus: TextView
    lateinit var timeCounter: TextView
    lateinit var buRestart: Button
    lateinit var buBack: Button
    lateinit var buHint: Button
    lateinit var buttons: Array<Button>
    var hintedButtonID = -1

    lateinit var player1: Player
    lateinit var player2: Player

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons(view)
        initPlayers()
        board = Board(1)
        if(player2.isBot) bot = Bot(board)


        buHint.setOnClickListener {
            setHintButton()
        }

        buBack.setOnClickListener {
            handleReset()
            (activity as MainActivity).replaceFragment(R.id.fragment_container, StartPageFragment())
        }

        buRestart.setOnClickListener { handleReset() }

        /**
         * Listens to click on a field
         * If Bot exists then launch bot after user has clicked
         */
        val buListener = View.OnClickListener { v ->
            removeHintButton()
            handleBoardLogic(v as Button)
            if(getPlayer(board.activePlayer).isBot){ handleBotMove() }
        }

        // attaches the listener to each button that represents field
        buttons.forEach { b -> b.setOnClickListener(buListener) }

        updateGameStatusText("Starting: ${getPlayer(board.activePlayer).name}")
    }


    private fun handleBoardLogic(clickedButton: Button){
        buttonIdentityChange(clickedButton, getPlayer(board.activePlayer).playerChar, getPlayerButtonColor(), false)
        board.move(getButtonCellID(clickedButton))

        if (board.isWinner || board.isFullBoard || board.isDraw) handleEndGame()
        if(!board.isWinner && !board.isFullBoard && !board.isDraw) updateGameStatusText("Next: ${getPlayer(board.activePlayer).name}")
    }

    private fun handleBotMove(){
        val fieldID = Bot(board).drawNewField()
        handleBoardLogic(buttons[fieldID])
    }

    /**
     * Sets all fields and hint button to isEnabled = false
     * Displays the result
     */
    private fun handleEndGame(){
        buHint.isEnabled = false
        buttons.forEach { b -> b.isEnabled = false }
        setResultGameStatusText()
        insertResultToDatabase()
    }

    private fun insertResultToDatabase() {
        val userModel = UserModel(activity!!.application)
        //var userOne = userModel.getUser(us)
        if(board.isWinner){

        }
        else if(board.isDraw){
            //userModel.insert(User())
        }
    }

    private fun getPlayer(playerNum: Int): Player {
            if (playerNum == 1) {
                return player1
            } else {
                return player2
            }
    }

    private fun setHintButton(){
        hintedButtonID = Bot(board).drawNewField()
        if(hintedButtonID != -1 && hintedButtonID<=buttons.size-1) {
            val button = buttons[hintedButtonID]
            buttonIdentityChange(button, "", Color.YELLOW, true)
        }
    }

    private fun removeHintButton(){
        if(hintedButtonID != -1 && hintedButtonID>buttons.size-1){
            val button = buttons[hintedButtonID]
            button.setBackgroundColor(Color.WHITE)
        }
    }


    private fun updateGameStatusText(message: String) {
            gameStatus.text = ""
            gameStatus.append(message)
    }


    private fun setResultGameStatusText(){
            var result = ""
            if(board.isWinner) result = "Winner: ${getPlayer(board.winner).name}"
            else if(board.isDraw) result = "It's a draw"
            updateGameStatusText(result)
    }

    private fun handleReset(){
        board.restartBoard()
        buttons.forEach {
            buttonIdentityChange(it, "", Color.WHITE, true)
        }
        buHint.isEnabled = true
        updateGameStatusText("Starting: ${getPlayer(board.activePlayer).name}")
    }

    /**
     * @return the right mapping from button to cellID (mapping to right ID in array)
     */
    private fun getButtonCellID(button: Button): Int{
        var cellID = -1
        when(button.id){
            R.id.bu1 -> cellID = 0
            R.id.bu2 -> cellID = 1
            R.id.bu3 -> cellID = 2
            R.id.bu4 -> cellID = 3
            R.id.bu5 -> cellID = 4
            R.id.bu6 -> cellID = 5
            R.id.bu7 -> cellID = 6
            R.id.bu8 -> cellID = 7
            R.id.bu9 -> cellID = 8
        }
        return cellID
    }

    /**
     * Changes the identity of the button (isEnabled, Backgroundcolor and text)
     */
    private fun buttonIdentityChange(button: Button, text: String, color: Int, isEnabled: Boolean): Button{
        button.isEnabled = isEnabled
        button.setBackgroundColor(color)
        button.text = text
        return button
    }


    /**
     * @return the players field/button color
     */
    private fun getPlayerButtonColor(): Int{
        if(board.activePlayer==1) return Color.parseColor("#009193")
        else return Color.parseColor("#FF9300")
    }

    /**
     * Initializes the players
     */
    private fun initPlayers(){
        var player1Text = arguments!!.getString("player1")
        var player2Text = arguments!!.getString("player2")
        var player1Char = arguments!!.getString("player1Char")
        var player2Char = arguments!!.getString("player2Char")
        var isBot = arguments!!.getBoolean("isBot", true)

        player1 = Player(player1Text, player1Char, ArrayList())
        player2 = Player(player2Text, player2Char, ArrayList(), isBot)
    }


    /**
     * Initializes all the buttons
     */
    private fun initButtons(view: View) {
            gameStatus = view.findViewById(R.id.gameStatus)
            timeCounter = view.findViewById(R.id.timeCounter)
            timeCounter.text = "00:00"
            buRestart = view.findViewById(R.id.buRestart)
            buBack = view.findViewById(R.id.buBack)
            buHint = view.findViewById(R.id.buHint)

            buttons = arrayOf(
                view.findViewById(R.id.bu1),
                view.findViewById(R.id.bu2),
                view.findViewById(R.id.bu3),
                view.findViewById(R.id.bu4),
                view.findViewById(R.id.bu5),
                view.findViewById(R.id.bu6),
                view.findViewById(R.id.bu7),
                view.findViewById(R.id.bu8),
                view.findViewById(R.id.bu9)
            )
    }



}