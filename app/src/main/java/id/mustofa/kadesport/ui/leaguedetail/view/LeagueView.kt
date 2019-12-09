package id.mustofa.kadesport.ui.leaguedetail.view

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.util.GlideRequests
import id.mustofa.kadesport.view.ClusterView
import id.mustofa.kadesport.view.base.EntityView
import id.mustofa.kadesport.view.clusterView
import org.jetbrains.anko.*

class LeagueView(context: Context) : _RelativeLayout(context), EntityView<League> {

  private val badge: ImageView
  private val title: TextView
  private val clusterDesc: ClusterView
  private lateinit var description: TextView
  private lateinit var glide: GlideRequests

  constructor(
    glide: GlideRequests,
    parent: ViewGroup
  ) : this(parent.context) {
    this.glide = glide
  }

  init {
    id = R.id.leagueView
    // header background
    view {
      backgroundResource = R.color.colorPrimary
    }.lparams(
      matchParent,
      matchParent
    ) {
      alignParentTop()
      sameBottom(R.id.itemBadge)
      bottomMargin = dip(95)
    }

    // badge
    badge = imageView {
      id = R.id.itemBadge
    }.lparams(
      height = dip(200),
      width = matchParent
    ) {
      centerHorizontally()
      topMargin = dip(16)
      bottomMargin = dip(8)
    }

    // title
    title = textView {
      id = R.id.itemTitle
      textSize = 24f
      typeface = Typeface.DEFAULT_BOLD
      textAlignment = TextView.TEXT_ALIGNMENT_CENTER
    }.lparams(matchParent) {
      below(R.id.itemBadge)
      horizontalMargin = dip(16)
      bottomPadding = dip(8)
    }

    // TODO: social & web links

    clusterDesc = clusterView {
      description = textView {
        maxLines = 4
        ellipsize = TextUtils.TruncateAt.END
        horizontalPadding = dip(16)
      }.lparams(matchParent)
    }.lparams(matchParent) {
      below(R.id.itemTitle)
    }
  }

  override fun bind(e: League) {
    glide.load(e.badgeUrl).into(badge)
    title.text = e.name
    description.text = e.description
    clusterDesc.setTitle("About this league")
    clusterDesc.setOnClickListener {
      context.alert(e.description, e.name) {
        yesButton { it.dismiss() }
        show()
      }
    }
  }
}