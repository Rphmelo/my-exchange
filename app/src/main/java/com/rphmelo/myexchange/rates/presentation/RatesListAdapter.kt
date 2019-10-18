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

class RatesListAdapter (
    private val context: Context,
    private var ratesList: HashMap<String, Double>?
): RecyclerView.Adapter<RatesListAdapter.RatesListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_list_rates, parent, false)
        return RatesListViewHolder(view)
    }

    override fun getItemCount(): Int {
        var itemCount = 0

        ratesList?.values?.toList()?.let {
            itemCount = it.size
        }

        return itemCount
    }

    override fun onBindViewHolder(holder: RatesListViewHolder, position: Int) {
        ratesList?.toList()?.get(position)?.let { holder.bindView(it) }
    }

    fun updateRatesList(ratesList: HashMap<String, Double>?){
        this.ratesList = ratesList
    }

    inner class RatesListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val txtDescription: TextView = itemView.findViewById(R.id.textview_view_item_list_rates_description)
        private val txtCode: TextView = itemView.findViewById(R.id.textview_view_item_list_rates_code)
        private var inputLayoutItemListRates: TextInputLayout = itemView.findViewById(R.id.input_layout_item_list_rates)
        private var inputEditTextItemListRates: TextInputEditText = itemView.findViewById(R.id.input_edit_text_item_list_rates)

        fun bindView(rate: Pair<String, Double>) = with(itemView){
            txtCode.text = rate.first
            inputEditTextItemListRates.setText(rate.second.toString())
        }

    }
}