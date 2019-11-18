/*
 * Mustofa on 11/18/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.common

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.kadesport.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class ListingView(context: Context, initView: RecyclerView.() -> Unit) : _FrameLayout(context) {

  constructor(context: Context) : this(context, {})

  private val progress: View
  private val messageContainer: View

  private lateinit var messageText: TextView
  private lateinit var messageImage: ImageView

  init {
    id = R.id.parentContainer
    lparams(matchParent, matchParent)
    recyclerView {
      id = R.id.items
      initView()
    }.lparams(matchParent, matchParent)

    progress = progressBar {
      id = R.id.loadingProgress
      isIndeterminate = true
    }.lparams(matchParent) {
      gravity = Gravity.CENTER
    }

    messageContainer = verticalLayout {
      lparams(matchParent, matchParent)
      id = R.id.messageContainer
      gravity = Gravity.CENTER
      padding = dip(16)
      messageImage = imageView {
        id = R.id.messageImage
      }.lparams(matchParent, dip(92))
      messageText = textView {
        id = R.id.messageText
        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        textSize = 16f
      }.lparams(matchParent)
    }
  }

  fun isLoading(constant: Boolean) {
    progress.isVisible = constant
  }

  fun setMessage(
    @StringRes message: Int = 0,
    @DrawableRes image: Int = R.drawable.ic_error
  ) {
    messageContainer.isVisible = (message != 0).also {
      if (it) {
        messageImage.imageResource = image
        messageText.setText(message)
      }
    }
  }
}