package com.marturelo.themoviedbapp.presentation.commons

import android.content.Context
import android.widget.LinearLayout
import com.marturelo.themoviedbapp.R
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.appcompat.view.ContextThemeWrapper
import com.marturelo.themoviedbapp.ext.dp

class SegmentView : LinearLayout {
    private val selectedTextColor = R.color.tmdb_gray_black
    private val unSelectedTextColor = R.color.tmdb_white

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(mContext: Context, attrs: AttributeSet?) {
        orientation = HORIZONTAL
    }

    fun withVO(titles: List<SegmentVO>) {
        removeAllViews()

        for (i in titles.indices) {
            val param = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            val buttonStyle = R.style.SegmentedAppearance_Primary
            val btn = Button(ContextThemeWrapper(context, buttonStyle), null, buttonStyle)

            btn.text = titles[i].title

            btn.setOnClickListener { view: View ->
                onSegmentSelected?.invoke(i)
                handleClick(i, view)
            }
            if (i != 0) {
                param.setMargins(12.dp, 0, 0, 0)
            }

            addView(btn, param)

            if (titles[i].isSelected) {
                markSelected(btn)
            } else {
                markUnSelected(btn)
            }
        }
    }

    private fun handleClick(position: Int, view: View) {
        var btnView: Button?
        for (i in 0 until childCount) {
            btnView = getChildAt(i) as Button
            if (i == position) {
                markSelected(btnView)
            } else {
                markUnSelected(btnView)
            }
        }
    }

    private fun markSelected(btn: Button) {
        btn.setBackgroundResource(R.drawable.primary_rounded_button_checked)
        btn.setTextColor(context.getColor(selectedTextColor))
    }

    private fun markUnSelected(btn: Button) {
        btn.setBackgroundResource(R.drawable.primary_rounded_button_enable)
        btn.setTextColor(context.getColor(unSelectedTextColor))
    }

    var onSegmentSelected: ((Int) -> Unit)? = null

    companion object {
        const val TAG = "SegmentView"
    }

    data class SegmentVO(val title: String, val isSelected: Boolean)
}