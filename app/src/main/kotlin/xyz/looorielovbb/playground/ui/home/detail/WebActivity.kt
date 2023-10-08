package xyz.looorielovbb.playground.ui.home.detail

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.webkit.GeolocationPermissions
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.addCallback
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
            if (TextUtils.isEmpty(link)) {
                Toast.makeText(this@WebActivity, "地址为空", Toast.LENGTH_LONG).show()
            }
            WebView.setWebContentsDebuggingEnabled(true)
            webView.webChromeClient = mWebChromeClient
            webView.webViewClient = mWebViewClient
            webView.settings.apply {

                javaScriptEnabled = true
                mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                blockNetworkImage = false
                blockNetworkLoads = false
                defaultTextEncodingName = "UTF-8"
                javaScriptCanOpenWindowsAutomatically = true
                //适应手机屏幕
                useWideViewPort = true
                loadWithOverviewMode = true
                layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                //允许缩放
                setSupportZoom(false)
                builtInZoomControls = false
                setGeolocationEnabled(true)
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
            }
            webView.loadUrl(link)
        }
        onBackPressedDispatcher.addCallback {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else {
                finish()
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
            return true
        }

        override fun onProgressChanged(webView: WebView?, process: Int) {
            super.onProgressChanged(webView, process)
        }

        override fun onGeolocationPermissionsShowPrompt(
            origin: String?,
            callback: GeolocationPermissions.Callback?
        ) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                val dialog = AlertDialog.Builder(this@WebActivity)
                    .setTitle("温馨提示")
                    .setMessage("当前网页正在请求定位")
                    .setPositiveButton(
                        "授权"
                    ) { _, _ ->
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ), PERMISSION_REQUEST_GEO_LOCATION
                        )
                    }
                    .create()
                dialog.show()
            }

        }

        override fun onGeolocationPermissionsHidePrompt() {
        }
    }

    /**
     * 处理各种通知 & 请求事件
     */
    private val mWebViewClient = object : WebViewClient() {

        /**
         * 跳转拦截处理
         * 打开网页时，不调用系统浏览器进行打开，而是在本WebView中直接显示
         */
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return false
        }

        @SuppressLint("WebViewClientOnReceivedSslError")
        override fun onReceivedSslError(
            webView: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            //忽略ssl错误
            handler?.proceed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_GEO_LOCATION -> {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {//COARSE_LOCATION 粗略的位置
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("提示")
                    builder.setMessage("粗略位置权限授权失败。")
                    builder.setPositiveButton("好的", null)
                    builder.show()
                }
                if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {//FINE_LOCATION 精确的位置
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("提示")
                    builder.setMessage("精确位置权限授权失败。")
                    builder.setPositiveButton("好的", null)
                    builder.show()
                }
            }
            else ->{
                Log.e(TAG, "wrong requestCode")
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_GEO_LOCATION = 0x01
        const val TAG = "WebActivity"
    }

}