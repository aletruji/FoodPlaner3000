package me.trujillo.foodplaner3000

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun DishImagePicker(
    imagePath: String?,
    onImageSelected: (String) -> Unit
) {
    val context = LocalContext.current

    // GALLERY PICKER
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            val file = File(context.filesDir, "dish_${System.currentTimeMillis()}.jpg")
            file.outputStream().use { out -> bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out) }
            onImageSelected(file.absolutePath)
        }
    }

    // CAMERA PICKER
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoUri != null) {
            onImageSelected(photoUri!!.path!!)
        }
    }

    fun launchCamera() {
        val file = File(context.filesDir, "dish_${System.currentTimeMillis()}.jpg")
        photoUri = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
        cameraLauncher.launch(photoUri)
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        if (imagePath != null && File(imagePath).exists()) {
            Image(
                bitmap = BitmapFactory.decodeFile(imagePath).asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("Kein Bild")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            androidx.compose.material3.Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Galerie")
            }
            androidx.compose.material3.Button(onClick = { launchCamera() }) {
                Text("Kamera")
            }
        }
    }
}
