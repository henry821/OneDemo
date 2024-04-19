package com.demo.modules.gallery.crop

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.net.toUri
import com.demo.one.databinding.GalleryCropActivityBinding
import java.io.File

/**
 * 图片裁剪页
 */
class GalleryCropActivity : AppCompatActivity() {

    private lateinit var binding: GalleryCropActivityBinding
    private val imageUri by lazy {
        IntentCompat.getParcelableExtra(intent, KEY_URI, Uri::class.java) ?: Uri.EMPTY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GalleryCropActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cropView.setImageBitmap(
            MediaStore.Images.Media.getBitmap(
                this.contentResolver,
                imageUri
            )
        )
        binding.confirm.setOnClickListener {
            setResult(RESULT_OK, Intent().also { it.putExtra(KEY_RESULT, imageUri.toString()) })
            finish()
        }
        binding.back.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        val type = intent.getIntExtra(KEY_CROP_TYPE, 0)
        binding.coverView.setFromAvatar(type == 1)
    }

    class CropActivityResultContract(val type: Int = 0) : ActivityResultContract<Uri, Uri>() {
        override fun createIntent(context: Context, input: Uri) =
            Intent(context, GalleryCropActivity::class.java).also {
                it.putExtra(KEY_URI, input)
                it.putExtra(KEY_CROP_TYPE, type)
            }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri {
            if (resultCode != RESULT_OK) return Uri.EMPTY
            val path = intent?.getStringExtra(KEY_RESULT) ?: ""
            return if (path.isBlank()) Uri.EMPTY else File(path).toUri()
        }
    }

    companion object {
        private const val KEY_URI = "key_uri"
        private const val KEY_CROP_TYPE = "key_crop_type"
        private const val KEY_RESULT = "key_result"
    }
}