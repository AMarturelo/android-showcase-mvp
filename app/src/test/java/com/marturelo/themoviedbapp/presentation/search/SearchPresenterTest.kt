package com.marturelo.themoviedbapp.presentation.search

import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import com.marturelo.themoviedbapp.domain.entity.SourceResultEntity
import com.marturelo.themoviedbapp.domain.usecase.SearchMoviesUseCase
import com.marturelo.themoviedbapp.presentation.commons.utils.FakeValuesEntity
import com.marturelo.themoviedbapp.presentation.dashboard.vo.MovieVO
import com.marturelo.themoviedbapp.presentation.dashboard.vo.toVO
import com.marturelo.themoviedbapp.presentation.search.vo.PayloadVO
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.functions.Consumer
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchPresenterTest {
    @MockK(relaxed = true)
    lateinit var navigator: SearchContract.Navigator

    @MockK(relaxed = true)
    lateinit var view: SearchContract.View

    @MockK(relaxed = true)
    lateinit var searchUseCase: SearchMoviesUseCase

    private lateinit var presenter: SearchPresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        presenter = spyk(
            SearchPresenter(searchUseCase)
        )
        presenter.navigator = navigator
        presenter.attachView(view)
        presenter.navigator = navigator
    }

    @Test
    fun given_presenter_whenInit_then_verifyCallbacks() {
        presenter.init()
        verify { presenter.notifyDataChange() }
        verify { presenter.populate() }
    }

    @Test
    fun given_presenterInit_whenPopulate_then_verifyCallbacks() {
        presenter.init()
        verify { presenter.notifyDataChange() }

        val paramsArgument = slot<SearchMoviesUseCase.Params>()
        val resultArgument = slot<Consumer<SourceResultEntity<List<MovieEntity>>>>()
        val errorArgument = slot<Consumer<Throwable>>()
        verify {
            searchUseCase.execute(
                capture(paramsArgument),
                capture(resultArgument),
                capture(errorArgument)
            )
        }
    }

    @Test
    fun given_presenterInit_whenPopulateWithItems_then_verifyCallbacks() {
        given_presenter_whenOnResultSuccess_then_verifyCallbacks()

        presenter.populate()
        Assert.assertEquals(presenter.payload?.contentState, SearchState.CONTENT)
    }

    @Test
    fun given_presenterInit_whenOnQueryChanged_then_verifyCallbacks() {
        presenter.restoreFromPayload(PayloadVO())
        presenter.onQueryChanged("query")
        Assert.assertEquals("query", presenter.payload?.query)
        verify { presenter.populate() }
    }

    @Test
    fun given_presenterInit_whenOnFocusLost_then_verifyCallbacks() {
        presenter.restoreFromPayload(PayloadVO())
        presenter.onFocusLost()
        verify { navigator.navigateToBack() }
    }

    @Test
    fun given_presenter_whenRestoreFromPayload_then_verifyCallbacks() {
        presenter.restoreFromPayload(PayloadVO())
        verify { presenter.notifyDataChange() }
        verify(exactly = 0) { presenter.populate() }
    }

    @Test
    fun given_presenter_whenOnResultSuccess_then_verifyCallbacks() {
        presenter.restoreFromPayload(PayloadVO())

        val inputMovies = SourceResultEntity(
            result = FakeValuesEntity.movies(),
            dataSource = Constants.DataSource.LOCAL,
            networkStatus = Constants.NetworkStatus.NETWORK_STATUS_SUCCESS
        )
        presenter.onResult(
            inputMovies
        )
        verify(exactly = 0) { view.showError(any()) }
        verify { presenter.notifyDataChange() }
        Assert.assertEquals(SearchState.CONTENT, presenter.payload?.contentState)
        Assert.assertEquals(inputMovies.result.map { it.toVO() }, presenter.payload?.items)
    }

    @Test
    fun given_presenter_whenOnResultLocalSuccessRemoteError_then_verifyCallbacks() {
        presenter.restoreFromPayload(PayloadVO())

        val inputMovies = SourceResultEntity(
            result = FakeValuesEntity.movies(),
            dataSource = Constants.DataSource.LOCAL,
            error = Exception("Fake"),
            networkStatus = Constants.NetworkStatus.NETWORK_STATUS_ERROR
        )
        presenter.onResult(
            inputMovies
        )
        verify { view.showError(any()) }
        verify { presenter.notifyDataChange() }
        Assert.assertEquals(SearchState.CONTENT, presenter.payload?.contentState)
        Assert.assertEquals(inputMovies.result.map { it.toVO() }, presenter.payload?.items)
    }

    @Test
    fun given_presenter_whenOnErrorEmptyItems_then_verifyCallbacks() {
        presenter.restoreFromPayload(PayloadVO())

        presenter.onError(
            Exception()
        )
        verify { presenter.notifyDataChange() }
        Assert.assertEquals(SearchState.ERROR, presenter.payload?.contentState)
    }

    @Test
    fun given_presenter_whenOnErrorWithItems_then_verifyCallbacks() {
        val inputMovies = SourceResultEntity(
            result = FakeValuesEntity.movies(),
            dataSource = Constants.DataSource.LOCAL,
            networkStatus = Constants.NetworkStatus.NETWORK_STATUS_SUCCESS
        )
        val inputError = Exception("Fake")

        presenter.init()
        presenter.onResult(inputMovies)

        presenter.onError(
            inputError
        )

        val errorArgument = slot<Throwable>()

        verify { view.showError(capture(errorArgument)) }
        verify { presenter.notifyDataChange() }
        Assert.assertEquals(SearchState.CONTENT, presenter.payload?.contentState)
        Assert.assertEquals(inputError, errorArgument.captured)
    }

    @Test
    fun given_error_whenNotifyDataChange_then_VerifyCallbacks() {
        every { presenter.payload } returns PayloadVO(
            contentState = SearchState.ERROR,
        )

        presenter.notifyDataChange()
        verify { view.showErrorState() }
    }

    @Test
    fun given_loading_whenNotifyDataChange_then_VerifyCallbacks() {
        every { presenter.payload } returns PayloadVO(
            contentState = SearchState.LOADING,
        )

        presenter.notifyDataChange()
        verify { view.showLoadingState() }
    }

    @Test
    fun given_content_whenNotifyDataChange_then_VerifyCallbacks() {
        every { presenter.payload } returns PayloadVO(
            contentState = SearchState.CONTENT,
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
        verify { searchUseCase.stop() }
    }
}