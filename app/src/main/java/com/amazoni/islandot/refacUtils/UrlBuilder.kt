package com.amazoni.islandot.refacUtils

import android.net.Uri

class UrlBuilder {
    public fun builder(url:String): Uri {
        val baseUrl = Uri.parse(url)
        val builder: Uri.Builder = Uri.Builder()
        builder.scheme(baseUrl.scheme)
        builder.authority(baseUrl.host)
        builder.path(baseUrl.path)
        builder.clearQuery()
        return builder.build()
    }
}