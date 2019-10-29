package com.edit.image.sample.aty

import android.os.Bundle
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.api.setImage
import kotlinx.android.synthetic.main.activity_edit.*

/**
 * @author y
 * @create 2019-04-23
 */
class ImageBigActivity : Base() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Large Picture"
        view_edit.setImage(ImageSource.asset("111.jpg").tilingDisabled())
    }

}