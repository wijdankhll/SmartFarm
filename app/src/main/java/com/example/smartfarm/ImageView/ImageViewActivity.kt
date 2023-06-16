package com.example.smartfarm.ImageView

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smartfarm.Detail.DetailActivity

import com.example.smartfarm.R
import com.example.smartfarm.databinding.ActivityImageViewBinding
import com.example.smartfarm.ml.ModelLatency
import com.example.smartfarm.ml.ModelSize
import com.example.smartfarm.navigasi.NavigasiActivity
import com.example.smartfarm.navigasi.ui.Camera.CameraActivity
import com.example.smartfarm.navigasi.ui.Camera.rotateFile
import com.example.smartfarm.navigasi.ui.Camera.uriToFile
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.TransformToGrayscaleOp
import org.tensorflow.lite.support.metadata.schema.ImageSize
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ImageViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageViewBinding
    lateinit var bitmap: Bitmap
    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        val ImageSize = 224


    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {

            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)
        binding = ActivityImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.padi)
        var imageProcessor = ImageProcessor.Builder()
//            .add(NormalizeOp(0.0f, 255.0f))
//            .add(TransformToGrayscaleOp())
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        binding.back.setOnClickListener {
            val intent = Intent(this, NavigasiActivity::class.java)
            startActivity(intent)
        }

        binding.starcameraButton.setOnClickListener { startCameraX() }
        binding.filephoto.setOnClickListener { startGallery() }
        binding.predictcameraButton.setOnClickListener{
            var tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)

            tensorImage = imageProcessor.process(tensorImage)
            val model = ModelSize.newInstance(this)

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(tensorImage.buffer)

            // Reshape the buffer byte if needed.
            val expectedSize = 1 * 224 * 224 * 3 * 4 // Untuk FLOAT32, 4 byte per float
            if (tensorImage.buffer.capacity() != expectedSize) {
                val resizedBuffer = ByteBuffer.allocateDirect(expectedSize)
                tensorImage.buffer.rewind()
                tensorImage.buffer.limit(expectedSize) // Menetapkan batas pada buffer yang ada
                resizedBuffer.put(tensorImage.buffer)
//                tensorImage.buffer = resizedBuffer // Mengganti buffer pada tensorImage dengan buffer yang diubah
            }

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            var maxIdx = 0
            outputFeature0.forEachIndexed { index, fl ->
                if (outputFeature0[maxIdx] < fl) {
                    maxIdx = index
                }
            }

            val result = maxIdx.toString()


            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_NAME, result)

// Mengirim data gambar ke DetailActivity
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            intent.putExtra(DetailActivity.EXTRA_IMAGE, byteArray)

            startActivity(intent)

            // Releases model resources if no longer used.
            model.close()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                val selectedImg = Uri.fromFile(file)
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImg)
                    binding.imageviewcamera.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }            }
        }
    }



    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImg)

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@ImageViewActivity)
                binding.imageviewcamera.setImageURI(uri)
            }

        }
    }
}