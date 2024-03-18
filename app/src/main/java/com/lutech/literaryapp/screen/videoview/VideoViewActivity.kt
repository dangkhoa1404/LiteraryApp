package com.lutech.literaryapp.screen.videoview

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.lutech.literaryapp.R
import com.lutech.literaryapp.base.BaseActivity
import com.lutech.literaryapp.utils.Constants
import kotlinx.android.synthetic.main.activity_video_view.player
import kotlinx.android.synthetic.main.activity_video_view.progress_bar
import kotlinx.android.synthetic.main.custom_controller.exo_duration
import kotlinx.android.synthetic.main.custom_controller.exo_exit
import kotlinx.android.synthetic.main.custom_controller.exo_play_pause
import kotlinx.android.synthetic.main.custom_controller.exo_position
import kotlinx.android.synthetic.main.custom_controller.tvNameVideo
import kotlinx.coroutines.cancel

class VideoViewActivity : BaseActivity() {

    private var fileName : String = ""

    private var filePath : String = ""

    private var simpleExoPlayer: ExoPlayer? = null

    private var hasPrepare = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setContentView(R.layout.activity_video_view)
        initData()
        initView()
        handleEvent()
    }

    private fun initData() {
        fileName = intent.getStringExtra(Constants.KEY_FILE_NAME)!!
        filePath = intent.getStringExtra(Constants.KEY_FILE_PATH)!!

        simpleExoPlayer = null
        simpleExoPlayer = ExoPlayer.Builder(this).setSeekBackIncrementMs(5000).setSeekForwardIncrementMs(5000).build()
        player.player = simpleExoPlayer
        player.keepScreenOn = true

        val mediaItem = MediaItem.fromUri(Uri.parse(filePath))
        simpleExoPlayer!!.setMediaItem(mediaItem)
        simpleExoPlayer!!.prepare()
        hasPrepare = true
    }

    private fun initView() {
        tvNameVideo.requestFocus()
        tvNameVideo.text = fileName
    }

    private fun handleEvent() {

        exo_exit.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        exo_play_pause.setOnClickListener {
            if (simpleExoPlayer != null) {
                if (simpleExoPlayer!!.isPlaying) {
                    simpleExoPlayer!!.pause()
                    exo_play_pause.setImageResource(R.drawable.ic_round_play_arrow_24)
                } else {
                    if (!exo_position.text.equals(exo_duration.text)) {
                        simpleExoPlayer!!.play()
                    } else {
                        simpleExoPlayer!!.seekTo(0)
                        simpleExoPlayer!!.playWhenReady = true
                    }
                    exo_play_pause.setImageResource(R.drawable.ic_baseline_pause_24)
                }
            }
        }

        simpleExoPlayer!!.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    progress_bar.visibility = View.VISIBLE
                } else if (playbackState == Player.STATE_READY) {
                    progress_bar.visibility = View.GONE
                } else if (playbackState == Player.STATE_ENDED) {
                    exo_play_pause.setImageResource(R.drawable.ic_round_play_arrow_24)
                }
            }
        })
    }

    private fun setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun onDestroy() {
        lifecycleScope.cancel()
        super.onDestroy()
        if (simpleExoPlayer != null && simpleExoPlayer!!.isPlaying) {
            simpleExoPlayer!!.pause()
            simpleExoPlayer!!.stop()
        }
        simpleExoPlayer!!.release()
    }

    override fun onPause() {
        super.onPause()
        if (simpleExoPlayer != null) {
            if (simpleExoPlayer!!.isPlaying) {
                simpleExoPlayer!!.playWhenReady = false
                simpleExoPlayer!!.pause()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        if (hasPrepare && simpleExoPlayer != null && !simpleExoPlayer!!.isPlaying) {
            initData()
            simpleExoPlayer!!.play()
        }
    }

}