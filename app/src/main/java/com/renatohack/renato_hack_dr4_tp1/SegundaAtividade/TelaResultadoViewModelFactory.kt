package com.renatohack.renato_hack_dr4_tp1.SegundaAtividade

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.renatohack.renato_hack_dr4_tp1.PrimeiraAtividade.MainActivity

class TelaResultadoViewModelFactory(val context: Context, val activity: TelaResultado) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TelaResultadoViewModel(context, activity) as T
    }
}