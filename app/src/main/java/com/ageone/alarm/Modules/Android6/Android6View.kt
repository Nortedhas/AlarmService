package com.example.ageone.Modules.Android6

import android.graphics.Color
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.ageone.alarm.External.Base.Button.BaseButton
import com.ageone.alarm.External.Base.Module.BaseModule
import com.ageone.alarm.External.Base.RecyclerView.BaseAdapter
import com.ageone.alarm.External.Base.RecyclerView.BaseViewHolder
import com.ageone.alarm.External.InitModuleUI
import com.ageone.alarm.R
import com.example.ageone.Modules.Android6.rows.Android6CardViewHolder
import com.example.ageone.Modules.Android6.rows.Android6TextViewHolder
import com.example.ageone.Modules.Android6.rows.initialize
import yummypets.com.stevia.*

class Android6View(initModuleUI: InitModuleUI = InitModuleUI()) : BaseModule(initModuleUI) {

    val viewModel = Android6ViewModel()

    val button by lazy {
        val button = BaseButton()
        button.textSize = 30F
        button.textColor = Color.WHITE
        button.typeface = Typeface.DEFAULT_BOLD
        button.cornerRadius = 15.dp
        button.text = "Call"
        button.backgroundColor = Color.parseColor("#FF0000")
        button.elevation = 3F.dp
        button.initialize()
        button
    }

    val viewAdapter by lazy {
        val viewAdapter = Factory(this)
        viewAdapter
    }

    init {
//        viewModel.loadRealmData()

        setBackgroundResource(R.drawable.base_background)
        renderToolbar()

        bodyTable.adapter = viewAdapter
//        bodyTable.overScrollMode = View.OVER_SCROLL_NEVER


        renderUIO()
        bindUI()
    }

    fun bindUI() {
        /*compositeDisposable.add(
            RxBus.listen(RxEvent.Event::class.java).subscribe {//TODO: change type event
                bodyTable.adapter?.notifyDataSetChanged()
            }
        )*/
    }

    inner class Factory(val rootModule: BaseModule) : BaseAdapter<BaseViewHolder>() {

        private val Android6TextType = 0
        private val Android6CardType = 1


        override fun getItemCount() = 3//viewModel.realmData.size

        override fun getItemViewType(position: Int): Int = when (position) {
            0 -> Android6TextType
            1 -> Android6CardType
            else -> -1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

            val layout = ConstraintLayout(parent.context)

            layout
                .width(matchParent)
                .height(wrapContent)

            val holder = when (viewType) {
                Android6TextType -> {
                    Android6TextViewHolder(layout)
                }
                Android6CardType -> {
                    Android6CardViewHolder(layout)
                }
                else -> {
                    BaseViewHolder(layout)
                }
            }

            return holder
        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

            when (holder) {
                is Android6TextViewHolder -> {
                    holder.initialize("David Kalanic","77921135675")
                }

                is Android6CardViewHolder -> {
                    holder.initialize("Mobile pocker app for use by a hotel, Mobile pocker app for use by a\n" +
                            "Mobile pocker app for use by a hotel ")
                }
            }

        }

    }

}

fun Android6View.renderUIO() {


    innerContent.subviews(
        bodyTable,
        button
    )

    bodyTable
        .fillHorizontally(0)
        .fillVertically()
        .constrainTopToTopOf(innerContent)

    button
        .height(70)
        .fillHorizontally(20)
        .constrainBottomToBottomOf(innerContent,30)

}


