package com.rsnimchenko.simple_arkanoid

import android.graphics.RectF
import kotlin.random.Random

class Ball {
    val rect = RectF()
    private var xVelocity = 200f
    private var yVelocity = -400f

    fun update(fps: Long) {
        rect.left += xVelocity / fps
        rect.top += yVelocity / fps
        rect.right = rect.left + BALL_SIZE
        rect.bottom = rect.top - BALL_SIZE
    }

    fun reverseYVelocity() {
        yVelocity = -yVelocity
    }

    fun reverseXVelocity() {
        xVelocity = -xVelocity
    }

    fun setRandomXVelocity() {
        if(Random.nextInt(2) == 0) reverseXVelocity()
    }

    fun clearObstacleY(y: Float) {
//        rect.bottom = y
//        rect.top = y - BALL_SIZE
    }

    fun clearObstacleX(x: Float) {
//        rect.left = x
//        rect.right = x + BALL_SIZE
    }

    fun reset(x: Int, y: Int) {
        rect.left = x / 2f - BALL_SIZE / 2f
        rect.top = y - PADDLE_HEIGHT * 2
        rect.right = rect.left + BALL_SIZE
        rect.bottom = rect.top - BALL_SIZE
    }
}