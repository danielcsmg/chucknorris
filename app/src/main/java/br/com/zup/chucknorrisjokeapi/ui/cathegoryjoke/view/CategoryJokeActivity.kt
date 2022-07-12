package br.com.zup.chucknorrisjokeapi.ui.cathegoryjoke.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.zup.chucknorrisjokeapi.RANDOM
import br.com.zup.chucknorrisjokeapi.URL_CHUCK_NORRIS
import br.com.zup.chucknorrisjokeapi.databinding.ActivityCathegoryJokeBinding
import br.com.zup.chucknorrisjokeapi.ui.cathegoryjoke.viewmodel.JokeViewModel
import br.com.zup.desafiorickemorty.ui.viewstate.ViewState
import com.squareup.picasso.Picasso

class CategoryJokeActivity : AppCompatActivity() {
    private val spinnerArray: ArrayList<String> by lazy {
        arrayListOf(RANDOM)
    }
    private val viewModel: JokeViewModel by lazy {
        ViewModelProvider(this)[JokeViewModel::class.java]
    }

    private lateinit var binding: ActivityCathegoryJokeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCathegoryJokeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getRandomJoke(RANDOM)
        viewModel.getCategory()
        Picasso.get().load(URL_CHUCK_NORRIS).into(binding.ivChuckNoris)
        binding.btnNewJoke.setOnClickListener {
            val category = binding.spnCathegory.selectedItem.toString()
            viewModel.getRandomJoke(category)
        }
        observer()
        observerCathegory()
    }

    private fun observer(){
        viewModel.jokeResponse.observe(this){
            when(it){
                is ViewState.Success -> {
                    binding.tvJoke.text = it.data.value
                }
                is ViewState.Error -> {
                    binding.tvJoke.text = it.throwable.message
                }
            }
        }
    }

    private fun observerCathegory() {
        viewModel.jokeCathegoryResponse.observe(this){
            when(it){
                is ViewState.Success -> {
                    spinnerArray.addAll(it.data)
                    setSpinner()
                }
                is ViewState.Error -> {
                    Toast.makeText(this, it.throwable.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setSpinner(){

        val arraySpinner = spinnerArray.toList()
        val arrayAdapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            arraySpinner
        )
        binding.spnCathegory.adapter = arrayAdapter
    }
}