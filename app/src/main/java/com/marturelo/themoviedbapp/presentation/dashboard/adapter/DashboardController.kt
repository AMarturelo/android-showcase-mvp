package com.marturelo.themoviedbapp.presentation.dashboard.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.marturelo.themoviedbapp.presentation.dashboard.adapter.model.MovieItemModel_
import com.marturelo.themoviedbapp.presentation.dashboard.vo.PayloadVO

class DashboardController :
    TypedEpoxyController<PayloadVO>() {

    override fun buildModels(data: PayloadVO) {
        for (item in data.items) {
            MovieItemModel_()
                .id(item.id)
                .item(item)
                .addTo(this)
        }
    }
}