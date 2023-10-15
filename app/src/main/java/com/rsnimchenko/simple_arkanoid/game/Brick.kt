package com.rsnimchenko.simple_arkanoid.game

import android.graphics.Paint
import android.graphics.RectF

class Brick(row: Int, column: Int, width: Int, height: Int, val paint: Paint) {
    private val padding = 5f
    private var isVisible = true
    val rect = RectF(
        column * width + padding,
        row * height + padding + TOP_PADDING,
        column * width + width - padding,
        row * height + height - padding + TOP_PADDING
    )

    fun setInvisible() {
        isVisible = false
    }

    fun getVisibility() = isVisible
}