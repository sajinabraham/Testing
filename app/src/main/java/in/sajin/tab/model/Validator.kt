package `in`.sajin.tab.model

object Validator {
    fun validate(username: String, password: String): Boolean {
        return !(username.isEmpty() || password.isEmpty())
    }
}