package br.com.zup.chucknorrisjokeapi.ui.cathegoryjoke.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.zup.chucknorrisjokeapi.R
import br.com.zup.chucknorrisjokeapi.RANDOM
import br.com.zup.chucknorrisjokeapi.URL_CHUCK_NORRIS
import br.com.zup.chucknorrisjokeapi.databinding.ActivityCathegoryJokeBinding
import br.com.zup.chucknorrisjokeapi.ui.cathegoryjoke.viewmodel.JokeViewModel
import br.com.zup.chucknorrisjokeapi.ui.favoritejokes.view.FavoriteJokesActivity
import br.com.zup.chucknorrisjokeapi.ui.login.view.LoginActivity
import br.com.zup.desafiorickemorty.ui.viewstate.ViewState
import com.google.android.material.snackbar.Snackbar
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
    }

    override fun onResume() {
        super.onResume()

        showUser()
        viewModel.getRandomJoke(RANDOM)
        viewModel.getCategory()
        Picasso.get().load(URL_CHUCK_NORRIS).into(binding.ivChuckNoris)
        binding.btnNewJoke.setOnClickListener {
            val category = binding.spnCathegory.selectedItem.toString()
            viewModel.getRandomJoke(category)
        }
        clickButtonFavoriteJokes()
        favoriteJoke()
        observer()
        observerCategory()
    }

    private fun favoriteJoke() {
        binding.cvJoke.setOnClickListener {
            viewModel.saveFavoriteJoke()
        }
    }

    private fun clickButtonFavoriteJokes() {
        binding.btnFavorite.setOnClickListener {
            goToFavoriteJokes()
        }
    }

    private fun observer() {
        viewModel.jokeResponse.observe(this) {
            when (it) {
                is ViewState.Success -> {
                    binding.tvJoke.text = it.data.value
                    viewModel.getCurrentJoke(it.data)
                }
                is ViewState.Error -> Snackbar.make(
                    binding.root,
                    "${it.throwable.message}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.message.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun observerCategory() {
        viewModel.jokeCategoryResponse.observe(this) {
            when (it) {
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

    private fun setSpinner() {
        val arraySpinner = spinnerArray.toList()
        val arrayAdapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            arraySpinner
        )
        binding.spnCathegory.adapter = arrayAdapter
    }

    private fun showUser() {
        val userLogin = viewModel.getUserMail()
        binding.tvUserLogin.text = getString(R.string.login_user, userLogin)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {
                viewModel.logout()
                finish()
                goToLogin()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToFavoriteJokes(){
        startActivity(Intent(this, FavoriteJokesActivity::class.java))
    }
}