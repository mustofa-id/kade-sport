/*
 * Mustofa on 12/5/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.view

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.view.base.EntityView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7._CardView

class EventView(context: Context) : _CardView(context), EntityView<Event> {

  private lateinit var home: TextView
  private lateinit var away: TextView

  init {
    // TODO: Add more properties
    id = R.id.eventView
    applyClickEffect()
    lparams(dip(256), dip(172))
    useCompatPadding = true
    relativeLayout {
      padding = dip(16)
      textView("VS") {
        id = R.id.eventVs
        textSize = 24f
        setTypeface(typeface, Typeface.BOLD_ITALIC)
      }.lparams {
        centerInParent()
      }
      home = textView().lparams {
        alignParentStart()
        alignParentTop()
        startOf(R.id.eventVs)
      }
      away = textView {
        textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
      }.lparams {
        alignParentEnd()
        alignParentBottom()
        endOf(R.id.eventVs)
      }
    }
  }

  override fun bind(e: Event) {
    home.text = e.homeName
    away.text = e.awayName
  }
}