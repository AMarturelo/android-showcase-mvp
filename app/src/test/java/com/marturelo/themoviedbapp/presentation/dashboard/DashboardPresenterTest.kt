package com.marturelo.themoviedbapp.presentation.dashboard

import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import com.marturelo.themoviedbapp.domain.usecase.DiscoveryMoviesUseCase
import com.marturelo.themoviedbapp.presentation.dashboard.commons.utils.FakeValuesEntity
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import com.marturelo.themoviedbapp.presentation.dashboard.vo.PayloadVO
import com.marturelo.themoviedbapp.presentation.dashboard.vo.toVO
import com.nhaarman.mockitokotlin2.capture
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.functions.Consumer
import java.util.*
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
    fun given_presenter_whenOnResult_then_verifyCallbacks() {
        presenter.restoreFromPayload(PayloadVO(discovery = Constants.DISCOVERY.TOP_RARED))

        val inputMovies = FakeValuesEntity.movies()
        presenter.onResult(
            inputMovies
        )
        verify { presenter.notifyDataChange() }
        Assert.assertEquals(DashboardState.CONTENT, presenter.payload?.contentState)
        Assert.assertEquals(inputMovies.map { it.toVO() }, presenter.payload?.items)
    }

    @Test
    fun given_presenter_whenOnErrorEmptyItems_then_verifyCallbacks() {
        presenter.restoreFromPayload(PayloadVO(discovery = Constants.DISCOVERY.TOP_RARED))

        presenter.onError(
            Exception()
        )
        verify { presenter.notifyDataChange() }
        Assert.assertEquals(DashboardState.ERROR, presenter.payload?.contentState)
    }

    @Test
    fun given_presenter_whenOnErrorWithItems_then_verifyCallbacks() {
        val inputMovies = FakeValuesEntity.movies()
        val inputError = Exception("Fake")

        presenter.init()
        presenter.onResult(inputMovies)

        presenter.onError(
            inputError
        )

        val errorArgument = slot<Throwable>()

        verify { view.showError(capture(errorArgument)) }
        verify { presenter.notifyDataChange() }
        Assert.assertEquals(DashboardState.CONTENT, presenter.payload?.contentState)
        Assert.assertEquals(inputError, errorArgument.captured)
    }

    @Test
    fun given_error_whenNotifyDataChange_then_VerifyCallbacks() {
        every { presenter.payload } returns PayloadVO(
            contentState = DashboardState.ERROR,
            discovery = Constants.DISCOVERY.TOP_RARED,
        )

        presenter.notifyDataChange()
        verify { view.showErrorState() }
    }

    @Test
    fun given_loading_whenNotifyDataChange_then_VerifyCallbacks() {
        every { presenter.payload } returns PayloadVO(
            contentState = DashboardState.LOADING,
            discovery = Constants.DISCOVERY.TOP_RARED,
        )

        presenter.notifyDataChange()
        verify { view.showLoadingState() }
    }

    @Test
    fun given_content_whenNotifyDataChange_then_VerifyCallbacks() {
        every { presenter.payload } returns PayloadVO(
            contentState = DashboardState.CONTENT,
            discovery = Constants.DISCOVERY.TOP_RARED,
        )

        presenter.notifyDataChange()
        verify { view.showContentState() }
        verify { view.updateUI(any()) }
    }

    @Test
    fun given_presenter_whenOnItemClicked_then_VerifyCallbacks() {
        val input = FakeValuesEntity.movie().toVO()
        presenter.onItemClicked(input)

        val movieArgument = slot<MovieVO>()

        verify { navigator.navigateToDetails(capture(movieArgument)) }
        Assert.assertEquals(input, movieArgument.captured)
    }

    @Test
    fun given_presenter_whenOnClosePressed_then_verifyCallbacks() {
        presenter.detachView()
        verify { discoveryMoviesUseCase.stop() }
    }
}