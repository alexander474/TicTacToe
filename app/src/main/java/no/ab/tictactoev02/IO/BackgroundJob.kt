package no.ab.tictactoev02.IO

import android.content.Context
import android.os.AsyncTask
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import no.ab.tictactoev02.Activities.MainActivity
import java.lang.ref.WeakReference

class BackgroundJob<T>(context: MainActivity) : AsyncTask<() -> T,Unit,T?>(){

    private val weakRef = WeakReference(context)
    private var returnVal: T? = null

    override fun onPreExecute() {
        super.onPreExecute()

        val activity = weakRef.get()
        if(activity == null || activity.isFinishing)return
        activity.progressBar.visibility = View.VISIBLE


    }


    override fun doInBackground(vararg params: () -> T): T? {
        returnVal = params[0].invoke()
        return returnVal
    }

    override fun onProgressUpdate(vararg values: Unit?) {
        super.onProgressUpdate(*values)

        val activity = weakRef.get()
    }


    override fun onPostExecute(result: T?) {
        super.onPostExecute(result)

        val activity = weakRef.get()
        if (activity == null || activity.isFinishing)
            return
        activity.progressBar.visibility = View.GONE
    }

}