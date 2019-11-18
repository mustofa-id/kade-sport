/*
 * Mustofa on 11/18/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.common

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.TextView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.State.*
import org.jetbrains.anko.*

abstract class StateView<T>(private val state: State<T>) : AnkoComponent<Activity> {

  final override fun createView(ui: AnkoContext<Activity>) = with(ui) {
    when (state) {
      is Loading -> frameLayout {
        id = R.id.loadingProgress
        lparams(matchParent, matchParent) { padding = dip(16) }
        progressBar {
          isIndeterminate = true
        }.lparams(matchParent) {
          gravity = Gravity.CENTER
        }
      }

      is Error -> verticalLayout {
        id = R.id.messageContainer
        lparams(matchParent, matchParent)
        gravity = Gravity.CENTER
        padding = dip(16)
        imageView(R.drawable.ic_error) {
          id = R.id.messageImage
        }.lparams(matchParent, dip(92))
        textView(state.message) {
          id = R.id.messageText
          textAlignment = TextView.TEXT_ALIGNMENT_CENTER
          textSize = 16f
        }.lparams(matchParent)
      }

      is Success -> successView(ui, state.data)
    }
  }

  protected abstract fun successView(ui: AnkoContext<Activity>, data: T): View
}