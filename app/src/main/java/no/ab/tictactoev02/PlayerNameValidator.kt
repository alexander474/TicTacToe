package no.ab.tictactoev02

import android.content.res.Resources


class PlayerNameValidator{


    fun checkIfNameExists(name: String): Boolean{
        return false
    }

    fun checkIfNameIsIllegal(name: String): Boolean{
        when(name){
            Resources.getSystem().getString(R.string.BOT_NAME) -> return true
        }
        return false
    }

}