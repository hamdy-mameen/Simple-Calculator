package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.simplecalculator.databinding.ActivityMainBinding
import com.xwvzdvpize.ytlqflattq.viewmodel.CalculatorViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var isLastCharDigit = false;
    var resultText:String?=null

    private val viewModel: CalculatorViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etInput.movementMethod= ScrollingMovementMethod()
        viewModel.resultLiveData.observe(this, Observer { result ->
            result?.let {
                binding.etResult.setText(it)
                resultText =it
            }

        })
        viewModel.inputLiveData.observe(this, Observer {input->
            input?.let {
                binding.etInput.setText(it)
                if(it.isNotEmpty()&&it.last().isDigit()){
                    isLastCharDigit=true
                }
            }
        })
        binding.zeroBtn.setOnClickListener {
          appendDigits("0")

        }
        binding.oneBtn.setOnClickListener {
            appendDigits("1")

        }
        binding.twoBtn.setOnClickListener {
            appendDigits("2")

        }
        binding.threeBtn.setOnClickListener {
            appendDigits("3")

        }
        binding.fourBtn.setOnClickListener {
            appendDigits("4")

        }
        binding.fiveBtn.setOnClickListener {
            appendDigits("5")

        }
        binding.sixBtn.setOnClickListener {
            appendDigits("6")

        }
        binding.sevenBtn.setOnClickListener {
            appendDigits("7")

        }
        binding.eightBtn.setOnClickListener {
            appendDigits("8")

        }
        binding.nineBtn.setOnClickListener {
            appendDigits("9")

        }
        binding.divisionBtn.setOnClickListener {
            appendOperators("/")
        }
        binding.multiplicationBtn.setOnClickListener {
            appendOperators("*")
        }
        binding.minusBtn.setOnClickListener {
            appendOperators("-")
        }
        binding.plusBtn.setOnClickListener {
            appendOperators("+")
        }
        binding.percentBtn.setOnClickListener {
            appendOperators("%")
        }
        binding.dotBtn.setOnClickListener {
            binding.etInput.text.append(".").toString()
            isLastCharDigit=false

        }
        binding.acBtn.setOnClickListener {
            binding.etInput.setText("")
            isLastCharDigit=false
            binding.etResult.setText("")
        }
        binding.backspaceBtn.setOnClickListener {
            viewModel.evaluateBackSpace(binding.etInput.text.toString())
        }
        binding.equalBtn.setOnClickListener {
            if (isLastCharDigit){
                if(resultText==null) binding.etInput.setText("") else binding.etInput.setText(resultText)
                binding.etResult.setText("")
            }
        }

    }
    private fun appendDigits(digit:String){
        viewModel.evaluateExpression(binding.etInput.text.append(digit).toString())
        isLastCharDigit = true
    }
    private fun appendOperators(operator:String){
        if (binding.etInput.text.toString().isNotEmpty() && isLastCharDigit) {
            binding.etInput.text.append(operator).toString()
            isLastCharDigit = false
        }
    }
}