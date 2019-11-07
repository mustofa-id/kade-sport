/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.league

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView.ScaleType
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ext.applyClickEffect
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class LeagueItemView : AnkoComponent<ViewGroup> {

  override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
    cardView {
      applyClickEffect()
      lparams(
        width = matchParent,
        height = dip(186)
      ) { margin = 8 }

      imageView {
        id = R.id.itemBadge
        scaleType = ScaleType.CENTER_CROP
      }.lparams(
        width = matchParent,
        height = matchParent
      )

      textView {
        id = R.id.itemTitle
        maxLines = 1
        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        backgroundResource = R.drawable.bg_title_gradient
        padding = dip(8)
        textColor = Color.WHITE
        typeface = Typeface.DEFAULT_BOLD
        textSize = 16f // anko textView already interprets value in sp units
      }.lparams(
        gravity = Gravity.CENTER or Gravity.BOTTOM,
        width = matchParent
      )
    }
  }

  companion object {
    fun create(parent: ViewGroup): FrameLayout {
      val ankoContext = AnkoContext.Companion.create(parent.context, parent)
      return LeagueItemView().createView(ankoContext)
    }
  }
}