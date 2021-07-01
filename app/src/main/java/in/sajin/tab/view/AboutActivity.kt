package `in`.sajin.tab.view

import `in`.sajin.tab.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class AboutActivity : AppCompatActivity() {


    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AboutActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val button: Button = findViewById(R.id.btnEmail)

        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:sajinabraham8@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Android Developer Interview")
            intent.putExtra(Intent.EXTRA_TEXT, "Hi Sajin")
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}