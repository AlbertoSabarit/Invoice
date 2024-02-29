package com.murray.customer.ui.list

import android.app.Dialog
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.text.style.LineHeightSpan
import android.text.style.RelativeSizeSpan
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.murray.customer.R

class BaseFragmentDialog() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_Rounded)
        builder.setTitle("Error")

        val message = SpannableString("Este cliente no se puede eliminar ya que posee una o más facturas/tareas...")
        message.setSpan(RelativeSizeSpan(1.3f), 0, message.length, 0)
        builder.setMessage(message)


        builder.setPositiveButton(
            android.R.string.ok
        ) { _, _ ->
            findNavController().popBackStack()
        }
        return builder.create()
    }

    companion object {
        const val title = "title"
        const val message = "message"
        const val request = "request"
        const val result = "result"

    }
    class CenterSpan : AlignmentSpan, LineHeightSpan {
        override fun getAlignment(): Layout.Alignment {
            return Layout.Alignment.ALIGN_CENTER
        }

        override fun chooseHeight(
            text: CharSequence?,
            start: Int,
            end: Int,
            spanstartv: Int,
            v: Int,
            fm: Paint.FontMetricsInt?
        ) {
            if (fm != null) {
                fm.ascent = 0
                fm.descent = 0
                fm.top = -v / 2
                fm.bottom = v / 2
            }
        }
    }
}