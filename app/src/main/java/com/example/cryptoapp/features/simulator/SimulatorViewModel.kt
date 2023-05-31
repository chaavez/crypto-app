package com.example.cryptoapp.features.simulator

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.R

class SimulatorViewModel : ViewModel() {
    private val _saveButtonColor = MutableLiveData<Int>()
    val saveButtonColor: LiveData<Int> = _saveButtonColor

    private val _saveButtonError = MutableLiveData<String?>()
    val saveButtonError: LiveData<String?> = _saveButtonError

    private val _datePriceInTittle = MutableLiveData<String>()
    val datePriceInTittle: LiveData<String> = _datePriceInTittle

    private val _resultTittle = MutableLiveData<String>()
    val resultTittle: LiveData<String> = _resultTittle

    private val _resultColor = MutableLiveData<Int>()
    val resultColor: LiveData<Int> = _resultColor

    private val _resultPrice = MutableLiveData<String>()
    val resultPrice: LiveData<String> = _resultPrice

    private val _priceIn = MutableLiveData<String>()
    val priceIn: LiveData<String> = _priceIn

    private val _currentPrice = MutableLiveData<String>()
    val currentPrice: LiveData<String> = _currentPrice

    init {
        _saveButtonColor.value = R.color.primary_300
    }

    fun priceInDate(date: String, context: Context) {
        if(date.isNotEmpty()) {
            val dateIn = context.getString(R.string.simulator_price_in)
            _datePriceInTittle.value = "$dateIn $date"
        }
    }

    fun saveInWallet(amount: String, date: String, context: Context) {
        val isDateValid = validateDate(date)

        if(!isDateValid && date.length == 10) {
            val dateError = context.getString(R.string.invalid_date)
            _saveButtonError.value = dateError
        } else {
            _saveButtonError.value = null
        }

        if(amount.isNotEmpty() && amount != "0" && isDateValid) {
            _saveButtonColor.value = R.color.secondary_200
        } else {
            _saveButtonColor.value = R.color.primary_300
        }
    }

    fun princeIn(amount: String, value: String, context: Context) {
        val moneySymbol = context.getString(R.string.simulator_money_symbol)
        if(amount > 0.0.toString()) {
            val multiply = amount.toDouble() * value.toDouble()
            _priceIn.value = "$moneySymbol $multiply"
        }
    }

    fun currentPrice(amount: String, value: String, context: Context) {
        val moneySymbol = context.getString(R.string.simulator_money_symbol)
        if(amount > 0.0.toString()) {
            val multiply = amount.toDouble() * value.toDouble()
            _currentPrice.value = "$moneySymbol $multiply"
        }
    }

    fun resultPrice(context: Context) {
        val moneySymbol = context.getString(R.string.simulator_money_symbol)
        val priceInValue = _priceIn.value?.substringAfter(moneySymbol)?.trim()?.toDouble() ?: 0.0
        val currentPriceValue = _currentPrice.value?.substringAfter(moneySymbol)?.trim()?.toDouble() ?: 0.0
        val result = priceInValue - currentPriceValue
        _resultPrice.value = "$moneySymbol $result"
    }

    fun resultColorAndTittle(context: Context) {
        val resultTittle = context.getString(R.string.simulator_result_price)
        val resultLoses = context.getString(R.string.simulator_would_lose)
        val resultProfit = context.getString(R.string.simulator_would_invoice)
        if(_resultPrice.value!!.toDouble() > 0.0) {
            _resultColor.value = R.color.green_100
            _resultTittle.value = "$resultTittle $resultProfit"
        } else if(_resultPrice.value!!.toDouble().equals(0.0)){
            _resultColor.value = R.color.tertiary_100
            _resultTittle.value = "$resultTittle $resultProfit"
        } else {
            _resultColor.value = R.color.secondary_100
            _resultTittle.value = "$resultTittle $resultLoses"
        }
    }

    fun validateDate(date: String): Boolean {
        val regex = """^([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/(\d{4})$""".toRegex()
        return regex.matches(date)
    }
}