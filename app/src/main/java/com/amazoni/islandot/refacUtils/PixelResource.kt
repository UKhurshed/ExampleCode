package com.guraj.ghadeou.metricaUtils

import com.amazoni.islandot.refacUtils.PixelStatus

data class PixelResource<out T>(
    val status: PixelStatus,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> pixelSuccess(data: T): PixelResource<T> =
            PixelResource(status = PixelStatus.SUCCESS, data = data, message = null)

        fun <T> pixelError(data: T?, message: String): PixelResource<T> =
            PixelResource(status = PixelStatus.ERROR, data = data, message = message)

        fun <T> pixelLoading(data: T?): PixelResource<T> =
            PixelResource(status = PixelStatus.LOADING, data = data, message = null)
    }
}