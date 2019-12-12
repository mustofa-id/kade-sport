/*
 * Mustofa on 12/5/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail.view

import android.content.Context
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.view.base.EntityView
import org.jetbrains.anko.*

class LoadingView(context: Context) : _LinearLayout(context), EntityView<LoadingView.Model> {

  data class Model(
    override val id: Long
  ) : Entity

  init {
    id = R.id.loadingView
    lparams(matchParent) { margin = dip(16) }
    orientation = VERTICAL
    progressBar().lparams(matchParent)
  }

  override fun bind(e: Model) {}
}