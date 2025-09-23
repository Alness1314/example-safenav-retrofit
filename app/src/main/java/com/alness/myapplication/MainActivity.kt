package com.alness.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alness.myapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var photoUri: Uri

    // Launcher para pedir permiso de cámara
    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // permiso concedido -> abrir cámara
            launchCameraAndSavePhoto()
        } else {
            // permiso denegado
            if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                // El usuario marcó "No preguntar" -> abrir ajustes
                showPermissionDeniedPermanentlyDialog()
            } else {
                // Denegado sin "no preguntar"
                Snackbar.make(binding.root, "Permiso de cámara denegado", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    // Launcher para tomar la foto y guardarla en el Uri que le pasemos
    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            // La foto ya está en photoUri
            binding.ivPhoto.setImageURI(photoUri)
            Snackbar.make(binding.root, "Foto guardada", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(binding.root, "No se tomó la foto", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btn1.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
        binding.btnCamera.setOnClickListener {
            checkCameraPermissionAndOpen()
        }
    }

    private fun checkCameraPermissionAndOpen() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                // Ya tiene permiso
                launchCameraAndSavePhoto()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // Mostrar justificación (rationale) y pedir permiso
                AlertDialog.Builder(this)
                    .setTitle("Permiso de cámara requerido")
                    .setMessage("La app necesita acceso a la cámara para tomar fotos.")
                    .setPositiveButton("Permitir") { _, _ ->
                        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
            else -> {
                // Pedir permiso por primera vez
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun launchCameraAndSavePhoto() {
        // Crear archivo en el directorio privado de la app
        val photoFile = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "photo_${System.currentTimeMillis()}.jpg"
        ).apply { createNewFile() }

        photoUri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            photoFile
        )

        // Lanzar la cámara
        takePictureLauncher.launch(photoUri)
    }

    private fun showPermissionDeniedPermanentlyDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permiso denegado permanentemente")
            .setMessage("Debes habilitar el permiso de cámara desde los ajustes de la app.")
            .setPositiveButton("Abrir ajustes") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:$packageName")
                }
                startActivity(intent)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


}