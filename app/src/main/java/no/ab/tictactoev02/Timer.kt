package no.ab.tictactoev02

import android.os.SystemClock
import android.view.View
import android.widget.Chronometer

class Timer(view: View){

    private val chronometer = view.findViewById<Chronometer>(R.id.timeCounter)
    private var stopedTime: Long = 0


    fun startTimer(){
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
    }

    fun pauseTimer(){
        stopedTime = chronometer.base - SystemClock.elapsedRealtime()
        chronometer.stop()
    }

    fun resumeTimer(){
        if(stopedTime != 0L){
            chronometer.base = SystemClock.elapsedRealtime() + stopedTime
            chronometer.start()
        }
    }

    fun stopTimer(){
        chronometer.stop()
        stopedTime = 0L
    }


}