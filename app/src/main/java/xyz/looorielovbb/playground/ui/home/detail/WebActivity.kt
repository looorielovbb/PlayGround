package xyz.looorielovbb.playground.ui.home.detail

import android.annotation.SuppressLint
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
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
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    finish()
                }
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val link: String = intent.getStringExtra("link") ?: ""
            Log.d(TAG, "link: ${link}")
            if (TextUtils.isEmpty(link)) {
                Toast.makeText(this@WebActivity, "地址为空", Toast.LENGTH_LONG).show()
            }
            webView.webChromeClient = mWebChromeClient
            webView.webViewClient = mWebViewClient
            webView.settings.apply {
                javaScriptEnabled = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                blockNetworkImage = false
                defaultTextEncodingName = "UTF-8"
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                //适应手机屏幕
                useWideViewPort = true
                loadWithOverviewMode = true
                layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                //无限放大
                setSupportZoom(false)
                builtInZoomControls = false
                setGeolocationEnabled(true)
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
                // 不使用缓存
                mixedContentMode = WebSettings.LOAD_NO_CACHE
                // 添加user-agent
                /*userAgentString =
                    "Mozilla/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) > " +
                            "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 > " +
                            "Chrome/37.0.0.0 Mobile Safari/537.36 > " +
                            "MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI"*/
            }
            webView.loadUrl(link)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    finish()
                }
            }
        }
    }

    /**
     * 辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
     */
    private val mWebChromeClient = object : WebChromeClient() {

        override fun onReceivedTitle(webView: WebView?, title: String?) {
            super.onReceivedTitle(webView, title)
            this@WebActivity.supportActionBar?.title = title ?: ""
        }

        override fun onShowFileChooser(
            webView: WebView?,
            back: ValueCallback<Array<Uri>>?,
            chooser: FileChooserParams?
        ): Boolean {
            return super.onShowFileChooser(webView, back, chooser)
        }

        override fun onProgressChanged(webView: WebView?, process: Int) {
            super.onProgressChanged(webView, process)
            if (process == 100) {
                //加载完成
                Log.d(TAG, "onProgressChanged: 加载完成")
            }
        }
    }

    /**
     * 处理各种通知 & 请求事件
     */
    private val mWebViewClient = object : WebViewClient() {
        /**
         * 网页加载完毕
         */
        override fun onPageFinished(webView: WebView?, url: String?) {
            super.onPageFinished(webView, url)
        }

        /**
         * 跳转拦截处理
         * 打开网页时，不调用系统浏览器进行打开，而是在本WebView中直接显示
         */
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        @SuppressLint("WebViewClientOnReceivedSslError")
        override fun onReceivedSslError(
            webView: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            super.onReceivedSslError(webView, handler, error)
            //忽略ssl错误
            handler?.proceed()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            finish()
        }
    }

    companion object {
        const val TAG = "WebActivity"
    }
}