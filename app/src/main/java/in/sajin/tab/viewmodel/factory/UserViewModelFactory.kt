package `in`.sajin.tab.viewmodel.factory

import `in`.sajin.tab.model.User
import `in`.sajin.tab.viewmodel.UserViewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory

class UserViewModelFactory(private val context: Context, private val user: User) : NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(context, user) as T
    }

}