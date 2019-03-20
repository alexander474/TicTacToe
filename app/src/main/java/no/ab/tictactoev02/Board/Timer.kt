package no.ab.tictactoev02.Board

import android.os.SystemClock

/**
 * Used inspiration from https://github.com/ajithvgiri/Stopwatch/blob/master/app/src/main/java/com/ajithvgiri/stopwatch/MainActivity.kt
 */
class Timer{

    var time = "00:00"

    private var milliSecondsTime: Long = 0
    private var startTime: Long = 0
    private var timeBuffer: Long = 0
    private var updateTime: Long = 0

    private var seconds: Int = 0
    private var minutes: Int = 0
    private var milliSeconds: Int = 0

    private var secondsText: String = ""
    private var minutesText: String = ""
    private var milliSecondsText: String = ""


    fun startTimer(): Runnable = object : Runnable{

        override fun run() {
            milliSecondsTime = SystemClock.uptimeMillis() - startTime
            updateTime = timeBuffer+milliSecondsTime
            seconds = (updateTime/1000).toInt()
            minutes = seconds/60
            seconds = seconds%60
            milliSeconds = (updateTime%1000).toInt()

            if(minutes.toString().length < 2){
                minutesText = "0"+minutes.toString()
            }else{
                minutesText = minutes.toString()
            }

            if(seconds.toString().length < 2){
                secondsText = "0"+seconds.toString()
            }else{
                secondsText = seconds.toString()
            }

            milliSecondsText = milliSeconds.toString()
        }
    }

    fun restartTimer(){
        time = "00:00"
    }

    fun stopTimer(){

    }

}