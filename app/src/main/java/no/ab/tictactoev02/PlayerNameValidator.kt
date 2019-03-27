package no.ab.tictactoev02

import android.content.Context
import android.content.res.Resources


class PlayerNameValidator(val context: Context){

    fun checkIfNameIsLegal(name: String): Boolean = when(name){
        context.getString(R.string.BOT_NAME) -> false
        else -> true
    }

}