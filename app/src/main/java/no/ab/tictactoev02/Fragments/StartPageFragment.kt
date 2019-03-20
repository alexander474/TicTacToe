package no.ab.tictactoev02.Fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import no.ab.tictactoev02.R

class StartPageFragment : FragmentHandler() {

    private lateinit var player1: EditText
    private lateinit var player2: EditText
    private lateinit var enableBot: Switch
    private lateinit var buStart: Button
    private lateinit var buHighscore: Button
    private lateinit var logoText: TextView
    private var isBot = false
    private var difficulty = "none"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initText(view)
        initButtons(view)
        val buListner = View.OnClickListener { handleButtonClick(it) }


        enableBot.setOnCheckedChangeListener{ buttonView, isChecked ->
            enabledBotHandler(isChecked)
        }

        buHighscore.setOnClickListener(buListner)
        buStart.setOnClickListener(buListner)


    }

    private fun handleButtonClick(view: View){
        when(view.id){
            R.id.buStart -> startGame()
            R.id.buHighscoore -> startHighScore()
        }
    }

    private fun enabledBotHandler(isChecked: Boolean){
        if (isChecked){
            player2.isEnabled = false
            player2.text.clear()
            player2.append(getString(R.string.BOT_NAME))
            isBot = true
            val dialog  = DifficultyDialog()
            dialog.setTargetFragment(this, 200)
            dialog.show(fragmentManager, "difficultDialog")

        }else{
            player2.isEnabled = true
            player2.text.clear()
            player2.append(getString(R.string.playerTwoName))
            isBot = false
        }
    }


    private fun startHighScore(){
        pushFragmentWithStack(requireActivity(), R.id.fragment_container, HighScoreFragment())
    }

    private fun startGame(){
        val fragment = GameFragment()
        val bundle = Bundle()
        var playerOneName = player1.text.toString()
        var playerTwoName = player2.text.toString()
        if(playerOneName.isEmpty()) playerOneName = getString(R.string.playerOneName)
        if(playerTwoName.isEmpty()) playerTwoName = getString(R.string.playerTwoName)
        bundle.putString("playerOneName", playerOneName)
        bundle.putString("playerTwoName", playerTwoName)
        bundle.putString("playerOneChar", getString(R.string.playerOneChar))
        bundle.putString("playerTwoChar", getString(R.string.playerTwoChar))
        bundle.putBoolean("isBot", isBot)
        bundle.putString("difficulty", difficulty)
        fragment.arguments = bundle
        pushFragmentWithStack(requireActivity(), R.id.fragment_container, fragment)
    }

    private fun initButtons(view: View){
        buStart= view.findViewById(R.id.buStart)
        buHighscore = view.findViewById(R.id.buHighscoore)
        player1 = view.findViewById(R.id.player1)
        player2 = view.findViewById(R.id.player2)
        enableBot = view.findViewById(R.id.enableBot)
    }

    private fun initText(view: View){
        logoText = view.findViewById(R.id.logoText)
        logoText.typeface = Typeface.createFromAsset(activity!!.applicationContext.assets, "fonts/logoFont.ttf")
    }
}