package com.xr95.githubuploader

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var web: WebView
    private val termuxPackage = "com.termux"
    private val runPermission = "com.termux.permission.RUN_COMMAND"
    private val termuxHome = "/data/data/com.termux/files/home"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.allowFileAccess = true
        web.webChromeClient = WebChromeClient()
        web.webViewClient = WebViewClient()
        web.addJavascriptInterface(Bridge(), "Android")
        setContentView(web)
        web.loadUrl("file:///android_asset/index.html")
    }

    inner class Bridge {
        @JavascriptInterface fun start() { runOnUiThread { runTermux("start.sh") } }
        @JavascriptInterface fun stop() { runOnUiThread { runTermux("stop.sh") } }
        @JavascriptInterface fun restart() { runOnUiThread { runTermux("restart.sh") } }
        @JavascriptInterface fun openTermuxSettings() {
            runOnUiThread {
                try {
                    startActivity(Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:$termuxPackage")))
                } catch (_: Exception) {
                    Toast.makeText(this@MainActivity, "Termux parametrlərini açmaq mümkün olmadı", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun runTermux(script: String) {
        if (!packageInstalled(termuxPackage)) {
            Toast.makeText(this, "Termux tapılmadı. Quraşdırmanı yoxla.", Toast.LENGTH_LONG).show()
            return
        }
        val scriptPath = "$termuxHome/glgonder/$script"
        val intent = Intent("com.termux.RUN_COMMAND").apply {
            setClassName(termuxPackage, "com.termux.app.RunCommandService")
            putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/usr/bin/bash")
            putExtra("com.termux.RUN_COMMAND_ARGUMENTS", arrayOf(scriptPath))
            putExtra("com.termux.RUN_COMMAND_WORKDIR", "$termuxHome/glgonder")
            putExtra("com.termux.RUN_COMMAND_BACKGROUND", true)
        }
        try {
            startService(intent)
            Toast.makeText(this, "$script Termux-a göndərildi", Toast.LENGTH_SHORT).show()
            web.evaluateJavascript("showMessage('Əmr Termux-a göndərildi. Serverin açılması bir neçə saniyə çəkə bilər.')", null)
        } catch (e: SecurityException) {
            Toast.makeText(this, "RUN_COMMAND icazəsi verilməyib. Termux və APK icazələrini yoxla.", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Termux əmri göndərilmədi: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun packageInstalled(pkg: String): Boolean {
        return try { packageManager.getPackageInfo(pkg, 0); true } catch (_: Exception) { false }
    }

    override fun onBackPressed() {
        if (web.canGoBack()) web.goBack() else super.onBackPressed()
    }
}
