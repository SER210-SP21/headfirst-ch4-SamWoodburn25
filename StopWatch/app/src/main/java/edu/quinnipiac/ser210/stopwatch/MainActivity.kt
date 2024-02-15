package edu.quinnipiac.ser210.stopwatch

/*
   * samantha woodburn
   * SER210
   * 2/14/24
   * HFAD chapter 5 demo
   * stopwatch app
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    //initialize the stopwatch
    lateinit var stopwatch: Chronometer
    var running = false
    var offset: Long = 0

    //add key strings for use with the bundle
    val OFFSET_KEY = "offset"
    val RUNNING_KEY ="running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //reference to the stopwatch
        stopwatch = findViewById<Chronometer>(R.id.stopwatch)

        //restore the previous state
        if(savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if(running){
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            }
            else setBaseTime()
        }

        //start button starts the watch if its not running
        val startButton = findViewById<Button>(R.id.start)
        startButton.setOnClickListener{
            if(!running){
                setBaseTime()
                stopwatch.start()
                running = true
            }
        }

        //pause button pauses if it is runnning
        val pauseButton = findViewById<Button>(R.id.pause)
        pauseButton.setOnClickListener{
            if(running){
                saveOffset()
                stopwatch.stop()
                running = false
            }
        }

        //reset button sets the ofset and stopwatch to 0
        val resetButton = findViewById<Button>(R.id.reset)
        resetButton.setOnClickListener{
            offset = 0
            setBaseTime()
        }
    }

    override fun onPause() {
        super.onPause()
        if(running){
            saveOffset()
            stopwatch.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        if(running){
            setBaseTime()
            stopwatch.start()
            offset = 0
        }
    }

    override fun onSaveInstanceState(savedInstantState: Bundle) {
        savedInstantState.putLong(OFFSET_KEY, offset)
        savedInstantState.putBoolean(RUNNING_KEY, running)
        savedInstantState.putLong(BASE_KEY, stopwatch.base)
        super.onSaveInstanceState(savedInstantState)
    }

    //update the stopwatch base time
    fun setBaseTime(){
        stopwatch.base =  SystemClock.elapsedRealtime() - offset
    }

    //record offset
    fun saveOffset(){
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }
}