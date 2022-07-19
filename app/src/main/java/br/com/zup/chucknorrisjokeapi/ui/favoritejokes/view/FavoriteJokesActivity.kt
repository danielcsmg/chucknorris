package br.com.zup.chucknorrisjokeapi.ui.favoritejokes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.chucknorrisjokeapi.databinding.ActivityFavoriteJokesBinding
import br.com.zup.chucknorrisjokeapi.ui.cathegoryjoke.view.adapter.FavoriteJokeAdapter
import br.com.zup.chucknorrisjokeapi.ui.favoritejokes.viewmodel.FavoriteJokeViewModel

class FavoriteJokesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteJokesBinding
    private val viewModel: FavoriteJokeViewModel by lazy {
        ViewModelProvider(this)[FavoriteJokeViewModel::class.java]
    }

    private val adapter: FavoriteJokeAdapter by lazy {
        FavoriteJokeAdapter(hashMapOf(), this::deleteFavoriteJoke)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteJokesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        getRV()
        viewModel.getListFavorite()
        observers()
    }

    private fun deleteFavoriteJoke(id: String){
        viewModel.removeImageFavorite(id)
    }

    private fun getRV(){
        binding.rvFavoriteJoke.adapter = adapter
        binding.rvFavoriteJoke.layoutManager = LinearLayoutManager(this)
    }

    private fun observers(){
        viewModel.favoriteListState.observe(this){
            adapter.updateListJoke(it)
        }
    }
}