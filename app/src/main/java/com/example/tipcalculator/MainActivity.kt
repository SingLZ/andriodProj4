package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.example.tipcalculator.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tipSeekBar.setOnSeekBarChangeListener(object :
            android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tipPercentageLabel.text = "Tip: $progress%"
                calculateTip()
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })

        binding.baseAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = calculateTip()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.resetButton.setOnClickListener {
            binding.baseAmountEditText.text.clear()
            binding.tipSeekBar.progress = 15
            binding.tipPercentageLabel.text = "Tip: 15%"
            binding.tipResultTextView.text = "Tip: $0.00"
            binding.totalResultTextView.text = "Total: $0.00"
        }

        calculateTip()

    }

    private fun calculateTip() {
        val baseAmountText = binding.baseAmountEditText.text.toString()
        val tipPercentage = binding.tipSeekBar.progress

        if (baseAmountText.isEmpty()) {
            binding.tipResultTextView.text = "Tip: $0.00"
            binding.totalResultTextView.text = "Total: $0.00"
            return
        }

        val baseAmount = baseAmountText.toDoubleOrNull() ?: 0.0
        val tipAmount = baseAmount * tipPercentage / 100
        val totalAmount = baseAmount + tipAmount

        binding.tipResultTextView.text = "Tip: $%.2f".format(tipAmount)
        binding.totalResultTextView.text = "Total: $%.2f".format(totalAmount)
    }
}