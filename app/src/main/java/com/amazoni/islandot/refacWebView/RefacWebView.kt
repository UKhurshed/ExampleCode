package com.amazoni.islandot.refacWebView

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.amazoni.islandot.databinding.ActivityRefacWebViewBinding
import com.amazoni.islandot.refacUtils.RefacPref
import com.onesignal.OneSignal
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

class RefacWebView : AppCompatActivity() {
    private lateinit var refWebView: WebView
    private val refActivity = "RefWebView"
    private lateinit var webBinding: ActivityRefacWebViewBinding

    val INPUT_FILE_REQUEST_CODE = 1
    private var mFilePathCallback: ValueCallback<Array<Uri?>?>? = null
    private var mCameraPhotoPath: String? = null
    private lateinit var refWebPref: RefacPref
    private lateinit var context: Context

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webBinding = ActivityRefacWebViewBinding.inflate(layoutInflater)
        setContentView(webBinding.root)
        refWebPref = RefacPref(this)
        context = this

        val refMainUrl = intent.getStringExtra("refUrl")

        Log.i(refActivity, "Url: $refMainUrl")

        refInitOneSignal()

        refWebView = webBinding.refacWebView

        refWebView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
        }

        //yesnoMainUrl   https://affpros.net/?serial=5283&creative_id=315&anid= quizMainUrl
        refWebView.loadUrl(refMainUrl ?: "")

        refWebView.webChromeClient = object : WebChromeClient() {

        }

        refWebView.setDownloadListener { url, _, _, mimetype, _ ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.allowScanningByMediaScanner()

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) //Notify client once download is completed!
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mimetype)
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, ".png")
            val webView = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            webView.enqueue(request)
            Toast.makeText(applicationContext, "Downloading File", Toast.LENGTH_LONG).show()
        }

        refWebView.webChromeClient = object : WebChromeClient() {

            override fun onShowFileChooser(
                webView: WebView?, filePathCallback: ValueCallback<Array<Uri?>?>,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                mFilePathCallback?.onReceiveValue(null)
                mFilePathCallback = filePathCallback
                var takePictureIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent!!.resolveActivity(packageManager) != null) {
                    // Create the File where the photo should go
                    var photoFile: File? = null
                    try {
                        photoFile = createImageFile()
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath)
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        Log.e(refActivity, "Unable to create Image File", ex)
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.absolutePath
                        takePictureIntent.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile)
                        )
                    } else {
                        takePictureIntent = null
                    }
                }
                val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                contentSelectionIntent.type = "image/*"
                val intentArray: Array<Intent?> =
                    takePictureIntent?.let { arrayOf(it) } ?: arrayOfNulls(0)
                val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser")
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE)
                return true
            }
        }

//        yesnoWebView.webViewClient = object : WebViewClientCustom(firebaseAnalytics, "sdfs", "sdfs")
        refWebView.webViewClient = object : WebViewClient() {


            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }


            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var file: File? = null
        try {
            file = File.createTempFile(timeStamp, ".jpg", storageDir)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }


    override fun onBackPressed() {
        when {
            refWebView.canGoBack() -> refWebView.goBack()
            else -> {
                val spaceBuilder = AlertDialog.Builder(this)
                spaceBuilder.setTitle("Confirm")
                    .setMessage("Do you want exit this app")
                    .setPositiveButton("Yep") { _, _ ->
                        finishAffinity()
                        exitProcess(0)
                    }
                    .setNegativeButton("Nop") { _, _ ->
                    }
                val publicDialog: AlertDialog = spaceBuilder.create()
                publicDialog.show()
            }
        }
    }


    private fun refInitOneSignal() {
        val appId = ""
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(appId)
//        OneSignal.startInit(this)
//            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//            .unsubscribeWhenNotificationsAreDisabled(true)
//            .init()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, intent)
            return
        }

        var results: Array<Uri?>? = null
        // Check that the response is a good one
        if (resultCode == RESULT_OK) {
            if (intent == null) {
                // If there is not data, then we may have taken a photo
                if (mCameraPhotoPath != null) {
                    results = arrayOf(Uri.parse(mCameraPhotoPath))
                }
            } else {
                val dataString: String? = intent.dataString
                if (dataString != null) {
                    results = arrayOf(Uri.parse(dataString))
                }
            }
        }

        mFilePathCallback!!.onReceiveValue(results)
        mFilePathCallback = null
        return
    }
}