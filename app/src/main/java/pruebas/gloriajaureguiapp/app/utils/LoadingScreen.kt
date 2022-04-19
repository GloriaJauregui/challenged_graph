package pruebas.gloriajaureguiapp.app.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import pruebas.gloriajaureguiapp.R

object LoadingScreen {
    var dialog: Dialog? = null

    fun displayLoadingWithText(
        context: Context?,
        cancelable: Boolean
    ) {
        dialog = context?.let { Dialog(it) }
        dialog?.let { it ->
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setContentView(R.layout.loading_screen)
            it.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setCancelable(cancelable)
            try {
                it.show()
            } catch (e: Exception) {
            }
        }
    }

    fun hideLoading() {
        try {
            if (dialog != null) {
                dialog?.dismiss()
            }
        } catch (e: Exception) {
        }
    }
}