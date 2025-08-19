package com.doaa.mosalam.librarymanagementsystem

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    // This class is used to initialize Hilt and set up the application context
    // You can add any global initialization code here if needed
    override fun onCreate() {
        super.onCreate()
//        listenToNetworkConnectivity()
    }


//    @SuppressLint("CheckResult")
//    fun listenToNetworkConnectivity() {
//        ReactiveNetwork.observeInternetConnectivity().subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io()).subscribe { isConnected: Boolean ->
//                Log.d(TAG, "Connected to internet: $isConnected")
//                FirebaseCrashlytics.getInstance().setCustomKey("connected_to_internet", isConnected)
//            }
//    }
}


