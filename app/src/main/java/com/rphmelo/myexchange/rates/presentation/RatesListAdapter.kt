package com.rphmelo.myexchange.rates.presentation

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rphmelo.myexchange.R
import com.rphmelo.myexchange.rates.domain.model.Rate

class RatesListAdapter (
    private val context: Context,
    private var ratesList: List<Rate>?
): RecyclerView.Adapter<RatesListAdapter.RatesListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_list_rates, parent, false)
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
        ratesList?.get(position)?.let { holder.bindView(it) }
    }

    fun updateRatesList(ratesList: List<Rate>?){
        this.ratesList = ratesList
    }

    inner class RatesListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val txtDescription: TextView = itemView.findViewById(R.id.textview_view_item_list_rates_description)
        private val txtCode: TextView = itemView.findViewById(R.id.textview_view_item_list_rates_code)
        private var inputLayoutItemListRates: TextInputLayout = itemView.findViewById(R.id.input_layout_item_list_rates)
        private var inputEditTextItemListRates: TextInputEditText = itemView.findViewById(R.id.input_edit_text_item_list_rates)

        fun bindView(rate: Rate) = with(itemView){
            txtCode.text = rate.code
            inputEditTextItemListRates.setText(rate.value.toString())
        }

    }
}