/*
 * Mustofa on 12/9/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.view

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.util.GlideRequests
import id.mustofa.kadesport.view.base.EntityView
import org.jetbrains.anko.*

class TeamSimpleView(context: Context) : _LinearLayout(context), EntityView<Team> {

  private lateinit var glide: GlideRequests

  private val badge: ImageView
  private val title: TextView

  init {
    id = R.id.teamSimpleView
    background = context.selectableItemBackground()
    orientation = VERTICAL
    isClickable = true
    isFocusable = true
    badge = imageView {
      adjustViewBounds = true
    }.lparams(dip(126), dip(126)) {
      margin = dip(8)
      bottomPadding = dip(8)
    }
    title = textView {
      textAlignment = TextView.TEXT_ALIGNMENT_CENTER
    }
  }

  fun withGlide(glide: GlideRequests): TeamSimpleView {
    this.glide = glide
    return this
  }

  override fun bind(e: Team) {
    title.text = e.name
    if (::glide.isInitialized) {
      glide.load(e.badgePath).into(badge)
    }
  }
}