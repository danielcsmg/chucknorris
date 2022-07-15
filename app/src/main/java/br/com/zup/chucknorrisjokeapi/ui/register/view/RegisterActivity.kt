package br.com.zup.chucknorrisjokeapi.ui.register.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.zup.chucknorrisjokeapi.databinding.ActivityRegisterBinding
import br.com.zup.chucknorrisjokeapi.domain.model.User
import br.com.zup.chucknorrisjokeapi.ui.cathegoryjoke.view.CategoryJokeActivity
import br.com.zup.chucknorrisjokeapi.ui.register.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        registerUser()
        initObservers()
    }

    private fun initObservers() {
        viewModel.registerState.observe(this) {
            goToJoke(it)
        }

        viewModel.errorState.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun goToJoke(user: User) {
        val intent = Intent(this, CategoryJokeActivity::class.java).apply {
            putExtra("USER_KEY", user)
        }
        startActivity(intent)
    }

    private fun registerUser() {
        binding.btnRegister.setOnClickListener {
            val user = getUserData()
            viewModel.validateDataUser(user)
        }
    }

    private fun getUserData(): User {
        return User(
            email = binding.etEmailRegister.text.toString(),
            password = binding.etPasswordRegister.text.toString()
        )
    }
}