package no.ab.tictactoev02

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import no.ab.tictactoev02.Board.BoardOLD
import no.ab.tictactoev02.Board.BotOLD
import java.util.ArrayList

class v3 : Fragment(){

    lateinit var board: BoardOLD
    lateinit var gameStatus: TextView
    lateinit var timeCounter: TextView
    lateinit var buRestart: Button
    lateinit var buBack: Button
    lateinit var buHint: Button
    lateinit var buttons: Array<Button>

    lateinit var player1: Player
    lateinit var player2: Player

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons(view)

        var player1Text = arguments!!.getString("player1")
        var player2Text = arguments!!.getString("player2")
        var player1Char = arguments!!.getString("player1Char")
        var player2Char = arguments!!.getString("player2Char")
        var isBot = arguments!!.getBoolean("isBot", true)

        player1 = Player(player1Text, player1Char, ArrayList())
        player2 = Player(player2Text, player2Char, ArrayList(), isBot)

        board = BoardOLD(view, buttons, player1, player2)

        buHint.setOnClickListener{
            board.getHint(BotOLD().drawNewField(player1.fields, player2.fields)) }

        buBack.setOnClickListener{ (activity as MainActivity).replaceFragment(R.id.fragment_container, StartPageFragment()) }

        buRestart.setOnClickListener{
            board.restartBoard()
            buHint.isEnabled = true
        }

        val buListener = View.OnClickListener {
                v-> val clickedButton = v as Button
            fieldClick(clickedButton)
        }

        buttons.forEach { b -> b.setOnClickListener(buListener) }
        updateGameStatusText("Starting: ${getActivePlayer().name}")
    }


    private fun fieldClick(clickedButton: Button){
        board.removeHint()
        board.move(clickedButton)
        if(board.activePlayer == 2 && board.player2.isBot && board.winner == -1 && !board.checkForFullBoard()){
            board.botMove(BotOLD().drawNewField(player1.fields, player2.fields))
        }
        if(board.winner != -1 || board.checkForFullBoard())buHint.isEnabled = false
        if(board.winner == -1 && !board.checkForFullBoard())updateGameStatusText("Next: ${getActivePlayer().name}")
    }

    private fun getActivePlayer(): Player{
        if(board.activePlayer == 1){
            return player1
        }else{
            return player2
        }
    }

    private fun updateGameStatusText(message: String){
        gameStatus.text = ""
        gameStatus.append(message)
    }

    private fun initButtons(view: View){
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
            view.findViewById(R.id.bu9))
    }


}