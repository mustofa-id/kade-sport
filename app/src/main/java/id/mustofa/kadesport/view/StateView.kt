/*
 * Mustofa on 12/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.view

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.State
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class StateView<T>(context: Context) : _FrameLayout(context) {

  private val recyclerView = recyclerView()
  private val loadingView = progressBar()
  private val messageView: LinearLayout

  private lateinit var messageImage: ImageView
  private lateinit var messageText: TextView

  init {
    messageView = verticalLayout {
      messageImage = imageView()
        .lparams(matchParent, dip(92))
      messageText = textView {
        textSize = 24f
        typeface = Typeface.DEFAULT_BOLD
        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
      }.lparams(matchParent)
    }

    applyRecursively {
      when (it) {
        is LinearLayout, is ProgressBar ->
          it.apply {
            lparams(matchParent) {
              gravity = Gravity.CENTER
              padding = dip(16)
            }
          }
        is RecyclerView, is FrameLayout ->
          it.lparams(matchParent, matchParent)
      }
    }
  }

  fun setup(adapter: Adapter<*>, manager: LayoutManager) {
    recyclerView.adapter = adapter
    recyclerView.layoutManager = manager
  }

  fun handleState(state: State<T>, onResult: (T?) -> Unit) {
    when (state) {
      is State.Loading -> isLoading(true)
      is State.Error -> setMessage(R.drawable.ic_error, state.message)
      is State.Empty -> {
        setMessage(R.drawable.ic_empty, R.string.msg_empty_result)
        onResult(null)
      }
      is State.Success -> {
        isLoading(false)
        onResult(state.data)
      }
    }
  }

  private fun isLoading(constraint: Boolean) {
    loadingView.isVisible = constraint
    messageView.isVisible = false
  }

  private fun setMessage(image: Int, text: Int) {
    loadingView.isVisible = false
    messageView.isVisible = true
    messageImage.setImageResource(image)
    messageText.setText(text)
  }
}