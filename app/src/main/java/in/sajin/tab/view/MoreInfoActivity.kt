package `in`.sajin.tab.view

import `in`.sajin.tab.R
import `in`.sajin.tab.databinding.ActivityMoreInfoBinding
import `in`.sajin.tab.viewmodel.NewWebViewModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

class MoreInfoActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MoreInfoActivity::class.java)
            context.startActivity(starter)
        }
    }

    private lateinit var viewModel: NewWebViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMoreInfoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_more_info)
        viewModel = ViewModelProvider(this).get(NewWebViewModel::class.java)

        //assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.webViewModel = viewModel
        binding.lifecycleOwner = this


        /**
         * Create instance for ViewModel class
         */
        viewModel = ViewModelProvider(this).get(NewWebViewModel::class.java)

        /**
         * Set ViewModel instance to binding class
         */
        //webView.settings.javaScriptEnabled = true
        viewModel.webViewUrl.value = "https://www.theaccessgroup.com/en-gb/"
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
