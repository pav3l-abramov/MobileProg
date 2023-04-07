package mmcs.assignment3_calculator.viewmodel

import android.app.Application
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

var len = 0

class CalculatorViewModel(application: Application) : AndroidViewModel(application), Calculator {
    override var display = MutableLiveData<String>()

    init {
        display.value = ""
    }

    override fun addDigit(dig: Int) {
        if (display.value == "division by zero")display.value = ""
        display.value = display.value + dig.toString()


    }

    override fun addPoint() {
        val input = display.value!!
        if (!input.substring(len).contains(".") && display.value!!.length!= len) display.value = display.value + "."
    }

    override fun addOperation(op: Operation) {
        if (display.value?.isEmpty() == true && op == Operation.SUB) {
            display.value += "-"
            return
        }
        if (display.value?.isEmpty() == true) return
        if (!isNumeric(display.value!!.takeLast(1))) {
            display.value = display.value!!.dropLast(1)

        }
        display.value = display.value + when (op) {
            Operation.MUL -> '*'
            Operation.DIV -> '/'
            Operation.ADD -> '+'
            Operation.SUB -> '-'
        }
        len = display.value!!.length
        return
    }

    fun isNumeric(s: String): Boolean {
        return s.chars().allMatch { Character.isDigit(it) }
    }

    override fun compute() {
        if (display.value?.isEmpty() == true) return
        val input = display.value!!
        var currentNum = ""
        var res = 0.0
        var nums = mutableListOf<Float>()
        var operations = mutableListOf<String>()
        for (i in 0..display.value!!.length - 1) {
            if (isNumeric(input[i].toString()) || input[i].toString() == "." || input[i].toString() == "-") {
                currentNum += input[i].toString()
                if (input[i].toString() == "-") {
                    if (i != 0) {
                        operations.add("+")
                        nums.add(currentNum.dropLast(1).toFloat())
                        currentNum = "-"
                    }
                }
            } else {
                nums.add(currentNum.toFloat())
                currentNum = ""
                operations.add(input[i].toString())
            }
        }
        nums.add(currentNum.toFloat())

        res = nums[0].toDouble()
        for (i in operations.indices) {
            if (operations[i] == "+")
                res += nums[i + 1]
            if (operations[i] == "*")
                res *= nums[i + 1]
            if (operations[i] == "/") {
                if (nums[i + 1].toDouble() == 0.0) {
                    display.value = "division by zero"
                    return
                }
                res /= nums[i + 1]
            }
        }
        display.value = res.toString()

    }

    override fun clear() {
        display.value = ""
    }

    override fun reset() {
        display.value = ""
    }
}