package com.xwvzdvpize.ytlqflattq.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel:ViewModel() {
    lateinit var expression: Expression
    private val _resultLiveData: MutableLiveData<String> = MutableLiveData()
    val resultLiveData: LiveData<String>
        get() = _resultLiveData
    private val _inputLiveData: MutableLiveData<String> = MutableLiveData()
    val inputLiveData: LiveData<String>
        get() = _inputLiveData


    fun evaluateExpression(exp: String) {

        try {
            expression = ExpressionBuilder(exp).build()
            _resultLiveData.value = expression.evaluate().toString()

        } catch (e: Exception) {
            _inputLiveData.value =exp
            _resultLiveData.value=""
        }
    }

    fun evaluateBackSpace(exp: String) {
        if (exp.isNotEmpty()) {
            val text = exp.dropLast(1)
            if (text.isNotEmpty()) {
                _inputLiveData.value = text
                val last = text.last()
               if (last.isDigit()){
                    evaluateExpression(text)
                }else if(!last.isDigit()&&text.length>1){
                    val operator= text.dropLast(1)
                   evaluateExpression(operator)
            }else{
                _resultLiveData.value=""

            }


            }else{
                _resultLiveData.value=""
                _inputLiveData.value=""
            }
        }
    }
}