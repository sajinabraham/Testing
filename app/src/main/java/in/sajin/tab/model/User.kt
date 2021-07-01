package `in`.sajin.tab.model

import android.text.TextUtils
import android.util.Patterns
import java.io.Serializable

class User : Serializable {
    var username: String? = null
    var password: String? = null
    val isValidUsername: Boolean
        get() = username != null && !TextUtils.isEmpty(username)
    val isValidPassword: Boolean
        get() = password != null && password!!.length >= 9
    val isValidCredential: Boolean
        get() = username.equals("testuser", ignoreCase = true) && password == "Password1"
    val welcomeMessage: String
        get() = """
               Hi from Access Group
               ${username}
               """.trimIndent()
}