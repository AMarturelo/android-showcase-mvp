package com.marturelo.themoviedbapp.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.marturelo.themoviedbapp.R
import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.ext.dp
import com.marturelo.themoviedbapp.ext.releasedDate
import com.marturelo.themoviedbapp.presentation.core.BaseDaggerMVPFragment
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import com.marturelo.themoviedbapp.presentation.details.vo.PayloadVO
import kotlinx.android.synthetic.main.fragment_details.btBack
import kotlinx.android.synthetic.main.fragment_details.ivBackdrop
import kotlinx.android.synthetic.main.fragment_details.ivPoster
import kotlinx.android.synthetic.main.fragment_details.tvMovieReleaseDate
import kotlinx.android.synthetic.main.fragment_details.tvMovieTitle
import kotlinx.android.synthetic.main.fragment_details.tvOverviews

class DetailsFragment :
    BaseDaggerMVPFragment<DetailsContract.View, DetailsContract.Presenter>(),
    DetailsContract.View {

    override val layout: Int
        get() = R.layout.fragment_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()

        if (presenter.payload != null) {
            presenter.restore()
        } else {
            restoreStateOrInit(savedInstanceState)
        }

        setupHero()
    }

    private fun setupHero() {
        presenter.payload?.run {
            ivPoster.transitionName = "cover${movie.id}"
            tvMovieTitle.transitionName = "title${movie.id}"
        }
    }

    private fun setupListeners() {
        btBack.setOnClickListener {
            presenter.onBackClicked()
        }
    }

    private fun restoreStateOrInit(savedInstanceState: Bundle? = null) {
        requireArguments().run {
            if (savedInstanceState?.containsKey(Constants.InstanceState.DETAILS) == true) {
                val payload: PayloadVO =
                    savedInstanceState.getParcelable(Constants.InstanceState.DETAILS)!!
                presenter.restoreFromPayload(payload)
            } else {
                val movie =
                    this.getParcelable<MovieVO>(Constants.Arg.MOVIE)!!
                presenter.initWithPayload(PayloadVO(movie))
            }
        }
    }

    override fun updateUI(payload: PayloadVO) {
        Glide.with(this)
            .load(payload.movie.backdropUrl)
            .transform(CenterCrop())
            .into(ivBackdrop)

        Glide.with(this)
            .load(payload.movie.posterUrl)
            .transform(CenterCrop(), RoundedCorners(16.dp))
            .into(ivPoster)

        tvMovieTitle.text = payload.movie.title
        tvOverviews.text = payload.movie.overview
        tvMovieReleaseDate.text = payload.movie.release_date.releasedDate()
    }
}