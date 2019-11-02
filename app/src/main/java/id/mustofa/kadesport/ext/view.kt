/*
 * Mustofa on 10/31/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import id.mustofa.kadesport.R
import org.jetbrains.anko.*

fun FrameLayout.applyClickEffect() {
  val typedValue = TypedValue()
  context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
  foreground = ContextCompat.getDrawable(context, typedValue.resourceId)
  isClickable = true
  isFocusable = true
}

fun <T> AnkoContext<T>.loadingView(): View = frameLayout {
  lparams(matchParent, matchParent) {
    padding = dip(16)
  }

  progressBar {
    isIndeterminate = true
  }.lparams(matchParent) {
    gravity = Gravity.CENTER
  }
}

fun <T> AnkoContext<T>.errorView(@StringRes message: Int): View {
  return verticalLayout {
    lparams(matchParent, matchParent)
    gravity = Gravity.CENTER
    padding = dip(16)

    imageView(R.drawable.ic_error)
      .lparams(matchParent, dip(92))

    textView(message) {
      textAlignment = TextView.TEXT_ALIGNMENT_CENTER
      textSize = 16f
    }.lparams(matchParent)
  }
}
