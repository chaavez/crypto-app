//package com.example.cryptoapp.features.simulator
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//
//class SimulatorViewModelFactory(private val repository: SimulatorRepository) : ViewModelProvider.Factory {
//    override fun <T: ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SimulatorRepository::class.java)) {
//            return SimulatorViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown SimulatorViewModel class")
//    }
//}