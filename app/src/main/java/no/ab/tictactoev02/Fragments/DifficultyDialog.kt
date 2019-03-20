package no.ab.tictactoev02.Fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import no.ab.tictactoev02.R

class DifficultyDialog : DialogFragment(){


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val items = resources.getStringArray(R.array.difficultyDialogModes)
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.difficultDialogText)
                .setItems(items,
                    DialogInterface.OnClickListener { dialog, which ->

                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}