package com.rphmelo.myexchange.rates.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rphmelo.myexchange.R
import com.rphmelo.myexchange.rates.domain.model.Rate

class RatesListAdapter (
    private val activity: RatesActivity,
    private var ratesList: List<Rate>?
): RecyclerView.Adapter<RatesListAdapter.RatesListViewHolder>() {

    private var ratesListValue = MutableLiveData<List<Double>>()
    private var currentCode: String? = null
    private var currentInputText: String? = null
    private var hasRatesListValue = false
    private var isUpdatingValue = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesListViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.view_item_list_rates, parent, false)
        return RatesListViewHolder(view)
    }

    override fun getItemCount(): Int {
        var itemCount = 0

        ratesList?.let {
            itemCount = it.size
        }

        return itemCount
    }

    override fun onBindViewHolder(holder: RatesListViewHolder, position: Int) {
        var currentRate: Rate?
        ratesList?.apply {
            currentRate = get(position)
            if(!hasRatesListValue){
                currentCode = currentRate?.code
                ratesListValue.value = this.map { it.value }
                hasRatesListValue = true
                isUpdatingValue = true
            }

            holder.bindView(currentRate, position)
        }
    }

    fun updateRatesList(ratesList: List<Rate>?){
        this.ratesList = ratesList
    }

    inner class RatesListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val txtCode: TextView = itemView.findViewById(R.id.textview_view_item_list_rates_code)
        private var inputEditTextItemListRates: TextInputEditText = itemView.findViewById(R.id.input_edit_text_item_list_rates)
        private var inputLayoutItemListRates: TextInputLayout = itemView.findViewById(R.id.input_layout_item_list_rates)

        fun bindView(rate: Rate?, position: Int) = with(itemView){
            txtCode.text = rate?.code

            var hasTextWatcher = false

            inputEditTextItemListRates.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                currentCode = rate?.code
                currentInputText = rate?.value.toString()

                if(!hasTextWatcher){
                    inputEditTextItemListRates.addTextChangedListener(object: TextWatcher {
                        override fun afterTextChanged(s: Editable?) {

                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            if(currentCode == rate?.code && isUpdatingValue){
                                ratesListValue.value = ratesListValue.value?.map { it+10 }
                            }
                            currentInputText = s.toString()
                        }
                    })
                    hasTextWatcher = true
                }
            }

            ratesListValue.observe(activity, Observer {
                if(isUpdatingValue){
                    isUpdatingValue = false
                    inputEditTextItemListRates.setText(it?.get(position).toString())
                    isUpdatingValue = true
                }
            })
        }
    }
}