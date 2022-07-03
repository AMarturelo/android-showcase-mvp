package com.marturelo.themoviedbapp.presentation.details

import com.marturelo.themoviedbapp.presentation.commons.utils.FakeValuesEntity
import com.marturelo.themoviedbapp.presentation.dashboard.vo.toVO
import com.marturelo.themoviedbapp.presentation.details.vo.PayloadVO
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class DetailsPresenterTest {
    @MockK(relaxed = true)
    lateinit var navigator: DetailsContract.Navigator

    @MockK(relaxed = true)
    lateinit var view: DetailsContract.View

    lateinit var presenter: DetailsPresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        presenter = spyk(
            DetailsPresenter()
        )
        presenter.navigator = navigator
        presenter.attachView(view)
        presenter.navigator = navigator
    }

    @Test
    fun given_presenter_whenInitWithPayload_then_VerifyCallbacks() {
        presenter.initWithPayload(PayloadVO(FakeValuesEntity.movie().toVO()))
        verify { presenter.notifyDataChange() }
    }

    @Test
    fun given_presenter_whenRestoreFromPayload_then_VerifyCallbacks() {
        presenter.restoreFromPayload(PayloadVO(FakeValuesEntity.movie().toVO()))
        verify { presenter.notifyDataChange() }
    }

    @Test
    fun given_presenter_whenRestore_then_VerifyCallbacks() {
        every { presenter.payload } returns PayloadVO(
            movie = FakeValuesEntity.movie().toVO()
        )

        presenter.restore()
        verify { presenter.notifyDataChange() }
    }

    @Test
    fun given_presenter_whenOnBackClicked_then_VerifyCallbacks() {
        presenter.onBackClicked()
        verify { navigator.navigateToBack() }
    }

    @Test
    fun given_presenter_whenNotifyDataChange_then_VerifyCallbacks() {
        every { presenter.payload } returns PayloadVO(
            movie = FakeValuesEntity.movie().toVO()
        )
        presenter.notifyDataChange()
        verify { view.updateUI(any()) }
    }
}