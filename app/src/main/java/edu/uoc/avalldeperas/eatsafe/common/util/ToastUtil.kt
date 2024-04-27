package edu.uoc.avalldeperas.eatsafe.common.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

object ToastUtil {
    fun showToast(text: String, context: Context, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, text, length).show()
    }

    fun showToast(@StringRes resource: Int, context: Context, length: Int = Toast.LENGTH_SHORT) {
        val text = context.getString(resource)
        showToast(text, context)
    }
}