package com.kraker.easylauncher

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.app.ActivityCompat.startActivityForResult

class Utils {

    companion object {

        const val GALLERY_APP_REQUEST_CODE = 21

        fun launchAppUsingPackageName(context : Context,packageName: String) {
            val pm = context.packageManager
            val intent: Intent? = pm.getLaunchIntentForPackage(packageName)
            intent?.addCategory(Intent.CATEGORY_LAUNCHER)
            if(intent!=null){
                context.startActivity(intent)
            }
        }

        fun getActivityIcon(context: Context, packageName: String, activityName: String): Drawable {
            val pm = context.packageManager
            val intent = Intent()
            intent.component = ComponentName(packageName, activityName)
            val resolveInfo = pm.resolveActivity(intent, 0)
            return resolveInfo.loadIcon(pm)
        }

        fun launchGalleryApp(activity: Activity) {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(activity, intent, GALLERY_APP_REQUEST_CODE, null)
        }

        fun launchContactApp(context: Context) {
            val intent = Intent()
            intent.component = ComponentName("com.android.contacts", "com.android.contacts.DialtactsContactsEntryActivity")
            intent.action = "android.intent.action.MAIN"
            intent.addCategory("android.intent.category.LAUNCHER")
            intent.addCategory("android.intent.category.DEFAULT")
            context.startActivity(intent)
        }

        fun launchPhoneApp(context: Context) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + "")
            context.startActivity(intent)
        }

        fun launchPhotoApp(context: Context) {
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            context.startActivity(intent)
        }

        fun launchMessageApp(context: Context) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.type = "vnd.android-dir/mms-sms"
            context.startActivity(intent)
        }
    }

}
