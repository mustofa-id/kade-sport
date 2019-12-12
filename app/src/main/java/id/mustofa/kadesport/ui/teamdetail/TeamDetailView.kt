/*
 * Mustofa on 12/11/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.teamdetail

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.util.GlideRequests
import id.mustofa.kadesport.view.base.EntityView
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.imageView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textView

// TODO: Design this view
class TeamDetailView(context: Context) : _LinearLayout(context), EntityView<Team> {

  private lateinit var glide: GlideRequests
  private val badge: ImageView
  private val name: TextView

  constructor(
    glide: GlideRequests,
    parent: ViewGroup
  ) : this(parent.context) {
    this.glide = glide
  }

  init {
    id = R.id.teamDetailView
    lparams(matchParent)
    orientation = VERTICAL
    badge = imageView()
    name = textView()
  }

  override fun bind(e: Team) {
    name.text = e.name
    if (::glide.isInitialized) {
      glide.load(e.badgePath).into(badge)
    }
  }
}