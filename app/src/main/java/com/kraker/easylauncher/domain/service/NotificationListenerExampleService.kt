package com.kraker.easylauncher.domain.service

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationListenerExampleService : NotificationListenerService() {

    companion object {
        const val SMS_PACKAGE_NAME = "com.android.mms"
        const val NOTIFICATION_CODE_KEY = "notification_code_key"
    }

    object InterceptedNotificationCode {
        val SMS_CODE = 0
        val OTHER_NOTIFICATIONS_CODE = 1
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notificationCode = matchNotificationCode(sbn)
        if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {
            val intent = Intent(packageName)
            intent.putExtra(NOTIFICATION_CODE_KEY, notificationCode)
            sendBroadcast(intent)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        val notificationCode = matchNotificationCode(sbn)
        if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {
            activeNotifications?.let {
                for (i in it.indices) {
                    if (notificationCode == matchNotificationCode(it[i])) {
                        val intent = Intent(packageName)
                        intent.putExtra(NOTIFICATION_CODE_KEY, notificationCode)
                        sendBroadcast(intent)
                        break
                    }
                }
            }
        }
    }

    private fun matchNotificationCode(sbn: StatusBarNotification): Int {
        val packageName = sbn.packageName
        return if (packageName == SMS_PACKAGE_NAME) {
            InterceptedNotificationCode.SMS_CODE
        } else {
            InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE
        }
    }
}

