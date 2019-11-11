/*
 * Mustofa on 11/11/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventsearch

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.onQuerySubmit
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.ui.leagueeventdetail.LeagueEventDetailActivity
import id.mustofa.kadesport.ui.leagueevents.LeagueEventAdapter
import id.mustofa.kadesport.util.GlideApp
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.searchView
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.recyclerview.v7.recyclerView

class LeagueEventSearchActivity : AppCompatActivity() {

  private var progress: View? = null
  private var messageView: View? = null
  private var messageText: TextView? = null

  private val model by viewModel<LeagueEventSearchViewModel>()
  private val adapter by lazy {
    LeagueEventAdapter(GlideApp.with(this)) {
      startActivity<LeagueEventDetailActivity>(
        LeagueEventDetailActivity.EVENT_ID to it.id
      )
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initView()
    subscribeObservers()
  }

  private fun initView() {
    @IdRes val cardId = 1112
    relativeLayout {
      val activity = this@LeagueEventSearchActivity
      lparams(matchParent, matchParent)
      cardView {
        id = cardId
        searchView {
          setIconifiedByDefault(false)
          queryHint = getString(R.string.title_event_search)
          requestFocus()
          onQuerySubmit { it?.let { q -> model.search(q) } }
        }.lparams(matchParent)
      }.lparams(matchParent) {
        horizontalMargin = dip(16)
      }

      recyclerView {
        layoutManager = LinearLayoutManager(activity)
        adapter = activity.adapter
        clipToPadding = true
      }.lparams(matchParent, matchParent) {
        below(cardId)
        topPadding = dip(16)
      }

      progress = progressBar {
        isVisible = false
        isIndeterminate = true
      }.lparams {
        centerInParent()
      }

      messageView = verticalLayout {
        isVisible = false
        gravity = Gravity.CENTER
        padding = dip(16)
        imageView(R.drawable.ic_error)
          .lparams(matchParent, dip(92))
        messageText = textView {
          textAlignment = TextView.TEXT_ALIGNMENT_CENTER
          textSize = 16f
        }.lparams(matchParent)
      }.lparams(matchParent, matchParent) {
        centerInParent()
      }
    }
  }

  private fun subscribeObservers() {
    observe(model.events) { adapter.submitList(it) }
    observe(model.loading) { progress?.isVisible = it }
    observe(model.message) {
      messageView?.isVisible = (it != 0).also { hasMsg ->
        if (hasMsg) messageText?.setText(it)
      }
    }
  }
}