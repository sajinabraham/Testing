package `in`.sajin.tab.view

import `in`.sajin.tab.R
import `in`.sajin.tab.databinding.ActivityLoginBinding
import `in`.sajin.tab.model.User
import `in`.sajin.tab.viewmodel.UserViewModel
import `in`.sajin.tab.viewmodel.factory.UserViewModelFactory
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class LoginActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Create instance for data binding auto generated class file
         */
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        /**
         * Create instance for ViewModel class
         */

        val userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(this, User())
        ).get(UserViewModel::class.java)

        /**
         * Set ViewModel instance to binding class
         */
        binding.userViewModel = userViewModel
    }
}