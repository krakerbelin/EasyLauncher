package com.kraker.easylauncher.app.extensions
import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun Activity.addFragmentToActivity(fragmentManager: FragmentManager, fragment: Fragment, frameId: Int, shouldAddToStack: Boolean) {
    val transaction = fragmentManager.beginTransaction()
    transaction.add(frameId, fragment)
    if (shouldAddToStack) {
        transaction.addToBackStack(frameId.toString())
    }
    transaction.commit()
}
