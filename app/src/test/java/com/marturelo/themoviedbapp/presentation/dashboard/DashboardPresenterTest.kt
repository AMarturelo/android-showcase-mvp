package com.marturelo.themoviedbapp.presentation.dashboard

import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import com.marturelo.themoviedbapp.domain.usecase.DiscoveryMoviesUseCase
import com.marturelo.themoviedbapp.presentation.dashboard.vo.PayloadVO
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.functions.Consumer
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class DashboardPresenterTest {
    @MockK(relaxed = true)
    lateinit var navigator: DashboardContract.Navigator

    @MockK(relaxed = true)
    lateinit var view: DashboardContract.View

    @MockK(relaxed = true)
    lateinit var discoveryMoviesUseCase: DiscoveryMoviesUseCase

    lateinit var presenter: DashboardPresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        presenter = spyk(
            DashboardPresenter(discoveryMoviesUseCase)
        )
        presenter.navigator = navigator
        presenter.attachView(view)
        presenter.navigator = navigator
    }

    @Test
    fun given_presenter_whenInit_then_verifyCallbacks() {
        presenter.init()
        verify { presenter.notifyDataChange() }
        Assert.assertEquals(Constants.DISCOVERY.POPULAR, presenter.payload?.discovery)
        verify { presenter.populate() }
    }

    @Test
    fun given_presenterInit_whenPopulate_then_verifyCallbacks() {
        presenter.init()
        verify { presenter.notifyDataChange() }

        val paramsArgument = slot<DiscoveryMoviesUseCase.Params>()
        val resultArgument = slot<Consumer<List<MovieEntity>>>()
        val errorArgument = slot<Consumer<Throwable>>()
        verify {
            discoveryMoviesUseCase.execute(
                capture(paramsArgument),
                capture(resultArgument),
                capture(errorArgument)
            )
        }
        Assert.assertEquals(presenter.payload?.discovery, paramsArgument.captured.discovery)
    }

    @Test
    fun given_presenter_whenRestoreFromPayload_then_verifyCallbacks() {
        presenter.restoreFromPayload(PayloadVO(discovery = Constants.DISCOVERY.TOP_RARED))
        verify { presenter.notifyDataChange() }
        verify(exactly = 0) { presenter.populate() }
    }

    @Test
    fun given_presenter_whenOnClosePressed_then_verifyCallbacks() {
        presenter.detachView()
        verify { discoveryMoviesUseCase.stop() }
    }
}