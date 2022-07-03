package com.marturelo.themoviedbapp.domain.core

import com.marturelo.themoviedbapp.domain.core.executor.PostExecutionThread
import com.marturelo.themoviedbapp.domain.core.executor.ThreadExecutor
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import timber.log.Timber

abstract class ObservableUseCase<T, Params>(
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread,
) : UseCase<DisposableObserver<T>, Params?> {

    private val disposable: CompositeDisposable = CompositeDisposable()

    abstract fun buildObservable(params: Params?): Observable<T>

    override fun execute(callback: DisposableObserver<T>, params: Params?) {
        val observable: Observable<T> = initObservable(params)
        addDisposable(observable.subscribeWith(callback))
    }

    fun execute(observer: DisposableObserver<T>) {
        execute(observer, null)
    }

    fun execute(params: Params? = null, onNext: Consumer<T>) {
        addDisposable(initObservable(params).subscribe(onNext) { throwable ->
            Timber.e(throwable)
            Timber.d(onNext.toString())
        })
    }

    fun execute(
        params: Params? = null,
        onNext: Consumer<T>,
        onError: Consumer<in Throwable>
    ) {
        addDisposable(initObservable(params).subscribe(onNext, onError))
    }

    fun execute(onNext: Consumer<T>) {
        addDisposable(initObservable().subscribe(onNext))
    }

    fun execute(onNext: Consumer<T>, onError: Consumer<in Throwable>) {
        addDisposable(initObservable().subscribe(onNext, onError))
    }

    private fun initObservable(): Observable<T> {
        return initObservable(null)
    }

    private fun initObservable(params: Params?): Observable<T> {
        return buildObservable(params)
            .subscribeOn(threadExecutor.scheduler)
            .observeOn(postExecutionThread.scheduler)
    }

    private fun addDisposable(observer: Disposable) {
        disposable.add(observer)
    }

    private fun dispose() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    override fun stop() {
        dispose()
    }

}