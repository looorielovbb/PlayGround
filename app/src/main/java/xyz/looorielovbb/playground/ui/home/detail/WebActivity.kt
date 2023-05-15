package xyz.looorielovbb.playground.ui.home.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import xyz.looorielovbb.playground.databinding.ActivityWebBinding
import xyz.looorielovbb.playground.ext.binding

class WebActivity : AppCompatActivity() {

    private val binding by binding(ActivityWebBinding::inflate)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setSupportActionBar(toolbar)
            //设置点击事件必须在后面，不然不生效
            toolbar.setNavigationOnClickListener {
                finish()
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val link: String = intent.getStringExtra("link") ?: ""
            Log.d(TAG, "link: ${link}")
            if (TextUtils.isEmpty(link)) {
                Toast.makeText(this@WebActivity, "地址为空", Toast.LENGTH_LONG).show()
            }
            webView.loadUrl(link)

            webView.webChromeClient = object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    this@WebActivity.supportActionBar?.title = title ?: ""
                }


            }
            webView.settings.javaScriptEnabled = true
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            webView.settings.blockNetworkImage = false
        }
    }

    companion object {
        const val TAG = "WebActivity"
    }
}