package com.amazoni.islandot.refacUtils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class RefacPref(context: Context) {
    private val refacSharedFileName = "shared_prefer_name"
    private val refacKeyDeepLink = "deep_link"
    private val refacRemoteKey = "remote"
    private val refacClick = "click"
    private val refacWebCampaign = "app_campaign"
    private val refacDeqUrl = "dequeWebViewUrl"
    private val refacBoolean = "statePressed"

    //subs preferences
    private val campaignWeb = "webCampaignId"
    private val sub1 = "sub1"
    private val sub2 = "sub2"
    private val sub3 = "sub3"
    private val sub4 = "sub4"
    private val sub5 = "sub5"
    private val adID = "ad_id"
    private val adSetID = "adset_id"
    private val campaignID = "campaign_id"
    private val appsStatus = "af_status"
    private val adsClientID = "adsClientID"
    private val appsID = "AppsFlyerID"
    private val oneSignalTag = "sentOneSignal"


    private val refacSharedPrefs: SharedPreferences =
        context.getSharedPreferences(refacSharedFileName, Context.MODE_PRIVATE)

    var refacPressed: Boolean
        get() = refacSharedPrefs.getBoolean(refacBoolean, false)
        set(value) = refacSharedPrefs.edit { putBoolean(refacBoolean, value) }

    var refacMyDeep: String
        get() = refacSharedPrefs.getString(refacKeyDeepLink, "") ?: ""
        set(value) = refacSharedPrefs.edit { putString(refacKeyDeepLink, value) }

    var remoteKey: String
        get() = refacSharedPrefs.getString(refacRemoteKey, "") ?: ""
        set(value) = refacSharedPrefs.edit { putString(refacRemoteKey, value) }

    var clickId: String
        get() = refacSharedPrefs.getString(refacClick, "") ?: ""
        set(value) = refacSharedPrefs.edit { putString(refacClick, value) }

    var appCampaign: String
        get() = refacSharedPrefs.getString(refacWebCampaign, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(refacWebCampaign, value) }

    //sub preferences
    var campaignWebID: String
        get() = refacSharedPrefs.getString(campaignWeb, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(campaignWeb, value) }

    var firstSub: String
        get() = refacSharedPrefs.getString(sub1, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(sub1, value) }

    var secondSub: String
        get() = refacSharedPrefs.getString(sub2, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(sub2, value) }

    var thirdSub: String
        get() = refacSharedPrefs.getString(sub3, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(sub3, value) }

    var fourthSub: String
        get() = refacSharedPrefs.getString(sub4, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(sub4, value) }

    var fifthSub: String
        get() = refacSharedPrefs.getString(sub5, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(sub5, value) }

    var adverID: String
        get() = refacSharedPrefs.getString(adID, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(adID, value) }

    var adverSetID: String
        get() = refacSharedPrefs.getString(adSetID, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(adSetID, value) }

    var campID: String
        get() = refacSharedPrefs.getString(campaignID, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(campaignID, value) }

    var apStatus: String
        get() = refacSharedPrefs.getString(appsStatus, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(appsStatus, value) }


//    private val adsClientID = "adsClientID"
//    private val appsID = "AppsFlyerID"

    var advertiseID: String
        get() = refacSharedPrefs.getString(adsClientID, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(adsClientID, value) }

    var appsFlyID: String
        get() = refacSharedPrefs.getString(appsID, "null") ?: "null"
        set(value) = refacSharedPrefs.edit { putString(appsID, value) }

    var deqUrlWeb: String
        get() = refacSharedPrefs.getString(refacDeqUrl, "") ?: ""
        set(value) = refacSharedPrefs.edit { putString(refacDeqUrl, value) }

    var sentOneSignal: Boolean
        get() = refacSharedPrefs.getBoolean(oneSignalTag, false)
        set(value) = refacSharedPrefs.edit { putBoolean(oneSignalTag, value) }

}
