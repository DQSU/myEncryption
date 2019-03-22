package gateway.hitrontech.com.encryption.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WhichContext : Application() {

    override fun onCreate() {
        super.onCreate()
        WhichContext.instance = applicationContext
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        var instance: Context? = null
            private set
    }
}
