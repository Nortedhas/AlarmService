package com.ageone.alarm.Application

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ageone.alarm.External.Base.Activity.BaseActivity
import com.ageone.alarm.Models.User.user
import com.swarmnyc.promisekt.Promise
import timber.log.Timber
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.legacy.content.WakefulBroadcastReceiver
import com.ageone.alarm.Application.Service.AlarmService
import com.ageone.alarm.R
import com.ageone.alarm.SCAG.DataBase
import com.github.kittinunf.fuel.core.FuelManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AppActivity: BaseActivity() {

    var fusedLocationClient: FusedLocationProviderClient? = null
    var locationRequest: LocationRequest? = null
    var locationCallback: LocationCallback? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        intent = Intent(this,AlarmService::class.java)
        //only vertical mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // for launchScreen
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        //fullscreen flags
        window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        setDisplaySize()



        FuelManager.instance.basePath = "http://176.119.157.149/"

        verifyStoragePermissions(this)

        user.isAuthorized = false //TODO: change after add registration
        coordinator.setLaunchScreen()

        Promise<Unit> { resolve, _ ->

            router.layout.setOnApplyWindowInsetsListener { _, insets ->
                utils.variable.statusBarHeight = insets.systemWindowInsetTop

                resolve(Unit)
                insets
            }

        }.then {
            api.handshake {
                startService(intent)
                //webSocket.initialize()
                coordinator.start()
            }
        }

        setContentView(router.layout)

    }

    private fun setDisplaySize() {
        val displayMetrics = resources.displayMetrics
        utils.variable.displayWidth = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        utils.variable.displayHeight = (displayMetrics.heightPixels / displayMetrics.density).toInt()

        Timber.i("width = ${utils.variable.displayWidth}")

        // Calculate ActionBar height
        val tv = TypedValue()
        if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            utils.variable.actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics) / 3
        }
    }

    override fun onBackPressed() {
        Timber.i("back")
        router.onBackPressed()
    }

}

fun Activity.hideKeyboard() {
    // Check if no view has focus
    val view = currentFocus
    view?.let { v ->
        //hide keyboard
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}

fun Activity.setStatusBarColor(color: Int) {
    window.statusBarColor = color
}

// Storage Permissions
private val REQUEST_EXTERNAL_STORAGE = 1
private val PERMISSIONS_STORAGE = arrayOf<String>(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

/**
 * Checks if the app has permission to write to device storage
 *
 * If the app does not has permission then the user will be prompted to grant permissions
 *
 * @param activity
 */
fun verifyStoragePermissions(activity: Activity) {
    // Check if we have write permission
    val permission =
        ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    if (permission != PackageManager.PERMISSION_GRANTED) {
        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(
            activity,
            PERMISSIONS_STORAGE,
            REQUEST_EXTERNAL_STORAGE
        )
    }
}

class OnBootBroadcastReceiver : BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {
        var intent = Intent("com.ageone.alarm.MyFirebaseMessagingService")
        intent.setClass(p0, MyFirebaseMessagingService::class.java)
        p0?.startService(intent)
    }
}
