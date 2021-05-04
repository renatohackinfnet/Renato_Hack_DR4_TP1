package com.renatohack.renato_hack_dr4_tp1.PrimeiraAtividade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.renatohack.renato_hack_dr4_tp1.R
import com.renatohack.renato_hack_dr4_tp1.SegundaAtividade.TelaResultado
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelFactory =
            MainViewModelFactory(
                this,
                this
            )
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        buttonStartLocation.setOnClickListener {
            viewModel.callAccessLocation()
        }

        buttonRegister.setOnClickListener {
            viewModel.callWriteOnSDCard()
        }

        buttonList.setOnClickListener {
            if (viewModel.callReadFromSDCard()){
                val intent = Intent(this, TelaResultado::class.java)
                startActivity(intent)
            }
        }
    }
}