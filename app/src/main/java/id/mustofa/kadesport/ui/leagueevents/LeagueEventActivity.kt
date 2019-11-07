/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueevents

import android.os.Bundle
import android.view.Gravity
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.util.GlideApp
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.progressBar
import org.jetbrains.anko.recyclerview.v7.recyclerView

class LeagueEventActivity : AppCompatActivity() {

  private lateinit var progress: ProgressBar

  private val model by viewModel<LeagueEventViewModel>()
  private val adapter by lazy {
    LeagueEventAdapter(GlideApp.with(this)) {

    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    frameLayout {
      lparams(matchParent, matchParent)
      recyclerView {
        val activity = this@LeagueEventActivity
        layoutManager = LinearLayoutManager(activity)
        adapter = activity.adapter
      }
      progress = progressBar {
        isIndeterminate = true
      }.lparams(matchParent) {
        gravity = Gravity.CENTER
      }
    }
    subscribeObservers()
    loadEvents()
  }

  private fun subscribeObservers() {
    observe(model.events) { adapter.submitList(it) }
    observe(model.loading) { progress.isVisible = it }
    observe(model.message) {}
  }

  private fun loadEvents() {
    intent.extras?.let {
      val id = it.getLong(LEAGUE_ID)
      val type = it.getSerializable(EVENT_TYPE) as LeagueEventType
      model.loadEvents(id, type)
    }
  }

  companion object {
    const val LEAGUE_ID = "_leagueId_"
    const val EVENT_TYPE = "_eventType_"
  }
}
