import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ImageFileManager(private val context: Context) {

    private var imageUri: Uri? = null

    // Cette méthode permet de récupérer le path de l'image sélectionnée
    fun getCurrentImagePath(): String? {
        return imageUri?.let { uri ->
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            val imagePath = columnIndex?.let { cursor?.getString(it) }
            cursor?.close()
            imagePath
        }
    }

    // Cette méthode permet de sélectionner une image depuis la galerie
    fun pickImage(fragment: Fragment, onImagePicked: (Uri?) -> Unit) {
        val pickImage = fragment.registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            onImagePicked(uri)
        }
        pickImage.launch("image/*")
    }

    // Cette méthode permet de sauvegarder l'image sélectionnée dans le dossier privé de l'application
    fun saveImageToInternalStorage(uri: Uri): String {
        val imageName = "category_${UUID.randomUUID()}.jpg"
        val imageFile = File(context.filesDir, imageName)

        val outputStream = FileOutputStream(imageFile)
        context.contentResolver.openInputStream(uri)?.copyTo(outputStream)
        Log.d("ImageFileManager", "Saved image to $imageFile.absolutePath")
        return imageFile.absolutePath
    }

    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 1
    }
}