package com.rsnimchenko.simple_arkanoid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.window.layout.WindowMetricsCalculator

class BreakoutGame : AppCompatActivity() {
    private lateinit var breakoutView: BreakoutView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FullScreen.fullScreen(this)
        FullScreen.enableFullScreen(this)

        breakoutView = BreakoutView(this)
        setContentView(breakoutView)
    }

    override fun onResume() {
        super.onResume()
        breakoutView.resume()
    }

    override fun onPause() {
        super.onPause()
        breakoutView.pause()
    }

    class BreakoutView(context: Context) : SurfaceView(context), Runnable {
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

        private val paddle = Paddle(screenX, screenY)
        private val ball = Ball()

        val bricks = mutableListOf<Brick>()

        init {
            createBricksAndRestart()
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
                    }
                }
            }

            if (ball.rect.top >= paddle.rect.top &&
                ball.rect.right <= paddle.rect.right &&
                ball.rect.left >= paddle.rect.left
            ) {
                ball.setRandomXVelocity()
                ball.reverseYVelocity()
                ball.clearObstacleY(paddle.rect.top - 2)
            }

            if (ball.rect.bottom > screenY) {
                ball.reverseYVelocity()
                ball.clearObstacleY(screenY - 2f)
            }

            if (ball.rect.top < 0) {
                ball.reverseYVelocity()
                ball.clearObstacleY(BALL_SIZE + 2f)
            }

            if (ball.rect.left < 0) {
                ball.reverseXVelocity()
                ball.clearObstacleX(2f)
            }

            if (ball.rect.right > screenX - BALL_SIZE) {
                ball.reverseXVelocity()
                ball.clearObstacleX(screenX - (BALL_SIZE * 2 + 2f))
            }
        }

        private fun draw() {
            if (ourHolder.surface.isValid) {
                canvas = ourHolder.lockCanvas()
                canvas.drawColor(Color.argb(255, 26, 128, 182))
                paint.color = Color.argb(255, 255, 255, 255)

                canvas.drawRect(paddle.rect, paint)
                canvas.drawRect(ball.rect, paint)

                paint.color = Color.argb(255, 249, 129, 0)
                bricks.forEach {
                    if (it.getVisibility()) canvas.drawRect(it.rect, paint)
                }

                ourHolder.unlockCanvasAndPost(canvas)
            }
        }

        private fun createBricksAndRestart() {
            ball.reset(screenX, screenY)

            val brickWidth = screenX / 8
            val brickHeight = screenY / 10

            for (column in 0 until 8) {
                for (row in 0 until 3) {
                    bricks.add(Brick(row, column, brickWidth, brickHeight))
                }
            }
        }

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
    }
}