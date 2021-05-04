package com.renatohack.renato_hack_dr4_tp1.SegundaAtividade

import android.icu.number.Notation.simple
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.renatohack.renato_hack_dr4_tp1.R
import kotlinx.android.synthetic.main.activity_tela_resultado.*

class TelaResultado : AppCompatActivity() {

    private lateinit var viewModel: TelaResultadoViewModel
    private lateinit var viewModelFactory: TelaResultadoViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_resultado)

        viewModelFactory = TelaResultadoViewModelFactory(this, this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TelaResultadoViewModel::class.java)

        val filesObserver = Observer<Array<String>> { files ->
            // Update the UI, in this case, a TextView.
            if (files.isNotEmpty()){
                listViewLocation.adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, files)
            }
        }
        viewModel.files.observe(this, filesObserver)

        viewModel.getFiles()

    }
}