package com.example.beeptimer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var seekbar: SeekBar
    private lateinit var textView: TextView

    private lateinit var beep: MediaPlayer

    private var running: Boolean = false
    private var time: Long = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        seekbar = findViewById(R.id.seekBar)
        textView = findViewById(R.id.textView)

        beep = MediaPlayer.create(this, R.raw.sound)

        seekbar.progress = 5
        textView.text = "${seekbar.progress} seconds"

        button.setOnClickListener{
            running = !running
            Log.i("running","$running")
            if(running){
                button.text = "Stop"
            }else{
                button.text = "Start"
            }

        }

        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                textView.text = "$progress seconds"
                time = seekbar.progress.toLong()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        val handler: Handler = Handler(Looper.getMainLooper())
        val run = object : Runnable {
            override fun run() {
                if(running) {
                    beep()
                }
                handler.postDelayed(this, time*1000)
            }
        }
        handler.post(run)
    }

    private fun beep() {
//        Toast.makeText(applicationContext, "Beep", Toast.LENGTH_SHORT).show()
        beep.start()
        Log.i("sound","beep")
    }

}