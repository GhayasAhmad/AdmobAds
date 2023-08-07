package com.example.ads

import android.app.Activity


fun Activity.loadInterWithFlooring(
    callback: ((Boolean) -> Unit)? = null
) {
    if (InterAd.highFloorAdUnitId.isNotEmpty()) {
        InterAd.loadAd(
            activity = this,
            adUnitId = InterAd.highFloorAdUnitId,
            callback = { highAd ->
                if (!highAd) {
                    InterAd.loadAd(
                        activity = this,
                        adUnitId = InterAd.mediumFloorAdUnitId,
                        callback = { mediumAd ->
                            if (!mediumAd) {
                                InterAd.loadAd(
                                    activity = this,
                                    adUnitId = InterAd.lowFloorAdUnitId,
                                    callback = {
                                        callback?.invoke(it)
                                    }
                                )
                            } else {
                                callback?.invoke(true)
                            }
                        }
                    )
                } else {
                    callback?.invoke(true)
                }
            }
        )
    } else if (InterAd.mediumFloorAdUnitId.isEmpty()) {
        InterAd.loadAd(
            activity = this,
            adUnitId = InterAd.mediumFloorAdUnitId,
            callback = { mediumAd ->
                if (!mediumAd) {
                    InterAd.loadAd(
                        activity = this,
                        adUnitId = InterAd.lowFloorAdUnitId,
                        callback = {
                            callback?.invoke(it)
                        }
                    )
                } else {
                    callback?.invoke(true)
                }
            }
        )
    } else {
        InterAd.loadAd(
            activity = this,
            adUnitId = InterAd.lowFloorAdUnitId,
            callback = {
                callback?.invoke(it)
            }
        )
    }

}


fun Activity.showInterAd(
    callback: (() -> Unit)? = null
) {
    InterAd.showAd(
        activity = this,
        callback = {
            callback?.invoke()
        }
    )
}





