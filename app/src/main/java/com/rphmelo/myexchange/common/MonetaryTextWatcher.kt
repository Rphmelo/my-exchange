package com.rphmelo.myexchange.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat

class MonetaryTextWatcher(private val editText: EditText) : TextWatcher {
    private var current = ""

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.toString() != current) {
            editText.removeTextChangedListener(this)

            val cleanString = s.toString().replace("[,.]".toRegex(), "")

            val parsed = cleanString.toDouble()
            val formatted = NumberFormat.getCurrencyInstance().format(parsed / 100)

            current = formatted.replace("[$]".toRegex(), "")
            editText.setText(current)
            editText.setSelection(current.length)

            editText.addTextChangedListener(this)
        }
    }

}