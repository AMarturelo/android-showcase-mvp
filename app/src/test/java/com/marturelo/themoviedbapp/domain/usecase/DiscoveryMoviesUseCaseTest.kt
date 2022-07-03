package com.marturelo.themoviedbapp.domain.usecase

import com.marturelo.themoviedbapp.commons.utils.Constants
import com.marturelo.themoviedbapp.commons.utils.Constants.DataSource.REMOTE
import com.marturelo.themoviedbapp.domain.entity.MovieEntity
import com.marturelo.themoviedbapp.domain.entity.SourceResultEntity
import com.marturelo.themoviedbapp.domain.repository.TMDBRepository
import com.marturelo.themoviedbapp.presentation.commons.utils.BaseUseCaseTest
import com.marturelo.themoviedbapp.presentation.commons.utils.FakeValuesEntity
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class DiscoveryMoviesUseCaseTest : BaseUseCaseTest() {
    @MockK(relaxed = true)
    private lateinit var repository: TMDBRepository

    private lateinit var useCase: DiscoveryMoviesUseCase

    @Before
    override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        useCase = DiscoveryMoviesUseCase(threadExecutor, postExecutionThread, repository)
    }

    @Test
    fun given_validData_whenInvokingUseCase_verifySuccess() {
        val result = SourceResultEntity(
            result = FakeValuesEntity.movies(),
            dataSource = REMOTE,
            networkStatus = Constants.NetworkStatus.NETWORK_STATUS_SUCCESS
        )
        every {
            repository.discovery(any())
        } returns Observable.just(result)
        val testObserver =
            useCase.buildObservable(
                DiscoveryMoviesUseCase.Params(
                    "dummy",
                )
            )
                .test()
        testObserver.assertComplete()
            .assertNoErrors()
            .assertValue(result)

        verify {
            repository.discovery(any())
        }
    }

    @Test
    fun given_invalidData_whenInvokingUseCase_verifyError() {
        val singleError = Observable.error<SourceResultEntity<List<MovieEntity>>>(Exception("Fake"))
        every {
            repository.discovery(any())
        } returns singleError
        val testObserver = useCase.buildObservable(
            DiscoveryMoviesUseCase.Params(
                "dummy",
            )
        )
            .test()
        testObserver.assertSubscribed()
            .assertError(Exception::class.java)
            .assertNotComplete()

        verify {
            repository.discovery(any())
        }
    }
}