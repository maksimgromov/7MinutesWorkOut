package com.maksimgromov.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.maksimgromov.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding : ActivityExerciseBinding? = null
    private var restTimer : CountDownTimer? = null
    private var restProgress : Int = 0
    private val restTimerMillisInFuture : Long = 10000
    private val countDownInterval : Long = 1000
    private val restTimerMaximumValue : Int = 10
    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress : Int = 0
    private val exerciseTimerMillisInFuture : Long = 30000
    private val exerciseTimerMaximumValue : Int = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        setRestView()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
    }

    private fun setRestView() {
        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setExerciseView() {
        binding?.flProgressBar?.visibility = View.GONE
        binding?.tvTitle?.text = "Exercise name"
        binding?.flExerciseView?.visibility = View.VISIBLE
        if(exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(restTimerMillisInFuture, countDownInterval) {

            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = restTimerMaximumValue - restProgress
                binding?.tvTimer?.text = (restTimerMaximumValue - restProgress).toString()
            }

            override fun onFinish() {
                setExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseTimerMillisInFuture, countDownInterval) {

            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = exerciseTimerMaximumValue - exerciseProgress
                binding?.tvTimerExercise?.text = (exerciseTimerMaximumValue - exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "30 seconds are over, let's go to rest view",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }
}