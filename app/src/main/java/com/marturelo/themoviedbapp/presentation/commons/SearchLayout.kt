package com.marturelo.themoviedbapp.presentation.commons

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import com.marturelo.themoviedbapp.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.layout_search.view.searchView

class SearchLayout : ConstraintLayout {

    var onFocusChange: ((Boolean) -> Unit)? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        View.inflate(context, R.layout.layout_search, this)

        setupListeners()
    }

    private fun setupListeners() {
        searchView.setOnQueryTextFocusChangeListener { _, focus ->
            onFocusChange?.invoke(focus)
        }
    }

}

fun SearchLayout.rxQuery(): Observable<String> {
    return Observable.create { emitter ->
        this.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                emitter.onNext(newText ?: "")
                return true
            }
        })
    }
}