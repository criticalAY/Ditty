package com.uchi.ditty.recyclerView

import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.uchi.ditty.Post
import com.uchi.ditty.R

class VideoAdapter(private val videos: List<Post> = emptyList()) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    private var videoList: MutableList<Post> = mutableListOf()
    private var currentlyPlayingPosition: Int = -1
    private var isVideoPlaying: Boolean = false

    fun setVideoList(videos: List<Post>) {
        videoList.addAll(videos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.short_card_view, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]
        holder.bind(video, position)
        holder.startVideoIfNeeded(position == currentlyPlayingPosition)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, MediaPlayer.OnCompletionListener, View.OnAttachStateChangeListener {
        private val videoView: VideoView = itemView.findViewById(R.id.video_view)
        private val title: TextView = itemView.findViewById(R.id.video_title)
        private val description: TextView = itemView.findViewById(R.id.video_description)
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.short_thumbnail)
        private lateinit var mediaUrl: String
        private var isVideoVisible: Boolean = false
        private var currentPosition: Int = -1

        init {
            thumbnailImageView.setOnClickListener(this)
            videoView.setOnClickListener(this)
            videoView.setOnCompletionListener(this)
            itemView.addOnAttachStateChangeListener(this)
        }

        fun bind(video: Post, position: Int) {
            mediaUrl = video.submission.mediaUrl
            currentPosition = position

            title.text = video.submission.title
            description.text = video.submission.description
            videoView.setVideoURI(Uri.parse(mediaUrl))
            videoView.visibility = View.GONE
            thumbnailImageView.visibility = View.VISIBLE
        }

        override fun onClick(v: View) {
            if (isVideoPlaying) {
                videoView.pause()
                isVideoPlaying = false
            } else {
                thumbnailImageView.visibility = View.GONE
                videoView.visibility = View.VISIBLE
                videoView.start()
                isVideoPlaying = true
                currentlyPlayingPosition = currentPosition
            }
        }

        fun startVideoIfNeeded(isCurrentlyPlaying: Boolean) {
            if (isVideoVisible) {
                if (isCurrentlyPlaying) {
                    videoView.start()
                } else {
                    videoView.pause()
                }
            }
        }

        override fun onCompletion(mp: MediaPlayer) {
            videoView.seekTo(0)
            videoView.start()
        }
        override fun onViewAttachedToWindow(v: View) {
            startVideoIfNeeded(currentPosition == currentlyPlayingPosition)
        }

        override fun onViewDetachedFromWindow(v: View) {
            // Pause the video and show the thumbnail when the view is detached
            videoView.pause()
            isVideoVisible = false
            thumbnailImageView.visibility = View.VISIBLE
            videoView.visibility = View.GONE
        }
    }
}