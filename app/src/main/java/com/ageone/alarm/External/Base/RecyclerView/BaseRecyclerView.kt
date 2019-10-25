package com.ageone.alarm.External.Base.RecyclerView

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ageone.alarm.Application.currentActivity

open class BaseRecyclerView: RecyclerView(currentActivity as Context) {

}

open class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

}

abstract class BaseAdapter<T : BaseViewHolder>: RecyclerView.Adapter<T>(){
    abstract override fun getItemCount(): Int
    abstract override fun getItemViewType(position: Int): Int
    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T
    abstract override fun onBindViewHolder(holder: T, position: Int)
}

class NonscrollRecylerView : BaseRecyclerView() {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
            Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST
        )
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom)
        val params = layoutParams
        params.height = measuredHeight
    }
}