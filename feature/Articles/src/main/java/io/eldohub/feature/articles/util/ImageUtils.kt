package io.eldohub.feature.articles.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val file = File(
            context.filesDir,
            "article_${System.currentTimeMillis()}.jpg"
        )
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        Uri.fromFile(file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
