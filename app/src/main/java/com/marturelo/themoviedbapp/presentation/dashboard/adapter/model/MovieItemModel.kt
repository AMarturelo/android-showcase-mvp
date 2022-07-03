package com.marturelo.themoviedbapp.presentation.dashboard.adapter.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.marturelo.themoviedbapp.R
import com.marturelo.themoviedbapp.ext.dp
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO

@EpoxyModelClass
abstract class MovieItemModel : EpoxyModelWithHolder<MovieItemModel.Holder>() {

    @EpoxyAttribute
    var itemClickedListener: (MovieVO) -> Unit = {}

    @EpoxyAttribute
    lateinit var item: MovieVO

    override fun getDefaultLayout(): Int = R.layout.layout_movie_item_model

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.tvMovieTitle.text = item.title
        holder.tvMovieReleaseDate.text = item.release_date.toString()
        holder.tvMovieAverageRating.text = item.vote_average.toString()
        Glide.with(holder.root)
            .load(item.posterUrl)
            .transform(CenterCrop(), RoundedCorners(16.dp))
            .into(holder.ivMovieCover)

        holder.root.setOnClickListener {
            itemClickedListener(item)
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var root: View
        lateinit var ivMovieCover: ImageView
        lateinit var tvMovieTitle: TextView
        lateinit var tvMovieReleaseDate: TextView
        lateinit var tvMovieAverageRating: TextView
        override fun bindView(itemView: View) {
            root = itemView
            ivMovieCover = itemView.findViewById(R.id.ivMovieCover)
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle)
            tvMovieReleaseDate = itemView.findViewById(R.id.tvMovieReleaseDate)
            tvMovieAverageRating = itemView.findViewById(R.id.tvMovieAverageRating)
        }

    }
}