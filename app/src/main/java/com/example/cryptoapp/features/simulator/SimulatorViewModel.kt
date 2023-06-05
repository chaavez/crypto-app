package com.example.cryptoapp.features.simulator

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.R
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.common.models.FixedAssets
import java.text.SimpleDateFormat
import java.util.*

class SimulatorViewModel(private val repository: SimulatorRepository) : ViewModel() {
    private var currentAmount: String = ""
    private var currentDate: String = ""


    val viewState = MutableLiveData<SimulatorFragment.State>()

    private val _currentAsset = MutableLiveData<Asset>()
    val asset: LiveData<Asset> = _currentAsset

    private val _onAmountError = MutableLiveData<String?>()
    val onAmountError: LiveData<String?> = _onAmountError

    private val _onDateError = MutableLiveData<String?>()
    val onDateError: LiveData<String?> = _onDateError

    val assetPricesFormatter =  MutableLiveData<AssetPricesFormatter>()

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
        val regex = """^([1-9][0-999999999999]*)${'$'}""".toRegex()
        return regex.matches(currentAmount)
    }

    private fun validateDate(): Boolean {
        val regex = """^([0-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/(20[0-9]{2}|20[0-9]{2})$""".toRegex()
        return regex.matches(currentDate)
    }

    private fun checkFields() {
        if (validateAmount() && validateDate()) {
            viewState.value = SimulatorFragment.State.LOADING
            _currentAsset.value?.let { currentAsset ->
                repository.fetchAsset(currentAsset.symbol, convertDate(currentDate)) { oldAsset ->
                    assetPricesFormatter.value = AssetPricesFormatter(currentDate, oldAsset, currentAsset)
                    viewState.value = SimulatorFragment.State.TO_SAVE
                }
            }
        } else {
            viewState.value = SimulatorFragment.State.STAND_BY
        }
    }

    private fun convertDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }
}

class AssetPricesFormatter(
    val oldAssetDate: String,
    val oldAssetPrice: String,
    val currentAssetPrice: String,
    val resultPrice: String,
    val resultVariation: String,
    var resultType: ResultType) {

    enum class ResultType {
        POSITIVE,
        NEGATIVE,
        SAME
    }

    constructor(oldAssetDate: String, oldAsset: Asset, currentAsset: Asset) : this(
        "Preço em $oldAssetDate",
        "R$ ${String.format("%,.2f", oldAsset.price)}",
        "R$ ${String.format("%,.2f", currentAsset.price)}",
        "R$ ${String.format("%,.2f", currentAsset.price - oldAsset.price)}",
        "${String.format("%.0f",(currentAsset.price - oldAsset.price) / oldAsset.price * 100)}%",
        ResultType.SAME
    ) {
        setupResultType(oldAsset, currentAsset)
    }

    private fun setupResultType(oldAsset: Asset, currentAsset: Asset) {
        val difference = currentAsset.price - oldAsset.price

        resultType = when {
            difference == 0.0 -> ResultType.SAME
            difference > 0.0 -> ResultType.POSITIVE
            else -> ResultType.NEGATIVE
        }
    }
}