package com.renatohack.renato_hack_dr4_tp1.PrimeiraAtividade

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(val context: Context, val activity: MainActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            context,
            activity
        ) as T
    }
}