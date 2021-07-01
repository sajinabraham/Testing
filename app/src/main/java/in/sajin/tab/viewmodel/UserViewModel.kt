package `in`.sajin.tab.viewmodel

import `in`.sajin.tab.R
import `in`.sajin.tab.model.User
import `in`.sajin.tab.view.ProfileActivity
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel(private val context: Context, private val user: User) : ViewModel() {
    @JvmField
    var username = MutableLiveData<String>()

    @JvmField
    var password = MutableLiveData<String>()

    @JvmField
    var welcome = MutableLiveData<String>()
    private var busy: MutableLiveData<Int>? = null

    /**
     * Get Mutable Data instance for progress bar
     * @return
     */
    fun getBusy(): MutableLiveData<Int> {
        if (busy == null) {
            busy = MutableLiveData()
            busy!!.value = View.GONE
        }
        return busy!!
    }

    /**
     * Event  for login button
     */
    fun onLoginClick() {
        user.username = username.value
        user.password = password.value
        if (!user.isValidUsername) {
            Toast.makeText(context, R.string.invalid_username, Toast.LENGTH_SHORT).show()
        } else if (!user.isValidPassword) {
            Toast.makeText(context, R.string.invalid_password, Toast.LENGTH_SHORT)
                .show()
        } else if (user.isValidCredential) {
            getBusy().value = View.VISIBLE
            Handler().postDelayed({
                getBusy().value = View.GONE
                Toast.makeText(context, R.string.login_successful, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, ProfileActivity::class.java)
                //           ProfileActivity.start(context, user)
                intent.putExtra("USER_OBJ", user)
                context.startActivity(intent)
                (context as Activity).finish()
            }, 500)
        } else {
            Toast.makeText(context, R.string.invalid_credentials, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     *   UserViewModel Constructor
     */
    init {
        welcome.value = user.welcomeMessage
    }
}