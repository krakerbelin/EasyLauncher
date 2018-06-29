package com.kraker.easylauncher.ui

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import com.kraker.easylauncher.R
import com.kraker.easylauncher.Utils
import com.kraker.easylauncher.app.extensions.onItemClick
import com.kraker.easylauncher.app.listeners.RecyclerItemClickListener
import com.kraker.easylauncher.domain.data.MenuObject
import com.kraker.easylauncher.domain.service.NotificationListenerExampleService.Companion.NOTIFICATION_CODE_KEY
import com.kraker.easylauncher.domain.service.NotificationListenerExampleService.InterceptedNotificationCode.SMS_CODE
import com.kraker.easylauncher.ui.adapters.MenuRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
        const val ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
    }

    private val COLUMN_NUMBER = 2
    private var listOfItems = ArrayList<MenuObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        initListener()
        initNotificationListener()
    }

    private fun initNotificationListener() {
        if (!isNotificationServiceEnabled()) {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle(R.string.notification_listener_service)
            alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation)
            alertDialogBuilder.setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialog, id -> startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS)) })
            alertDialogBuilder.setNegativeButton(R.string.no, DialogInterface.OnClickListener { dialog, id -> })
            alertDialogBuilder.create().show()
        }

        var notificationBroadcastReceiver = NotificationBroadcastReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(packageName)
        registerReceiver(notificationBroadcastReceiver, intentFilter)
    }

    private fun initUI() {
        var contactItem = MenuObject()
        contactItem.itemName = "Contact"
        contactItem.icon = getDrawable(R.drawable.ic_contact)
        listOfItems.add(contactItem)

        var phoneItem = MenuObject()
        phoneItem.itemName = "Telephone"
        phoneItem.icon = getDrawable(R.drawable.ic_phone)
        listOfItems.add(phoneItem)

        var applicationsItem = MenuObject()
        applicationsItem.itemName = "Applications"
        applicationsItem.icon = getDrawable(R.drawable.ic_app_launcher)
        listOfItems.add(applicationsItem)

        var photoItem = MenuObject()
        photoItem.itemName = "Appareil photo"
        photoItem.icon = getDrawable(R.drawable.ic_photo)
        listOfItems.add(photoItem)

        var galeryItem = MenuObject()
        galeryItem.itemName = "Galerie"
        galeryItem.icon = getDrawable(R.drawable.ic_galery)
        listOfItems.add(galeryItem)

        var messageItem = MenuObject()
        messageItem.icon = getDrawable(R.drawable.ic_message)
        messageItem.itemName = "Messages"
        listOfItems.add(messageItem)

        menuRecycler.layoutManager = GridLayoutManager(this, COLUMN_NUMBER)
        menuRecycler.adapter = MenuRecyclerAdapter(listOfItems)
    }

    private fun initListener() {
        menuRecycler.onItemClick(object : RecyclerItemClickListener.OnClickListener {
            override fun onItemClick(position: Int, view: View) {
                when (position) {
                    0 -> Utils.launchContactApp(applicationContext)
                    1 -> Utils.launchPhoneApp(applicationContext)
                    3 -> Utils.launchPhotoApp(applicationContext)
                    4 -> Utils.launchGalleryApp(this@MainActivity)
                    5 -> Utils.launchMessageApp(applicationContext)
                    else -> {
                    }
                }
            }
        })
    }

    inner class NotificationBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val receivedNotificationCode = intent.getIntExtra(NOTIFICATION_CODE_KEY, -1)

            //TODO Handle all the notifications here
            when(receivedNotificationCode){
                SMS_CODE -> {
                    notificationTextView.text = getString(R.string.sms_received)
                } else -> {
                notificationTextView.text = getString(R.string.sms_received)
                }
            }
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat = Settings.Secure.getString(contentResolver, ENABLED_NOTIFICATION_LISTENERS)
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
