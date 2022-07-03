package com.marturelo.themoviedbapp.presentation.commons.utils

import com.marturelo.themoviedbapp.domain.core.executor.PostExecutionThread
import com.marturelo.themoviedbapp.domain.core.executor.ThreadExecutor
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before

abstract class BaseUseCaseTest {
    @MockK(relaxed = true)
    protected lateinit var threadExecutor: ThreadExecutor

    @MockK(relaxed = true)
    protected lateinit var postExecutionThread: PostExecutionThread

    protected val testScheduler = TestScheduler()

    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
        every { threadExecutor.scheduler } returns testScheduler
        every { postExecutionThread.scheduler } returns testScheduler
    }

    @After
    open fun tearDown() {
        testScheduler.shutdown()
    }
}