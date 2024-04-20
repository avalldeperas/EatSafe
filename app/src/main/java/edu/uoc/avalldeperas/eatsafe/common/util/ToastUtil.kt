package edu.uoc.avalldeperas.eatsafe.common.util

import android.content.Context
import android.widget.Toast

object ToastUtil {
    fun showToast(text: String, context: Context, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, text, length).show()
    }
}