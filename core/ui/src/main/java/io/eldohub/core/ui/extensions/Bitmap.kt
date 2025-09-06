package io.eldohub.core.ui.extensions

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.util.UUID

fun Bitmap.toFile(context: Context, fileName: String = "${UUID.randomUUID()}.jpg"): File {
    val file = File(context.cacheDir, fileName)
    file.outputStream().use { outStream ->
        this.compress(Bitmap.CompressFormat.JPEG, 80, outStream)
        outStream.flush()
    }
    return file
}
