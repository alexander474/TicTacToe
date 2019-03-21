package no.ab.tictactoev02.Fragments

import android.content.DialogInterface
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import no.ab.tictactoev02.R
import no.ab.tictactoev02.Timer

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
            enabledBotHandler(view, isChecked)
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

    private fun enabledBotHandler(view: View, isChecked: Boolean){
        if (isChecked){
            player2.isEnabled = false
            player2.text.clear()
            player2.append(getString(R.string.BOT_NAME))
            isBot = true
            difficultyAlert(view)

        }else{
            player2.isEnabled = true
            player2.text.clear()
            player2.append(getString(R.string.playerTwoName))
            isBot = false
        }
    }

    fun difficultyAlert(view: View){
        val items = resources.getStringArray(R.array.difficultyDialogModes)
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle(R.string.difficultDialogText)
            .setItems(items,
                DialogInterface.OnClickListener { dialog, which ->
                    difficulty = items.get(which)
                })
        builder.create().show()
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