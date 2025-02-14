package com.edit.image.sample

import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView

/**
 * @author y
 * @create 2019-04-23
 */
class NewBitmapDialog : DialogFragment() {

    companion object {
        fun new(bitmap: Bitmap, fragmentManager: FragmentManager) {
            val newBitmapDialog = NewBitmapDialog()
            newBitmapDialog.show(bitmap)
            newBitmapDialog.show(fragmentManager, NewBitmapDialog::class.java.simpleName)
        }
    }

    private var bitmap: Bitmap? = null
    var newImage: SubsamplingScaleImageView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val mRootView = View.inflate(activity, R.layout.dialog_save_new_image, null)
        newImage = mRootView.findViewById(R.id.save_new_image)
        builder.setView(mRootView)
        return builder.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newImage?.setDebug(BuildConfig.DEBUG)
        bitmap?.let { newImage?.setImage(ImageSource.cachedBitmap(it)) }
    }

    fun show(bitmap: Bitmap) {
        this.bitmap = bitmap
    }
}
