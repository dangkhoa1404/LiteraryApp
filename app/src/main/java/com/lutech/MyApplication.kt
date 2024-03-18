package com.lutech

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import java.io.File


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        try {
            val dexOutputDir: File = codeCacheDir
            dexOutputDir.setReadOnly()

            myApplication = this

        }catch (e: Exception) {
            e.printStackTrace()
        }


    }
    private class AdjustLifecycleCallbacks : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        }

        override fun onActivityStarted(activity: Activity) {

        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {

        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
//        MultiDex.install(this)
    }
    companion object {

        var myApplication: MyApplication? = null

        fun getInstance(): MyApplication {
            if (myApplication == null) {
                myApplication = MyApplication()
            }
            return myApplication!!
        }
    }
}