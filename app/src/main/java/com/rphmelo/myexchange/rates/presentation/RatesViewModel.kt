package com.rphmelo.myexchange.rates.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rphmelo.myexchange.rates.domain.model.RatesResponse
import com.rphmelo.myexchange.rates.data.repository.RatesRepository
import com.rphmelo.myexchange.rates.domain.model.Rate
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import javax.inject.Inject

class RatesViewModel @Inject constructor(private var ratesRepository: RatesRepository, private val executor: Executor) : ViewModel() {

    var ratesList = MutableLiveData<List<Rate>>()
    var isLoading = MutableLiveData<Boolean>()
    var disposable: Disposable? = null

    fun getLatest(base: String) {
        setLoading(true)

        executor.execute {

            ratesRepository.getLatest(base)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<RatesResponse> {
                    override fun onComplete() {
                        setLoading(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onNext(ratesResponse: RatesResponse) {
                        ratesList.value = ratesResponse.getRatesList()
                    }

                    override fun onError(e: Throwable) {
                        disposable = null
                    }
                })
        }
    }

    private fun setLoading(value: Boolean){
        isLoading.value = value
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

    private fun dispose(){
        disposable?.dispose()
    }
}