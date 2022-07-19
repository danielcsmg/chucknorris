package br.com.zup.chucknorrisjokeapi.ui.favoritejokes.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.chucknorrisjokeapi.domain.repository.FavoriteJokeRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FavoriteJokeViewModel: ViewModel() {
    private val favoriteJokeRepository = FavoriteJokeRepository()

    private var _favoriteListState = MutableLiveData<HashMap<String, String>>()
    val favoriteListState: LiveData<HashMap<String, String>> = _favoriteListState

    private var _messageState = MutableLiveData<String>()
    val messageState: LiveData<String> = _messageState

    fun getListFavorite(){
        favoriteJokeRepository.getListJokeFavorite()
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val favoriteList = hashMapOf<String, String>()

                    for (resultSnapshot in snapshot.children){
                        val favoriteResponse = resultSnapshot.value.toString()
                        val favoriteKey = resultSnapshot.key.toString()
                        favoriteList[favoriteKey] = favoriteResponse
                    }
                    _favoriteListState.value = favoriteList
                }

                override fun onCancelled(error: DatabaseError) {
                    _messageState.value = error.message
                }
            })
    }

    fun removeImageFavorite(jokeId: String?){
        val path: String? = jokeId
        favoriteJokeRepository.databaseReferences().child("$path").removeValue()
    }
}