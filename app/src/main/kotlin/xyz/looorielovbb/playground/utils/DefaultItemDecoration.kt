package xyz.looorielovbb.playground.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import xyz.looorielovbb.playground.ext.dpToPx

class DefaultItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        val itemCount =  parent.layoutManager?.itemCount ?:0
        val pos = parent.getChildAdapterPosition(view)
        if (pos == 0) {
            outRect.set(0, 12.dpToPx(), 0, 12.dpToPx())
        } else {
            outRect.set(0, 0, 0, 12.dpToPx())
        }
    }
}