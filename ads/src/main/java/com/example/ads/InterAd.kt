package com.example.ads

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object InterAd {

    const val tag = "INTERSTITIAL_AD"
    private var admobInter: InterstitialAd? = null
    var isInterstitialAdShowing = false

    //Ads Ids
    var highFloorAdUnitId = ""
    var mediumFloorAdUnitId = ""
    var lowFloorAdUnitId = ""


    fun loadAd(
        activity: Activity,
        adUnitId: String,
        callback: ((Boolean) -> Unit)? = null
    ) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(activity, adUnitId, adRequest,
            object : InterstitialAdLoadCallback() {

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e(tag, "Loading Ad: onAdFailedToLoad -> " + adError.message + adError.code)
                    admobInter = null
                    callback?.invoke(false)
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.e(tag, "Loading Ad: onAdLoaded.")
                    admobInter = interstitialAd
                    callback?.invoke(true)
                }
            }
        )
    }

    fun showAd(
        activity: Activity,
        callback: (() -> Unit)? = null
    ) {
        if (admobInter != null) {
            isInterstitialAdShowing = true
            admobInter?.show(activity)
        } else {
            Log.e(tag, "Show Ad: Ad object is null.")
            isInterstitialAdShowing = false
            callback?.invoke()
        }

        admobInter?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.e(tag, "Show Ad: onAdDismissedFullScreenContent")
                isInterstitialAdShowing = false
                callback?.invoke()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.e(tag, "Show Ad: Failed -> " + adError.message + adError.code)
                isInterstitialAdShowing = false
                callback?.invoke()
            }

            override fun onAdShowedFullScreenContent() {
                Log.e(tag, "Show Ad: onAdShowedFullScreenContent")
                isInterstitialAdShowing = false
                admobInter = null
            }
        }
    }


    fun setFloorIds(
        highFloorAdUnitId: String,
        mediumFloorAdUnitId: String,
        lowFloorAdUnitId: String
    ) {
        InterAd.highFloorAdUnitId = highFloorAdUnitId
        InterAd.mediumFloorAdUnitId = mediumFloorAdUnitId
        InterAd.lowFloorAdUnitId = lowFloorAdUnitId
    }

}