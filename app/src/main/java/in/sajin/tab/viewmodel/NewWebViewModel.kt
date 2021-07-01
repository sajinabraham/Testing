package `in`.sajin.tab.viewmodel

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewWebViewModel : ViewModel() {

    val webViewUrl = MutableLiveData<String>().apply{ value = "file:///android_asset/html_files/gallery_page.html" }

    companion object WebViewUrlLoader {
        @BindingAdapter("loadUrl")
        @JvmStatic
        fun  WebView.setUrl(url: String) {
            this.loadUrl(url)
            this.clearCache(true)
            this.clearHistory()
            this.settings.javaScriptEnabled = true
        }
    }
}
