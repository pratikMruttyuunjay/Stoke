package com.mruttyuunjay.stoke.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import com.mruttyuunjay.stoke.databinding.LayoutUpdateBinding

object UiHelper {

    enum class updateFrom{
        Product,
        Category,
        Batch
    }

    fun updateDialog(
        context:Context,
        from:updateFrom,
        onUpdateClick: (String,Dialog) -> Unit,
        onDismiss: () -> Unit
    ) {

        val dialog = Dialog(context)

        /*              For full screen dialog
        com.google.android.material.R.style.Theme_Material3_Light_BottomSheetDialog
        */

        val vb = LayoutUpdateBinding.inflate(LayoutInflater.from(context))

        when(from){
            updateFrom.Product -> {
                vb.toolbarHeading.text = "Update Product"
                vb.updateBtn.text = "Update Product"
            }
            updateFrom.Category -> {
                vb.toolbarHeading.text = "Update Category"
                vb.updateBtn.text = "Update Category"
            }
            updateFrom.Batch -> {
                vb.toolbarHeading.text = "Update Batch"
                vb.updateBtn.text = "Update Batch"
            }
        }
        vb.back.setOnClickListener {
            onDismiss.invoke()
            dialog.dismiss()
        }
        vb.updateBtn.setOnClickListener {
            onUpdateClick.invoke(vb.titleValue.text.toString(),dialog)
        }

        dialog.setContentView(vb.root)

        // Set the dialog window attributes
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams

        dialog.setCancelable(false)
        dialog.show()
    }

}