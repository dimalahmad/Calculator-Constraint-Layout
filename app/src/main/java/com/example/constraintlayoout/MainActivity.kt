package com.example.constraintlayoout

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var displayOperation: TextView
    private lateinit var displayResult: TextView
    private var currentInput = ""
    private var operation = ""
    private var result = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayOperation = findViewById(R.id.tvFormula)
        displayResult = findViewById(R.id.tvResult)

        val buttonIds = listOf(
            R.id.zero, R.id.one, R.id.two, R.id.three,
            R.id.four, R.id.five, R.id.six, R.id.seven,
            R.id.eight, R.id.nine, R.id.plus, R.id.mines,
            R.id.multiply, R.id.divide, R.id.percent, R.id.dot,
            R.id.clear, R.id.equal
        )

        buttonIds.forEach { id ->
            findViewById<TextView>(id).setOnClickListener { onButtonClick(it as TextView) }
        }
    }

    private fun onButtonClick(button: TextView) {
        val value = button.text.toString()

        when (value) {
            "+", "-", "x", "/", "%", "=" -> {
                if (currentInput.isNotEmpty()) {
                    result = if (operation.isEmpty()) {
                        currentInput.toDouble()
                    } else {
                        calculateResult()
                    }
                    currentInput = ""
                    operation = if (value == "=") {
                        ""
                    } else {
                        value
                    }
                    displayOperation.text = "$result $operation"
                    if (value == "=") {
                        displayResult.text = result.toString()
                        displayOperation.text = ""
                    }
                } else if (value == "=") {
                    calculateResult()
                    displayResult.text = result.toString()
                    displayOperation.text = ""
                }
            }
            "C" -> clear()
            else -> {
                currentInput += value
                displayOperation.text = "$result $operation $currentInput"
            }
        }
    }

    private fun calculateResult(): Double {
        val currentValue = currentInput.toDouble()

        result = when (operation) {
            "+" -> result + currentValue
            "-" -> result - currentValue
            "x" -> result * currentValue
            "/" -> result / currentValue
            "%" -> result % currentValue
            else -> result
        }

        return result
    }

    private fun clear() {
        currentInput = ""
        operation = ""
        result = 0.0
        displayOperation.text = ""
        displayResult.text = "0"
    }
}
