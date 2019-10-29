package com.davemorrissey.labs.subscaleview.temp

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.AnyThread
import androidx.exifinterface.media.ExifInterface
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.Companion.TAG

/**
 * Helper method for load tasks. Examines the EXIF info on the image file to determine the orientation.
 * This will only work for external files, not assets, resources or other URIs.
 */
@AnyThread
fun Context.getExifOrientation(sourceUri: String): Int {
    var exifOrientation = ViewValues.ORIENTATION_0
    if (sourceUri.startsWith(ContentResolver.SCHEME_CONTENT)) {
        var cursor: Cursor? = null
        try {
            val columns = arrayOf(MediaStore.Images.Media.ORIENTATION)
            cursor = contentResolver.query(Uri.parse(sourceUri), columns, null, null, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    val orientation = cursor.getInt(0)
                    if (ViewValues.VALID_ORIENTATIONS.contains(orientation) && orientation != ViewValues.ORIENTATION_USE_EXIF) {
                        exifOrientation = orientation
                    } else {
                        Log.w(TAG, "Unsupported orientation: $orientation")
                    }
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Could not get orientation of image from media store")
        } finally {
            cursor?.close()
        }
    } else if (sourceUri.startsWith(ImageSource.FILE_SCHEME) && !sourceUri.startsWith(ImageSource.ASSET_SCHEME)) {
        try {
            val exifInterface = ExifInterface(sourceUri.substring(ImageSource.FILE_SCHEME.length - 1))
            val orientationAttr = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            if (orientationAttr == ExifInterface.ORIENTATION_NORMAL || orientationAttr == ExifInterface.ORIENTATION_UNDEFINED) {
                exifOrientation = ViewValues.ORIENTATION_0
            } else if (orientationAttr == ExifInterface.ORIENTATION_ROTATE_90) {
                exifOrientation = ViewValues.ORIENTATION_90
            } else if (orientationAttr == ExifInterface.ORIENTATION_ROTATE_180) {
                exifOrientation = ViewValues.ORIENTATION_180
            } else if (orientationAttr == ExifInterface.ORIENTATION_ROTATE_270) {
                exifOrientation = ViewValues.ORIENTATION_270
            } else {
                Log.w(TAG, "Unsupported EXIF orientation: $orientationAttr")
            }
        } catch (e: Exception) {
            Log.w(TAG, "Could not get EXIF orientation of image")
        }
    }
    return exifOrientation
}

fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> safeLet(p1: T1?, p2: T2?, p3: T3?, p4: T4?, block: (T1, T2, T3, T4) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null
}