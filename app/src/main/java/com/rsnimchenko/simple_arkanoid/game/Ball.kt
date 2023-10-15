package com.rsnimchenko.simple_arkanoid.game

import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import kotlin.math.abs
import kotlin.random.Random

class Ball {
    val rect = RectF()
    private var xVelocity = 0f
    private var yVelocity = 1f
    var paint = Paint().apply { color = Color.argb(255, 255, 255, 255) }

    fun update(fps: Long) {
        rect.left += xVelocity / fps
        rect.top += yVelocity / fps
        rect.right = rect.left + BALL_SIZE
        rect.bottom = rect.top + BALL_SIZE
    }

    fun reverseYVelocity() {
        if (abs(yVelocity) == 1f) yVelocity = 600f
        yVelocity = -yVelocity
    }

    fun reverseXVelocity() {
        xVelocity = -xVelocity
    }

    fun calculateXVelocity(paddleMoving: PaddleMoving) {
        when {
            xVelocity == 0f -> {
                xVelocity = Random.nextInt(300, 600).toFloat()
                if (Random.nextInt(2) == 0) reverseXVelocity()
            }

            paddleMoving == PaddleMoving.LEFT && xVelocity > 0 -> {
                xVelocity = Random.nextInt(-600, -300).toFloat()
            }

            paddleMoving == PaddleMoving.RIGHT && xVelocity < 0 -> {
                xVelocity = Random.nextInt(300, 600).toFloat()
            }

            else -> {}
        }
    }

    fun clearObstacleY(y: Float) {
        rect.run {
            top = y - BALL_SIZE
            bottom = y
        }
    }

    fun clearObstacleX(x: Float) {
        rect.left = x
        rect.right = x + BALL_SIZE
    }

    fun reset(paddleLeft: Float, paddleRight: Float, paddleTop: Float) {
        paint.color = Color.argb(255, 255, 255, 255)
        rect.left = (paddleLeft + paddleRight) / 2f - BALL_SIZE / 2f
        rect.right = rect.left + BALL_SIZE
        rect.top = paddleTop - BALL_SIZE
        rect.bottom = paddleTop
    }
}