package com.kraker.easylauncher.app.extensions

import android.support.v7.widget.RecyclerView
import com.kraker.easylauncher.app.listeners.RecyclerItemClickListener

/**
 * Called when the user click on a recycler's cell
 */
fun RecyclerView.onItemClick(listener: RecyclerItemClickListener.OnClickListener) {
    this.addOnChildAttachStateChangeListener(RecyclerItemClickListener(this, listener))
}

