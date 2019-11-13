/*
 * Mustofa on 11/13/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventfavorite

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ui.leagueevents.LeagueEventAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class LeagueEventFavoriteView(
  private val eventAdapter: LeagueEventAdapter
) : AnkoComponent<LeagueEventFavoriteFragment> {

  private lateinit var progress: View
  private lateinit var messageView: View
  private lateinit var messageText: TextView

  override fun createView(ui: AnkoContext<LeagueEventFavoriteFragment>): View {
    return with(ui) {
      frameLayout {
        lparams(matchParent, matchParent)
        recyclerView {
          layoutManager = LinearLayoutManager(ctx)
          adapter = eventAdapter
          setHasFixedSize(true)
        }

        progress = progressBar {
          isIndeterminate = true
        }.lparams(matchParent) {
          gravity = Gravity.CENTER
        }

        messageView = verticalLayout {
          isVisible = false
          lparams(matchParent, matchParent)
          gravity = Gravity.CENTER
          padding = dip(16)
          imageView(R.drawable.ic_error)
            .lparams(matchParent, dip(92))
          messageText = textView {
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            textSize = 16f
          }.lparams(matchParent)
        }
      }
    }
  }

  fun isLoading(constant: Boolean) {
    progress.isVisible = constant
  }

  fun setMessage(value: Int) {
    messageView.isVisible = (value != 0).also { hasMsg ->
      if (hasMsg) messageText.setText(value)
    }
  }
}