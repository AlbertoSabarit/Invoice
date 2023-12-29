package com.murray.invoice.base

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.murray.invoice.R

class  FragmentProgressDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Utilizar la instancia de LayoutInfater para inflar el diseño XML que contiene un
        // componente ProgressBar
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.fragment_dialog_progress, null)

        // Crea un cuadro de diálogo con el diseño inflado
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        builder.setCancelable(false)
        builder.setTitle("Esperando...")
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        // Devuelve el cuadro de diálogo creado
        return dialog
    }
}
