package com.marturelo.themoviedbapp.presentation.search.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.IdUtils
import com.airbnb.epoxy.TypedEpoxyController
import com.marturelo.themoviedbapp.presentation.dashboard.adapter.model.MovieItemModel_
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import com.marturelo.themoviedbapp.presentation.search.vo.PayloadVO

class SearchController(
    val itemClickedListener: (MovieVO) -> Unit = {},
    private val recyclerView: RecyclerView
) :
    TypedEpoxyController<PayloadVO>() {

    override fun buildModels(data: PayloadVO) {
        for (item in data.items) {
            MovieItemModel_()
                .id(item.id.toString())
                .item(item)
                .itemClickedListener(itemClickedListener)
                .addTo(this)
        }
    }

    fun getViewById(id: String): View? {
        return adapter.getModelById(IdUtils.hashString64Bit(id))?.let {
            return@let (recyclerView.layoutManager as? LinearLayoutManager)?.findViewByPosition(
                adapter.getModelPosition(it)
            )
        }
    }
}