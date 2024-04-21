package com.example.recipes_app_prev

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)

        val swipeButton: Button = findViewById(R.id.button_swipe)

        val gestureDetector = GestureDetector(this, SwipeGestureListener())

        swipeButton.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close the welcome activity
    }

    inner class SwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val deltaX: Float = e2.x - (e1?.x ?: 0.0f )
            if (deltaX < 0) {
                // Swipe right, do nothing
            } else {
                // Swipe left, start main activity
                startMainActivity()
            }
            return true
        }
    }
}
