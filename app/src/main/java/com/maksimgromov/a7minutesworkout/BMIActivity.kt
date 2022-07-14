package com.maksimgromov.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.maksimgromov.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = getString(R.string.calculate_bmi)
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            if (validateMetricUnits()) {
                val height: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weight: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val bmi = weight / (height * height)
                displayBMIResult(bmi)
            } else {
                Toast.makeText(this@BMIActivity, getString(R.string.please_enter_valid_values), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun validateMetricUnits(): Boolean {
        return (binding?.etMetricUnitWeight?.text.toString().isEmpty() || binding?.etMetricUnitHeight?.text.toString().isEmpty())
    }

    private  fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = getString(R.string.very_severely_underweight)
            bmiDescription = getString(R.string.eat_more)
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = getString(R.string.severely_underweight)
            bmiDescription = getString(R.string.eat_more)
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = getString(R.string.underweight)
            bmiDescription = getString(R.string.eat_more)
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = getString(R.string.normal)
            bmiDescription = getString(R.string.good_shape)
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = getString(R.string.overweight)
            bmiDescription = getString(R.string.workout_maybe)
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = getString(R.string.obese_class_one)
            bmiDescription = getString(R.string.workout_maybe)
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = getString(R.string.obese_class_two)
            bmiDescription = getString(R.string.dangerous_condition)
        } else {
            bmiLabel = getString(R.string.obese_class_three)
            bmiDescription = getString(R.string.dangerous_condition)
        }
        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }
}