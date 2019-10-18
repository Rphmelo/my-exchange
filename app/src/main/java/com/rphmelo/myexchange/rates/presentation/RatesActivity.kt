package com.rphmelo.myexchange.rates.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.rphmelo.myexchange.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class RatesActivity : AppCompatActivity() {

    private lateinit var ratesViewModel: RatesViewModel
    private lateinit var rvActivityRatesListRates: RecyclerView
    private lateinit var ratesListAdapter: RatesListAdapter
    private val defaultBase = "EUR"

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)

        configureDagger()
        configureViewModel()

        observeGetLatest()
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        ratesListAdapter = RatesListAdapter(baseContext, HashMap())
        rvActivityRatesListRates = findViewById(R.id.recycler_activity_rates_list_rates)
        rvActivityRatesListRates.adapter = ratesListAdapter
        ratesViewModel.getLatest(defaultBase)
    }

    private fun observeGetLatest() {
        ratesViewModel.ratesList.observe(this, Observer {
            it?.apply {
                ratesListAdapter.updateRatesList(this)
                rvActivityRatesListRates.adapter?.notifyDataSetChanged()
            }
        })
    }

    private fun configureDagger() {
        AndroidInjection.inject(this)
    }

    private fun configureViewModel() {
        ratesViewModel = ViewModelProviders.of(this, viewModelFactory).get(RatesViewModel::class.java)
    }
}
