package br.com.zup.chucknorrisjokeapi.domain.repository

import br.com.zup.chucknorrisjokeapi.FAVORITE_PATH
import br.com.zup.chucknorrisjokeapi.JOKE_PATH
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase

class FavoriteJokeRepository {
    private val auth: FirebaseAuth = Firebase.auth
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference("$JOKE_PATH/${auth.currentUser?.uid}/$FAVORITE_PATH")

    fun databaseReferences() = reference

    fun getListJokeFavorite(): Query {
        return reference.orderByKey()
    }
}