package `in`.sajin.tab.model

import `in`.sajin.tab.viewmodel.UserViewModel
import android.content.Context
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat


import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserTest {

    /**
     *
     * when then Input is not empty
     */
    @Test
    fun whenInputIsValid() {
        val username = "testuser"
        val password = "Password1"
        val result = Validator.validate(username, password)
        assertThat(result).isEqualTo(true)
    }

    /**
     *
     * when then Input is empty
     */
    @Test
    fun whenInputIsInValid() {
        val username = ""
        val password = ""
        val result = Validator.validate(username, password)
        assertThat(result).isEqualTo(false)
    }

}