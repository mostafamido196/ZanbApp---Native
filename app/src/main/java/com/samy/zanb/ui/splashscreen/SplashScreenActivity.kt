package com.samy.zanb.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.samy.zanb.R
import com.samy.zanb.ui.main.MainActivity
import com.samy.zanb.utils.Constants
import com.samy.zanb.utils.CustomNotificationUtils
import com.samy.zanb.utils.Utils.myLog

class SplashScreenActivity : AppCompatActivity() {
    var isOpenFromNotification = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        isOpenFromNotification = intent.getIntExtra(CustomNotificationUtils.EXTRA_NOTIFICATION, -1)
        myLog("-----------------------------------------------------------")
        myLog("Splashscreen: isOpenFromNotification: $isOpenFromNotification")
        Handler().postDelayed({
            val i = Intent(
                this,
                MainActivity::class.java
            )
            i.putExtra(Constants.ISAUTOOPENDNOTIFICATION,isOpenFromNotification)
            startActivity(i)
            finish()
        }, 2000)
    }
}