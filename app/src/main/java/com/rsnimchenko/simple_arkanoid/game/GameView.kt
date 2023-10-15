package com.rsnimchenko.simple_arkanoid.game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.window.layout.WindowMetricsCalculator
import com.rsnimchenko.simple_arkanoid.SCORE
import com.rsnimchenko.simple_arkanoid.ui.MainActivity

class GameView(context: Context) : SurfaceView(context), Runnable {
    private var gameThread: Thread? = null
    private val ourHolder = holder

    @Volatile
    var playing: Boolean? = null
    private var paused = true
    private lateinit var canvas: Canvas
    private val paint = Paint()
    private var fps = 0L
    private var timeThisFrame = 0L

    private val metrics =
        WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context)
    private val screenX = metrics.bounds.width()
    private val screenY = metrics.bounds.height()

    private val paddle = Paddle(screenX)
    private val ball = Ball()

    private val bricks = mutableListOf<Brick>()

    private var health = 3
    private var score = 0

    init {
        setupGame()
    }

    override fun run() {
        while (playing == true) {
            val startFrameTime = System.currentTimeMillis()
            if (!paused) update()
            draw()
            timeThisFrame = System.currentTimeMillis() - startFrameTime
            if (timeThisFrame >= 1) fps = 1000 / timeThisFrame
        }
    }

    private fun update() {
        paddle.update(fps)
        ball.update(fps)

        bricks.forEach {
            if (it.getVisibility()) {
                if (RectF.intersects(it.rect, ball.rect)) {
                    it.setInvisible()
                    ball.reverseYVelocity()
                    ball.paint = it.paint
                    score += 10
                    if (score == 240) openFinishScreen()
                }
            }
        }

        if (RectF.intersects(paddle.rect, ball.rect)) {
            ball.calculateXVelocity(paddle.paddleMoving)
            ball.reverseYVelocity()
            ball.clearObstacleY(paddle.rect.top - 1f)
            paddle.paint = ball.paint
        }

        if (ball.rect.bottom > screenY) {
            bottomColliding()
            health -= 1
            if (health == 0) openFinishScreen()
        }

        if (ball.rect.top < TOP_PADDING) {
            ball.reverseYVelocity()
            ball.clearObstacleY(BALL_SIZE + TOP_PADDING + 1f)
            ball.paint.color = Color.argb(255, 255, 255, 255)
        }

        if (ball.rect.left < 0) {
            ball.reverseXVelocity()
            ball.clearObstacleX(1f)
        }

        if (ball.rect.right > screenX) {
            ball.reverseXVelocity()
            ball.clearObstacleX(screenX - (BALL_SIZE - 1f))
        }
    }

    private fun draw() {
        if (ourHolder.surface.isValid) {
            canvas = ourHolder.lockCanvas()
            canvas.drawColor(Color.argb(255, 0, 170, 255))

            canvas.drawRoundRect(paddle.rect, 20f, 20f, paddle.paint)
            canvas.drawOval(ball.rect, ball.paint)

            bricks.forEach {
                if (it.getVisibility()) canvas.drawRoundRect(it.rect, 15f, 15f, it.paint)
            }

            paint.color = Color.argb(255, 255, 255, 255)
            paint.textSize = TOP_PADDING
            canvas.drawText("Score $score", 0f, TOP_PADDING, paint)

            for (i in 1..health) {
                canvas.drawOval(
                    screenX - BALL_SIZE * i.toFloat() - 200f - i * 10f,
                    0f,
                    screenX - BALL_SIZE * (i - 1).toFloat() - 200f - i * 10f,
                    BALL_SIZE,
                    paint
                )
            }

            ourHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun setupGame() {
        paddle.reset(screenX, screenY)
        ball.reset(paddle.rect.left, paddle.rect.right, paddle.rect.top)

        val brickWidth = screenX / 8
        val brickHeight = screenY / 10

        if (bricks.isEmpty())
            for (column in 0 until 8) {
                for (row in 0 until 3) {
                    val paint = when (row) {
                        0 -> Paint().apply { color = Color.argb(255, 37, 142, 39) }
                        1 -> Paint().apply { color = Color.argb(255, 141, 66, 90) }
                        2 -> Paint().apply { color = Color.argb(255, 255, 128, 173) }
                        else -> Paint()
                    }
                    bricks.add(Brick(row, column, brickWidth, brickHeight, paint))
                }
            }
    }

    private fun bottomColliding() {
        paddle.reset(screenX, screenY)
        ball.reset(paddle.rect.left, paddle.rect.right, paddle.rect.top)
        paused = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action?.and(MotionEvent.ACTION_MASK)) {
            MotionEvent.ACTION_DOWN -> {
                paused = false
                if (event.x > screenX / 2) paddle.setMovementState(PaddleMoving.RIGHT)
                else paddle.setMovementState(PaddleMoving.LEFT)
            }

            MotionEvent.ACTION_UP -> paddle.setMovementState(PaddleMoving.STOPPED)
        }

        return true
    }

    fun pause() {
        playing = false
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            Log.e("mylog", "${e.message}")
        }
    }

    fun resume() {
        playing = true
        gameThread = Thread(this).apply { start() }
    }

    private fun openFinishScreen() {
        (context as Activity).let {
            it.startActivity(Intent(it, MainActivity::class.java).apply {
                putExtra(SCORE, score)
            })
            it.finish()
        }
    }
}