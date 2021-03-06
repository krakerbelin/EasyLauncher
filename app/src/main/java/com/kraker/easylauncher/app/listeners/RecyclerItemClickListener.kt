package com.kraker.easylauncher.app.listeners
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerItemClickListener (private val mRecycler: RecyclerView, private val clickListener: OnClickListener? = null, private val longClickListener: OnLongClickListener? = null) : RecyclerView.OnChildAttachStateChangeListener {

    override fun onChildViewDetachedFromWindow(view: View?) {
        view?.setOnClickListener(null)
        view?.setOnLongClickListener(null)
    }

    override fun onChildViewAttachedToWindow(view: View?) {
        view?.setOnClickListener { v -> setOnChildAttachedToWindow(v) }
    }

    private fun setOnChildAttachedToWindow(view: View?) {
       view?.let {
            val position = mRecycler.getChildLayoutPosition(it)
            if (position >= 0) {
                clickListener?.onItemClick(position, it)
                longClickListener?.onLongItemClick(position, it)
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(position: Int, view: View)
    }

    interface OnLongClickListener {
        fun onLongItemClick(position: Int, view: View)
    }
}
