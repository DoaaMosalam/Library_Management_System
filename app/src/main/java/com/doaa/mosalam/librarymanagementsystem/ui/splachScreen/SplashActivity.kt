package com.doaa.mosalam.librarymanagementsystem.ui.splachScreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.animation.AnticipateInterpolator
import android.view.animation.BounceInterpolator
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import com.doaa.mosalam.librarymanagementsystem.MainActivity
import com.doaa.mosalam.librarymanagementsystem.common.BasicActivity
import com.doaa.mosalam.librarymanagementsystem.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BasicActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        initSplashScreen()
        super.onCreate(savedInstanceState)
        val motionLayout = binding.motionLayout

        motionLayout(motionLayout)

//        animationIcon()
    }

    private fun animationIcon() {
        val logo = binding.imageMotion

        // Animation 1: move to the top
        val moveUp = ObjectAnimator.ofFloat(logo, "translationY", 0f, -300f).apply {
            duration = 1000
        }

        // Animation 2: Bounce
        val moveDown = ObjectAnimator.ofFloat(logo, "translationY", -300f, 0f).apply {
            duration = 1000
            interpolator = BounceInterpolator()
        }

        // Animation 3: big and small
        val scaleX = ObjectAnimator.ofFloat(logo, "scaleX", 1f, 1.2f, 1f).apply {
            duration = 800
        }
        val scaleY = ObjectAnimator.ofFloat(logo, "scaleY", 1f, 1.2f, 1f).apply {
            duration = 800
        }
// Combine all animations in a set
        AnimatorSet().apply {
            playSequentially(moveUp, moveDown, scaleX, scaleY)
            start()
        }

        goToHomeActivity()
    }

    private fun motionLayout(motionLayout: MotionLayout) {
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                // When the transition is completed, we navigate to the home activity
                goToHomeActivity()
            }

            override fun onTransitionTrigger(
                p0: MotionLayout?,
                p1: Int,
                p2: Boolean,
                p3: Float
            ) {
            }
        })
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val options = ActivityOptions.makeCustomAnimation(
            this, android.R.anim.fade_in, android.R.anim.fade_out
        )
        startActivity(intent, options.toBundle())
        finish()
    }

    // This method is used to hide the status bar and make the splash screen as a full screen activity.
    private fun initSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setOnExitAnimationListener { splashScreenViewProvider: SplashScreenViewProvider ->
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenViewProvider.view,
                    "translationY",
                    0f,
                    -splashScreenViewProvider.view.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 1000L
                slideUp.doOnEnd { splashScreenViewProvider.remove() }
                slideUp.start()
            }
        } else {
            setTheme(androidx.appcompat.R.style.Theme_AppCompat)
        }
    }
}