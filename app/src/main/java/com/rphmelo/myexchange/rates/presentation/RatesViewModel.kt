package com.rphmelo.myexchange.rates.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rphmelo.myexchange.rates.domain.model.RatesResponse
import com.rphmelo.myexchange.rates.data.repository.RatesRepository
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import javax.inject.Inject

class RatesViewModel @Inject constructor(private var ratesRepository: RatesRepository, private val executor: Executor) : ViewModel() {

    var ratesList = MutableLiveData<HashMap<String, Double>>()
    var disposable: Disposable? = null

    fun getLatest(base: String) {
        executor.execute {
            ratesRepository.getLatest(base)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<RatesResponse> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onNext(ratesResponse: RatesResponse) {
                        ratesList.value = ratesResponse.rates
                    }

                    override fun onError(e: Throwable) {
                        disposable = null
                    }
                })
        }
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

    private fun dispose(){
        disposable?.dispose()
    }
}