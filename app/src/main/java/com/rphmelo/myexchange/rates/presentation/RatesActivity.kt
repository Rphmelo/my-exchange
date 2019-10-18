package com.rphmelo.myexchange.rates.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import com.rphmelo.myexchange.R
import com.rphmelo.myexchange.extension.gone
import com.rphmelo.myexchange.extension.visible
import dagger.android.AndroidInjection
import javax.inject.Inject

class RatesActivity : AppCompatActivity(), RatesView {

    private lateinit var ratesViewModel: RatesViewModel
    private lateinit var rvActivityRatesListRates: RecyclerView
    private lateinit var ratesListAdapter: RatesListAdapter
    private lateinit var ratesProgressBar: ProgressBar
    private val defaultBase = "EUR"

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)

        initComponents()

        configureDagger()
        configureViewModel()

        observeLoading()
        observeGetLatest()
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        ratesListAdapter = RatesListAdapter(baseContext, arrayListOf())
        rvActivityRatesListRates.hasFixedSize()
        rvActivityRatesListRates.adapter = ratesListAdapter

        ratesViewModel.getLatest(defaultBase)
    }

    private fun initComponents(){
        ratesProgressBar = findViewById(R.id.progress_bar_rates_list)
        rvActivityRatesListRates = findViewById(R.id.recycler_activity_rates_list_rates)
    }

    private fun observeGetLatest() {
        ratesViewModel.ratesList.observe(this, Observer {
            it?.apply {
                ratesListAdapter.updateRatesList(this)
                rvActivityRatesListRates.adapter?.notifyDataSetChanged()
            }
        })
    }

    private fun observeLoading() {
        ratesViewModel.isLoading.observe(this, Observer {
            it?.apply {
                if(this){
                    ratesProgressBar.visible()
                } else {
                    ratesProgressBar.gone()
                }
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
