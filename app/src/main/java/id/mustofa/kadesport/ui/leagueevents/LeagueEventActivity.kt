/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueevents

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.ui.leagueeventdetail.LeagueEventDetailActivity
import id.mustofa.kadesport.util.GlideApp
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class LeagueEventActivity : AppCompatActivity() {

  private lateinit var progress: ProgressBar
  private lateinit var errorView: View
  private lateinit var errorText: TextView

  private val model by viewModel<LeagueEventViewModel>()
  private val adapter by lazy {
    LeagueEventAdapter(GlideApp.with(this)) {
      startActivity<LeagueEventDetailActivity>(
        LeagueEventDetailActivity.EVENT_ID to it.id
      )
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

      errorView = verticalLayout {
        lparams(matchParent, matchParent)
        gravity = Gravity.CENTER
        padding = dip(16)
        imageView(R.drawable.ic_error)
          .lparams(matchParent, dip(92))
        errorText = textView {
          textAlignment = TextView.TEXT_ALIGNMENT_CENTER
          textSize = 16f
        }.lparams(matchParent)
      }
    }

    subscribeObservers()
    loadEvents()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) onBackPressed()
    return super.onOptionsItemSelected(item)
  }

  private fun subscribeObservers() {
    observe(model.events) { adapter.submitList(it) }
    observe(model.loading) { progress.isVisible = it }
    observe(model.message) {
      errorView.isVisible = (it != 0).also { has ->
        if (has) errorText.setText(it)
      }
    }
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
