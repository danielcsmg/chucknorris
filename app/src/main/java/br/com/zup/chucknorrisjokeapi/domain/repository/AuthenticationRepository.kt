package br.com.zup.chucknorrisjokeapi.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationRepository {
    private val auth: FirebaseAuth = Firebase.auth

    fun registerUser(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

//    fun updateUserProfile(nome: String): Task<Void>? {
//        val profile = UserProfileChangeRequest.Builder().setDisplayName(nome).build()
//        return auth.currentUser?.updateProfile(profile)
//    }

    fun logout(){
        auth.signOut()
    }

    fun loginUser(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun getUserEmail(): String = auth.currentUser?.email.toString()
}