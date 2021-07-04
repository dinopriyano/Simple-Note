package com.dupat.note.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class GridSpacingItemDecoration(val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val (spanCount, spanIndex, spanSize) = extractGridData(parent, view)
        val position = parent.getChildAdapterPosition(view)

        outRect.left = (spacing * ((spanCount - spanIndex) / spanCount.toFloat())).toInt()
        outRect.right = (spacing * ((spanIndex + spanSize) / spanCount.toFloat())).toInt()
        outRect.bottom = spacing
        if(position in 0..1){
            outRect.top = spacing
        }
    }

    private fun extractGridData(parent: RecyclerView, view: View): GridItemData {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            return extractGridLayoutData(layoutManager, view)
        } else if (layoutManager is StaggeredGridLayoutManager) {
            return extractStaggeredGridLayoutData(layoutManager, view)
        } else {
            throw UnsupportedOperationException("Bad layout params")
        }
    }

    private fun extractGridLayoutData(layoutManager: GridLayoutManager, view: View): GridItemData {
        val lp: GridLayoutManager.LayoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        return GridItemData(
            layoutManager.spanCount,
            lp.spanIndex,
            lp.spanSize
        )
    }

    private fun extractStaggeredGridLayoutData(layoutManager: StaggeredGridLayoutManager, view: View): GridItemData {
        val lp: StaggeredGridLayoutManager.LayoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        return GridItemData(
            layoutManager.spanCount,
            lp.spanIndex,
            if (lp.isFullSpan) layoutManager.spanCount else 1
        )
    }

    internal data class GridItemData(val spanCount: Int, val spanIndex: Int, val spanSize: Int)
}