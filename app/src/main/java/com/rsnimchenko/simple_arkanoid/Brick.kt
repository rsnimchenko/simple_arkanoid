package com.rsnimchenko.simple_arkanoid

import android.graphics.RectF

class Brick(row: Int, column: Int, width: Int, height: Int) {
    private val padding = 5f
    private var isVisible = true
    val rect = RectF(
        column * width + padding,
        row * height + padding,
        column * width + width - padding,
        row * height + height - padding
    )

    fun setInvisible() {
        isVisible = false
    }

    fun getVisibility() = isVisible
}