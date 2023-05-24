package com.foodline.global.helper


import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.foodline.BuildConfig
import com.foodline.global.utils.DebugLog
import com.foodline.global.utils.TAG
import com.foodline.global.utils.deleteDirectory
import java.io.*

const val IMAGE_TEMP_FILE = "images/temps"
const val CAMERA_PROVIDER = ".provider"
const val CAMERA_INTENT_EXTRA = "return-data"

const val TYPE_OF_FILE_IMAGE = "image/*"
const val DEFAULT_FILE_NAME = "default_img.jpg"

object ImagePicker {

    private const val IMAGE_MAX_SIZE = 2 * 1024 * 1024 // 2Mb
    private const val IMAGE_MAX_RESOLUTION = 1200

    const val PICK_IMAGE_CAMERA_ID = 998
    const val PICK_IMAGE_GALLERY_ID = 999

    fun getUriFromResult(
        context: Context,
        resultCode: Int,
        imageReturnedIntent: Intent?,
        isCamera: Boolean,
        fileName: String = DEFAULT_FILE_NAME
    ): Uri {
        DebugLog.d(
            TAG,
            "getImageFromResult, resultCode: $resultCode imageReturnedIntent $imageReturnedIntent isCamera $isCamera"
        )
        var uri = Uri.EMPTY
        if (resultCode == Activity.RESULT_OK) {
            uri = if (isCamera) {
                /** CAMERA  */
                val imageFile = getTempFile(context, fileName)
                Uri.fromFile(imageFile)
            } else {
                /** ALBUM  */
                imageReturnedIntent?.data
            }
            return decodeBitmap(context, uri, fileName)
        }
        return uri
    }

    private fun decodeBitmap(
        context: Context,
        uri: Uri?,
        fileName: String = DEFAULT_FILE_NAME
    ): Uri {

        var actuallyBitmap: Bitmap
        var fileDescriptor: AssetFileDescriptor? = null
        var out: FileOutputStream? = null
        val tempFile: File

        try {
            uri!!
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true

            fileDescriptor = context.contentResolver.openAssetFileDescriptor(uri, "r")
            BitmapFactory.decodeFileDescriptor(fileDescriptor!!.fileDescriptor, null, options)
            DebugLog.d(
                TAG,
                options.inSampleSize.toString() + " sample method bitmap ... " + options.outWidth + " " + options.outHeight
            )
            // Calculate inSampleSize
            val inSampleSize = calculateInSampleSize(
                options,
                IMAGE_MAX_RESOLUTION
            )
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            options.inSampleSize = inSampleSize


            actuallyBitmap =
                BitmapFactory.decodeFileDescriptor(fileDescriptor.fileDescriptor, null, options)
            DebugLog.d(
                TAG,
                options.inSampleSize.toString() + " sample method bitmap ... " + actuallyBitmap.width + " " + actuallyBitmap.height
            )

            val rotation = getRotation(context, uri)
            actuallyBitmap = rotate(actuallyBitmap, rotation)
            actuallyBitmap = scaleBitmap(actuallyBitmap)

            tempFile = getTempFile(context, fileName)

            out = FileOutputStream(tempFile)
            actuallyBitmap.compress(
                Bitmap.CompressFormat.JPEG,
                getCompressRatio(actuallyBitmap),
                out
            )
            actuallyBitmap.recycle()
            out.flush()

        } catch (e: Exception) {
            DebugLog.e(TAG, e.toString())
            return Uri.EMPTY
        } finally {
            try {
                fileDescriptor?.close()
                out?.close()
            } catch (e: Exception) {
                DebugLog.e(TAG, e.toString())
            }

        }
        return Uri.fromFile(tempFile)
    }

    private fun getCompressRatio(bitmap: Bitmap): Int {
        var compressQuality = 104 // quality decreasing by 5 every loop. (start from 99)
        try {
            var streamLength = IMAGE_MAX_SIZE
            while (streamLength >= IMAGE_MAX_SIZE) {
                val bmpStream = ByteArrayOutputStream()
                compressQuality -= 5
                DebugLog.d(TAG, "Quality: $compressQuality")
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
                val bmpPicByteArray = bmpStream.toByteArray()
                streamLength = bmpPicByteArray.size
                DebugLog.d(TAG, "Size: $streamLength")
            }
        } catch (e: Exception) {
            DebugLog.e(TAG, "getCompressRatio $e")
            compressQuality = 75
        }

        return compressQuality
    }

    private fun scaleBitmap(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        if (width > IMAGE_MAX_RESOLUTION || height > IMAGE_MAX_RESOLUTION) {
            val excessSizeRatio =
                if (width > height) width / IMAGE_MAX_RESOLUTION.toFloat() else height / IMAGE_MAX_RESOLUTION.toFloat()
            val newBitmap = Bitmap.createScaledBitmap(
                bitmap,
                (width / excessSizeRatio).toInt(),
                (height / excessSizeRatio).toInt(),
                false
            )

            if (newBitmap != bitmap) {
                bitmap.recycle()
            }
            return newBitmap
        }
        return bitmap
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, maxSize: Int): Int {

        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > maxSize || width > maxSize) {

            val halfHeight = height / 2
            val halfWidth = width / 2
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= maxSize || halfWidth / inSampleSize >= maxSize) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun getRotation(context: Context, imageUri: Uri): Int {

        var rotation = 0
        var inputStream: InputStream? = null
        try {
            inputStream = context.contentResolver.openInputStream(imageUri)
            val exifInterface = ExifInterface(inputStream!!)
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotation = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> rotation = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> rotation = 270
            }
            // Now you can extract any Exif tag you want
            // Assuming the image is a JPEG or supported raw format
        } catch (e: IOException) {
            // Handle any errors
        } finally {
            try {
                inputStream?.close()
            } catch (ignored: IOException) {
            }
        }
        DebugLog.d(TAG, "Image rotation: $rotation")
        return rotation
    }

    private fun rotate(bm: Bitmap, rotation: Int): Bitmap {
        if (rotation != 0) {
            val matrix = Matrix()
            matrix.postRotate(rotation.toFloat())
            return Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
        }
        return bm
    }


    fun getTempFile(context: Context, fileName: String = DEFAULT_FILE_NAME): File {
        val path = File(context.filesDir, IMAGE_TEMP_FILE)
        if (!path.exists()) path.mkdirs()
        return File(path, fileName)
    }

    fun getCameraIntent(context: Context, fileName: String = DEFAULT_FILE_NAME): Intent {
        val uriForFile = FileProvider.getUriForFile(
            context,
            BuildConfig.APPLICATION_ID + CAMERA_PROVIDER,
            getTempFile(context, fileName)
        )
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(CAMERA_INTENT_EXTRA, true)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile)
        takePhotoIntent.clipData = ClipData.newRawUri("", uriForFile)
        takePhotoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)

        return takePhotoIntent
    }

    fun getGalleryIntent(): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = TYPE_OF_FILE_IMAGE
        return intent
    }

    fun clear(context: Context) {
        val path = File(context.filesDir, IMAGE_TEMP_FILE)
        path.deleteDirectory()
    }

}