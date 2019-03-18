package no.ab.tictactoev02

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import no.ab.tictactoev02.Fragments.GameFragment
import no.ab.tictactoev02.Fragments.HighScoreFragment

class StartPageFragment : Fragment() {

    private lateinit var player1: EditText
    private lateinit var player2: EditText
    private lateinit var enableBot: CheckBox
    private lateinit var buStart: Button
    private lateinit var buHighscore: Button
    private var isBot = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buStart= view.findViewById(R.id.buStart)
        buHighscore = view.findViewById(R.id.buHighscoore)
        player1 = view.findViewById(R.id.player1)
        player2 = view.findViewById(R.id.player2)
        enableBot = view.findViewById(R.id.enableBot)

        enableBot.setOnCheckedChangeListener{ buttonView, isChecked ->
            if (isChecked){
                player2.isEnabled = false
                player2.text.clear()
                player2.append(getString(R.string.BOT_NAME))
                isBot = true
            }else{
                player2.isEnabled = true
                player2.text.clear()
                player2.append(getString(R.string.playerTwo))
                isBot = false
            }
        }

        buHighscore.setOnClickListener{
            startHighScore()
        }


        buStart.setOnClickListener{
            startGame()
        }


    }

    private fun startHighScore(){
        (activity as MainActivity).replaceFragment(R.id.fragment_container,
            HighScoreFragment()
        )
    }

    private fun startGame(){
        var player1Text = player1.text.toString()
        var player2Text = player2.text.toString()
        if(player1Text.isEmpty()) player1Text = getString(R.string.playerOne)
        if(player2Text.isEmpty()) player2Text = getString(R.string.playerTwo)
        val fragment = GameFragment()
        val bundle = Bundle()
        bundle.putString("player1", player1Text)
        bundle.putString("player2", player2Text)
        bundle.putString("player1Char", getString(R.string.playerOneChar))
        bundle.putString("player2Char", getString(R.string.playerTwoChar))
        bundle.putBoolean("isBot", isBot)
        fragment.arguments = bundle
        (activity as MainActivity).replaceFragment(R.id.fragment_container, fragment)
    }
}