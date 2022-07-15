package br.com.zup.chucknorrisjokeapi.ui.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.zup.chucknorrisjokeapi.databinding.ActivityLoginBinding
import br.com.zup.chucknorrisjokeapi.domain.model.User
import br.com.zup.chucknorrisjokeapi.ui.cathegoryjoke.view.CategoryJokeActivity
import br.com.zup.chucknorrisjokeapi.ui.login.viewmodel.LoginViewModel
import br.com.zup.chucknorrisjokeapi.ui.register.view.RegisterActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        goToRegister()
        login()
        initObserver()
    }

    private fun goToRegister() {
        binding.tvRegisterNow.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login(){
        binding.btnRegister.setOnClickListener {
            val user = getUserData()
            viewModel.validate(user)
        }
    }

    private fun initObserver() {
        viewModel.loginState.observe(this) {
            goToJoke(it)
        }

        viewModel.errorState.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getUserData(): User {
        return User(
            email = binding.etEmailRegister.text.toString(),
            password = binding.etPasswordRegister.text.toString()
        )
    }

    private fun goToJoke(user: User) {
        val intent = Intent(this, CategoryJokeActivity::class.java).apply {
            putExtra("USER_KEY", user)
        }
        startActivity(intent)
    }
}