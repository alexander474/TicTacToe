package no.ab.tictactoev02

import android.content.Context
import java.io.File
import java.io.FileInputStream

class FileHandler(val context: Context){


    public fun write(fileName: String, content: String){
        if(checkIfFileExists(fileName)){
            writeToExistingFile(fileName, content)
        }else{
            writeToNewFile(fileName, content)
        }

    }

    public fun read(fileName: String): String{
        val folder = context.filesDir
        val file: File? = File(folder, fileName)

        return FileInputStream(file).bufferedReader().use{
            it.readText()
        }
    }


    private fun checkIfFileExists(fileName: String): Boolean{
        val folder = context.filesDir
        val file: File? = File(folder, fileName)
        return file != null
    }


    private fun writeToNewFile(fileName: String, content: String){
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(content.toByteArray())
        }
    }

    private fun writeToExistingFile(fileName: String, content: String){
        context.openFileOutput(fileName, Context.MODE_APPEND).use {
            it.write(content.toByteArray())
        }
    }


}