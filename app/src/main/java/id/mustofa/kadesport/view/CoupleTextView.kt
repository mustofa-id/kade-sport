/*
 * Mustofa on 12/5/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.view

import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ext.splitLiner
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.tintedImageView

class CoupleTextView(context: Context) : _LinearLayout(context) {

  private val leftText: TextView
  private val rightText: TextView
  private val icon: ImageView

  init {
    id = R.id.sectionInfoView
    orientation = HORIZONTAL
    weightSum = 2f
    bottomPadding = dip(8)
    lparams(matchParent)
    leftText = textView {
      horizontalPadding = dip(8)
      gravity = Gravity.END
    }.lparams(0, weight = 1f)
    icon = tintedImageView()
      .lparams { gravity = Gravity.CENTER }
    rightText = textView {
      horizontalPadding = dip(8)
    }.lparams(0, weight = 1f)
  }

  fun setLeftText(text: String?) {
    leftText.text = text.splitLiner()
  }

  fun setRightText(text: String?) {
    rightText.text = text.splitLiner()
  }

  fun setIcon(iconRes: Int, colorRes: Int) {
    icon.setImageResource(iconRes)
    icon.setColorFilter(ContextCompat.getColor(context, colorRes))
  }
}