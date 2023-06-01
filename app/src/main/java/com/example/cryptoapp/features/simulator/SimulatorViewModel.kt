package com.example.cryptoapp.features.simulator

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.R
import com.example.cryptoapp.common.fragments.highlights.HighlightsFragment
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.common.models.FixedAssets

class SimulatorViewModel : ViewModel() {
    private var currentAmount: String = ""
    private var currentDate: String = ""

    private val _currentAsset = MutableLiveData<Asset>()
    val asset: LiveData<Asset> = _currentAsset

    private val _onAmountError = MutableLiveData<String?>()
    val onAmountError: LiveData<String?> = _onAmountError

    private val _onDateError = MutableLiveData<String?>()
    val onDateError: LiveData<String?> = _onDateError

    private val _datePriceInTittle = MutableLiveData<String>()
    val datePriceInTittle: LiveData<String> = _datePriceInTittle

    private val _resultTittle = MutableLiveData<String>()
    val resultTittle: LiveData<String> = _resultTittle

    private val _resultPrice = MutableLiveData<String>()
    val resultPrice: LiveData<String> = _resultPrice

    private val _priceIn = MutableLiveData<String>()
    val priceIn: LiveData<String> = _priceIn

    private val _currentPrice = MutableLiveData<String>()
    val currentPrice: LiveData<String> = _currentPrice

    val viewState = MutableLiveData<SimulatorFragment.State>()

    init {  }

    fun loadFirstAsset() {
        _currentAsset.value = FixedAssets.BTC()
        viewState.value = SimulatorFragment.State.STAND_BY
    }

    fun updateAsset(asset: Asset) {
        _currentAsset.value = asset
    }

    fun updateAmount(amount: String, context: Context) {
        currentAmount = amount
        _onAmountError.value = if (!validateAmount() && amount.isNotEmpty()) context.getString(R.string.invalid_amount) else null
        checkFields()
    }

    fun updateDate(date: String, context: Context) {
        currentDate = date
        _onDateError.value = if (!validateDate() && date.length == 10) context.getString(R.string.invalid_date) else null
        checkFields()
    }

    private fun validateAmount(): Boolean {
        val regex = """^([1-9][0-999999999999]{0,1})${'$'}""".toRegex()
        return regex.matches(currentAmount)
    }

    private fun validateDate(): Boolean {
        val regex = """^([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/(\d{4})$""".toRegex()
        return regex.matches(currentDate)
    }

    private fun checkFields() {
        viewState.value = if (validateAmount() && validateDate()) SimulatorFragment.State.TO_SAVE else SimulatorFragment.State.STAND_BY
    }

    fun priceInDate(date: String, context: Context) {
        if(date.isNotEmpty()) {
            val dateIn = context.getString(R.string.simulator_price_in)
            _datePriceInTittle.value = "$dateIn $date"
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
//        if(_resultPrice.value!!.toDouble() > 0.0) {
//            _resultColor.value = R.color.green_100
//            _resultTittle.value = "$resultTittle $resultProfit"
//        } else if(_resultPrice.value!!.toDouble().equals(0.0)){
//            _resultColor.value = R.color.tertiary_100
//            _resultTittle.value = "$resultTittle $resultProfit"
//        } else {
//            _resultColor.value = R.color.secondary_100
//            _resultTittle.value = "$resultTittle $resultLoses"
//        }
    }
}