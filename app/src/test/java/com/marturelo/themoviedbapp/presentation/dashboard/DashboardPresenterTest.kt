package com.marturelo.themoviedbapp.presentation.dashboard

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test


class DashboardPresenterTest {
    @MockK(relaxed = true)
    lateinit var navigator: DashboardContract.Navigator

    @MockK(relaxed = true)
    lateinit var view: DashboardContract.View

    lateinit var presenter: DashboardPresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        presenter = spyk(
            DashboardPresenter(
            )
        )
        presenter.navigator = navigator
        presenter.bindView(view)
        presenter.navigator = navigator
    }

    @Test
    fun given_presenter_whenOnClosePressed_then_verifyCallbacks() {
        presenter.onClosePressed()
        verify { navigator.onClose() }
    }
}