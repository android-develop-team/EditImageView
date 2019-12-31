package com.image.edit.eraser

import android.graphics.PointF

data class EraserPath(val pointFList: List<PointF>, val width: Float, val color: Int, val scale: Float)