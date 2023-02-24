package com.mruttyuunjay.stoke.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import com.mruttyuunjay.stoke.R
import com.mruttyuunjay.stoke.databinding.LayoutQtyDialogBinding
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
        txt:String,
        onUpdateClick: (String,Dialog) -> Unit,
        onDismiss: () -> Unit
    ) {

        val dialog = Dialog(context)

        /*              For full screen dialog
        com.google.android.material.R.style.Theme_Material3_Light_BottomSheetDialog
        */

        val vb = LayoutUpdateBinding.inflate(LayoutInflater.from(context))

        vb.titleValue.setText(txt)
        when(from){
            updateFrom.Product -> {
                "Update Product".apply {
                    vb.toolbarHeading.text = this
                    vb.updateBtn.text = this
                }
            }
            updateFrom.Category -> {
                 "Update Category".apply {
                     vb.toolbarHeading.text = this
                     vb.updateBtn.text = this
                 }
            }
            updateFrom.Batch -> {
                 "Update Batch".apply {
                     vb.toolbarHeading.text = this
                     vb.updateBtn.text = this
                 }
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


    enum class Qty{
        Inc,
        Dec,
    }
    fun qtyDialog(
        context:Context,
        qty:Qty,
        txt:String,
        onUpdateClick: (qty:Int) -> Unit,
        onDismiss: () -> Unit
    ) {

        val dialog = Dialog(context)

        val vb = LayoutQtyDialogBinding.inflate(LayoutInflater.from(context))
        vb.defaultQtyValue.setText(txt)
        vb.defaultQtyValue.keyListener = null
//        vb.defaultQtyValue.keyListener = null

        when(qty){
            Qty.Inc-> {
                "Add Quantity".apply {
                    vb.toolbarHeading.text = this
                    vb.updateBtn.text = this
                    vb.logo.setImageResource(R.drawable.baseline_add_24)
                }
            }
            Qty.Dec -> {
                "Minus Quantity".apply {
                    vb.toolbarHeading.text = this
                    vb.updateBtn.text = this
                    vb.logo.setImageResource(R.drawable.baseline_remove_24)
                }
            }
        }

        dialog.setContentView(vb.root)

        vb.back.setOnClickListener {
            onDismiss.invoke()
            dialog.dismiss()
        }
        vb.updateBtn.setOnClickListener {
            onUpdateClick.invoke(vb.qtyValue.text.toString().toInt())
            dialog.dismiss()
        }

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