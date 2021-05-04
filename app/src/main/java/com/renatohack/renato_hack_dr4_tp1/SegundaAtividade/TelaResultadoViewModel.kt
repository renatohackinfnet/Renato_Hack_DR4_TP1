package com.renatohack.renato_hack_dr4_tp1.SegundaAtividade

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renatohack.renato_hack_dr4_tp1.PrimeiraAtividade.MainActivity
import java.io.File

class TelaResultadoViewModel(val context: Context, val activity: TelaResultado) : ViewModel() {

    val path = context.getExternalFilesDir(null)

    private val _files = MutableLiveData<Array<String>>()
    val files : LiveData<Array<String>> = _files


    fun getFiles() {
        lateinit var files : Array<String>

        if (path != null) {
            files = path.list()
        }
        _files.value = files
    }

}