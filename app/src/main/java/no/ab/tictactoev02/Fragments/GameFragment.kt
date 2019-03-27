package no.ab.tictactoev02.Fragments

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import no.ab.tictactoev02.Board.BOT.Bot
import no.ab.tictactoev02.Board.BOT.EasyBot
import no.ab.tictactoev02.Board.BOT.HardBot
import no.ab.tictactoev02.Board.BOT.MediumBot
import no.ab.tictactoev02.Board.Board
import no.ab.tictactoev02.IO.UserEntity
import no.ab.tictactoev02.Player
import no.ab.tictactoev02.R
import no.ab.tictactoev02.Timer
import no.ab.tictactoev02.ViewModel.UserViewModel

class GameFragment : FragmentHandler() {

    lateinit var board: Board
    lateinit var bot: Bot
    lateinit var gameStatus: TextView
    lateinit var timeCounter: TextView
    lateinit var buRestart: Button
    lateinit var buHint: Button
    lateinit var buttons: Array<Button>

    //Determines if a button is hinted, if not -1
    var hintedButtonID = -1

    lateinit var player1: Player
    lateinit var player2: Player
    lateinit var difficulty: String

    private lateinit var timer: Timer

    private lateinit var userModel: UserViewModel
    private var allUsers = ArrayList<UserEntity>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer = Timer(view)
        initButtons(view)
        initPlayers()
        board = Board((1..2).random())

        //If player two is bot, then set the bot field
        if(player2.isBot) {
            bot = when(difficulty){
                "EASY" -> EasyBot()
                "MEDIUM" -> MediumBot()
                "HARD" -> HardBot()
                else -> EasyBot()
            }
        }else{
            // If you're not playing against a bot you should not be able to get any hints
            buttonIdentityChange(buHint, getString(R.string.buttonHintText), R.color.colorButtonDisabled, true)
        }

        buHint.setOnClickListener {
            setHintButton()
        }

        buRestart.setOnClickListener { handleReset() }

        /**
         * Listens to click on a field
         * If EasyBot exists then launch bot after user has clicked
         */
        val buListener = View.OnClickListener { v ->
            removeHintButton()
            handleBoardLogic(v as Button)
            if(getPlayer(board.activePlayer).isBot && !(board.isWinner || board.isDraw)){ handleBotMove() }
        }

        // attaches the listener to each button that represents field
        buttons.forEach { b -> b.setOnClickListener(buListener) }

        //sets the game-status first time the game is loaded
        updateGameStatusText("Starting: ${getPlayer(board.activePlayer).name}")

        userModel = UserViewModel(activity!!.application)
        userModel.allUsersLive.observe(this, Observer { liveData ->
            liveData?.let { data ->
                allUsers = data.toCollection(ArrayList())
            }
        })

        // If the starting player is bot then move the bot when the fragment is loaded
        if(player2.isBot && board.activePlayer == 2 && !board.gameStarted){
            handleBotMove()
        }
    }


    /**
     * @param clickedButton The button/field on the board that is clicked
     */
    private fun handleBoardLogic(clickedButton: Button){
        if(!board.gameStarted) timer.startTimer()
        // Sets the clicked button to not enabled and the color to the players color
        buttonIdentityChange(clickedButton, getPlayer(board.activePlayer).playerChar, getPlayerButtonColor(), false)
        board.move(getButtonCellID(clickedButton))

        if (board.isWinner || board.isFullBoard || board.isDraw) handleEndGame()

        // Displays the next moving player
        if(!board.isWinner && !board.isFullBoard && !board.isDraw) updateGameStatusText("Next: ${getPlayer(board.activePlayer).name}")
    }


    /**
     * Executor for the bot to move
     */
    private fun handleBotMove(){
        val fieldID = bot.run(board.fields)
        handleBoardLogic(buttons[fieldID])
    }

    /**
     * Sets all fields and hint button to isEnabled = false
     * Displays the result
     */
    private fun handleEndGame(){
        timer.stopTimer()
        buHint.isEnabled = false
        buttons.forEach { b -> b.isEnabled = false }
        setResultGameStatusText()
        insertResultToDatabase()
    }

    /**
     * Inserts the result
     * If the user already exists then update with either win, draw, loose
     * If the user dosen't exist, then create it and set either win, draw loose
     */
    private fun insertResultToDatabase() {
        if(board.isWinner){
            val winner = checkIfUserExists(getPlayer(board.winner))
            val looser = checkIfUserExists(getPlayer(board.looser))
            insertWithWinner(winner,looser)
        }
        else if(board.isDraw){
            val userOne = checkIfUserExists(player1)
            val userTwo = checkIfUserExists(player2)
            insertWithDraw(userOne,userTwo)
        }
    }

    /**
     * Updates the users in the database with draw
     */
    private fun insertWithDraw(userOne: UserEntity, userTwo: UserEntity){
        userOne.incrementDraw(1)
        userTwo.incrementDraw(1)
        userModel.update(userOne)
        userModel.update(userTwo)
    }

    /**
     * Updates the users in the database with either win or loose
     */
    private fun insertWithWinner(winner: UserEntity, looser: UserEntity){
        winner.incrementWin(1)
        looser.incrementLoose(1)
        userModel.update(winner)
        userModel.update(looser)
    }

    /**
     * Checks if a player matches a user in database by name and if no user is found
     * then it will create a new user with the name of that player
     * @param player Which player we are searching for
     * @return returns either existing or recently created user
     */
    private fun checkIfUserExists(player: Player): UserEntity{
        allUsers.forEach { if(it.name.equals(player.name)) return it }
        val newUser = UserEntity(player.name, 0,0,0)
        userModel.insert(newUser)
        return newUser
    }


    /**
     * @param playerID The player that will be returned as a Player()
     * @return the player matching that playerID
     */
    private fun getPlayer(playerID: Int): Player {
            if (playerID == 1) {
                return player1
            } else {
                return player2
            }
    }

    /**
     * Sets hinted button in the game, based on the bot logic
     * Hint is only enabled when playing against bot
     */
    private fun setHintButton(){
        if(player2.isBot){
            hintedButtonID = bot.run(board.fields)
            if(hintedButtonID != -1 && hintedButtonID<=buttons.size-1) {
                val button = buttons[hintedButtonID]
                buttonIdentityChange(button, "", R.color.colorButtonHint, true)
            }
        }
    }

    /**
     * Removes the hinted button in the game
     */
    private fun removeHintButton(){
        if(hintedButtonID != -1){
            val button = buttons[hintedButtonID]
            button.setBackgroundColor(Color.WHITE)
        }
    }


    /**
     * Updates the status text in the game
     * @param message The text that will be displayed
     */
    private fun updateGameStatusText(message: String) {
            gameStatus.text = ""
            gameStatus.append(message)
    }


    /**
     * Displays the result when it's either win or draw.
     * If there is a winner, display the winning player
     */
    private fun setResultGameStatusText(){
            var result = ""
            if(board.isWinner) result = "Winner: ${getPlayer(board.winner).name}"
            else if(board.isDraw) result = "It's a draw"
            updateGameStatusText(result)
    }

    /**
     * Resets the board to it initial state
     */
    private fun handleReset(){
        timer.stopTimer()
        board.restartBoard()
        buttons.forEach {
            buttonIdentityChange(it, "", R.color.colorBoardButtonDefault, true)
        }
        buHint.isEnabled = true
        updateGameStatusText("Starting: ${getPlayer(board.activePlayer).name}")
        if(board.activePlayer==2 && player2.isBot){
            handleBotMove()
        }
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
     * Changes the identity of the button (isEnabled, Background-color and text)
     */
    private fun buttonIdentityChange(button: Button, text: String, color: Int, isEnabled: Boolean): Button{
        button.isEnabled = isEnabled
        button.setBackgroundColor(resources.getColor(color))
        button.text = text
        return button
    }


    /**
     * @return the players field/button color
     */
    private fun getPlayerButtonColor(): Int{
        if(board.activePlayer==1) return R.color.colorBoardButtonPlayerOne
        else return R.color.colorBoardButtonPlayerTwo
    }

    /**
     * Initializes the players with information from the bundle
     */
    private fun initPlayers(){
        var playerOneName = arguments!!.getString("playerOneName")
        var playerTwoName = arguments!!.getString("playerTwoName")
        var playerOneChar = arguments!!.getString("playerOneChar")
        var playerTwoChar = arguments!!.getString("playerTwoChar")
        var isBot = arguments!!.getBoolean("isBot", true)
        difficulty = arguments!!.getString("difficulty")

        player1 = Player(playerOneName, playerOneChar)
        player2 = Player(playerTwoName, playerTwoChar, isBot)
    }


    /**
     * Initializes all the buttons
     */
    private fun initButtons(view: View) {
            gameStatus = view.findViewById(R.id.gameStatus)
            timeCounter = view.findViewById(R.id.timeCounter)
            timeCounter.text = "00:00"
            buRestart = view.findViewById(R.id.buRestart)
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

    /**
     * To make sure that the timer is set on hold/pause when the user is setting the app on pause
     */
    override fun onPause() {
        super.onPause()
        timer.pauseTimer()
    }


    /**
     * To make sure that the timer resuming after being set on a pause
     */
    override fun onResume() {
        super.onResume()
        timer.resumeTimer()
    }




}