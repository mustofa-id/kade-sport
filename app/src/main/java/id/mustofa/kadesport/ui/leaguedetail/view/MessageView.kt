/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail.view

import android.content.Context
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.view.base.EntityView
import org.jetbrains.anko.*

class MessageView(context: Context) : _LinearLayout(context), EntityView<MessageView.Model> {

  data class Model(
    override val id: Long,
    val text: Int,
    val image: Int
  ) : Entity

  private val messageImage: ImageView
  private val messageText: TextView

  init {
    id = R.id.messageView
    lparams(matchParent)
    orientation = VERTICAL
    messageImage = imageView()
      .lparams(matchParent, dip(92))
    messageText = textView {
      textSize = 18f
      typeface = Typeface.DEFAULT_BOLD
      textAlignment = TextView.TEXT_ALIGNMENT_CENTER
    }.lparams(matchParent)
  }

  override fun bind(e: Model) {
    messageText.setText(e.text)
    messageImage.setImageResource(e.image)
  }
}