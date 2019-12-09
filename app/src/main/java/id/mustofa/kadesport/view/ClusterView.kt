/*
 * Mustofa on 12/4/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.view

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import id.mustofa.kadesport.R
import org.jetbrains.anko.*

class ClusterView(context: Context) : _LinearLayout(context) {

  private val header: FrameLayout

  private lateinit var headerTitle: TextView

  init {
    lparams(matchParent) {
      orientation = VERTICAL
      bottomPadding = dip(16)
    }
    header = frameLayout {
      lparams(matchParent) {
        horizontalPadding = dip(16)
      }
      applyClickEffect()
      linearLayout {
        lparams(matchParent) {
          orientation = HORIZONTAL
          gravity = Gravity.CENTER
        }
        // head title
        headerTitle = textView {
          textSize = 18f
          typeface = Typeface.DEFAULT_BOLD
        }.lparams(width = 0, weight = 1F) {
          verticalPadding = dip(8)
        }
        // head arrow
        imageView(R.drawable.ic_arrow_forward) {
        }.lparams {
          marginStart = dip(8)
        }
      }
    }
    // children
  }

  fun setTitle(title: String) {
    headerTitle.text = title
  }

  override fun setOnClickListener(l: OnClickListener?) {
    header.setOnClickListener(l)
  }
}