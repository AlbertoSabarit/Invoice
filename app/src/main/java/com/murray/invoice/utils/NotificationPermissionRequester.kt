package com.hanmajid.android.tiramisu.notificationruntimepermission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NotificationPermissionRequester(private val activity:ComponentActivity) {

    private val requestPermissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (!isGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                if (Build.VERSION.SDK_INT >= 33) {
                    //Caso de uso o apartado 5a del diagrama Android Developer. Comprobar que se muestra la opción PermissionRationale
                    if (activity.shouldShowRequestPermissionRationale((Manifest.permission.POST_NOTIFICATIONS))) //5b explicamos al usuario
                        showNotificationPermissionRationale()
                    else
                        showSettingDialog() //Caso de uso o apartado 6 del diagrama Android Developer

                }

        } else {
            Toast.makeText(activity, "Permiso asignado", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Esta función se ejecuta cuando el usuario ha rechazado por primera vez asignar el permiso de
     * mostrar notificaciones al usuario
     */
    private fun showNotificationPermissionRationale() {
        MaterialAlertDialogBuilder(
            activity,
            //R.style.MaterialAlertDialog_Material3
        )
        .setTitle("Alert")
        .setMessage("Notification permission is required, to show notification")
        .setPositiveButton("Ok") { _, _ ->
            if (Build.VERSION.SDK_INT >= 33) {
                requestPermissionLauncher?.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        //Si cancela en nuestro código se mostrará siempre showSettingDialog
        .setNegativeButton("Cancel", null)
        .show()
    }

    /**
     * Esta función muestra un cuadro de dialogo que permite al usuario aceptar o bien cancelar la opción de dar permisos.
     * En el caso que el usuario acepte se abre la ventana de configuración de la aplicación
     * donde el usuario debe seleccionar manualmente que permite las notificaciones a la app
     */
    private  fun showSettingDialog() {
        MaterialAlertDialogBuilder(
            activity,
            //R.style.MaterialAlertDialog_Material3
        )
        .setTitle("Notification Permission")
        .setMessage("Notification permission is required, Please allow notification permission from setting")
        .setPositiveButton("Ok") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:${activity.packageName}")
            activity.startActivity(intent)
        }
        .setNegativeButton("Cancel", null)
        .show()
    }

    /**
     * Función que solicita el permiso para crear notificaciones
     */
    fun tryRequest() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        return requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}