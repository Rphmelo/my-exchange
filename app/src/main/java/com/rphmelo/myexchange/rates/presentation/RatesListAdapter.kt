package com.rphmelo.myexchange.rates.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rphmelo.myexchange.R
import com.rphmelo.myexchange.extension.calculateRatio
import com.rphmelo.myexchange.extension.convertCurrency
import com.rphmelo.myexchange.extension.getResourceByName
import com.rphmelo.myexchange.extension.round
import com.rphmelo.myexchange.rates.domain.model.Rate

class RatesListAdapter (
    private val activity: RatesActivity,
    private var ratesList: List<Rate>?
): RecyclerView.Adapter<RatesListAdapter.RatesListViewHolder>() {

    private var ratesListValue = MutableLiveData<List<Double>>()
    private var ratesListRatios = MutableLiveData<List<Double>>()
    private var currentCode: String? = null
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
        private val txtDescription: TextView = itemView.findViewById(R.id.textview_view_item_list_rates_description)
        private var inputEditTextItemListRates: TextInputEditText = itemView.findViewById(R.id.input_edit_text_item_list_rates)
        private var imageViewItemListRatesIcon: ImageView = itemView.findViewById(R.id.image_view_item_list_rates_icon)

        fun bindView(rate: Rate?, position: Int) = with(itemView){
            txtCode.text = rate?.code

            val drawableFlagId: Int? = getResourceByName(activity, "ic_${rate?.code?.toLowerCase()}", "drawable")
            val stringFlagId: Int? = getResourceByName(activity, "flag_name_${rate?.code?.toLowerCase()}", "string")

            drawableFlagId?.let {
                imageViewItemListRatesIcon.setImageResource(drawableFlagId)
            }

            stringFlagId?.let {
                txtDescription.text = activity.resources.getString(stringFlagId)
            }

            var hasTextWatcher = false
            var shouldChangeRate = true

            inputEditTextItemListRates.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                currentCode = rate?.code
                ratesListRatios.value = ratesListValue.value?.map {
                    var newValue = 0.0
                    inputEditTextItemListRates.text?.apply {
                        if(isNotEmpty()){
                            newValue = calculateRatio(it, inputEditTextItemListRates.text.toString().toDouble())
                        }
                    }

                    newValue
                }

                if(!hasTextWatcher){
                    inputEditTextItemListRates.addTextChangedListener(object: TextWatcher {
                        override fun afterTextChanged(s: Editable?) {

                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            s?.let {
                                if(it.isNotEmpty()){
                                    if(currentCode == rate?.code && isUpdatingValue){
                                        ratesListValue.value = ratesListValue.value?.mapIndexed { index, value ->
                                            value.convertCurrency(ratesListRatios.value!![index], it.toString().toDouble().round(2))
                                        }
                                    }
                                }
                            }

                        }
                    })
                    hasTextWatcher = true
                }
            }

            ratesListValue.observe(activity, Observer {
                val newRateText: String? = it?.get(position).toString()

                if(isUpdatingValue ){
                    isUpdatingValue = false
                    inputEditTextItemListRates.setText(newRateText)
                    isUpdatingValue = true

                    newRateText?.apply { inputEditTextItemListRates.setSelection(length) }
                }
            })
        }
    }
}