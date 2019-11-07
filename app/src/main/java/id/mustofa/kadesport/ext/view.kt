/*
 * Mustofa on 10/31/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import id.mustofa.kadesport.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

fun <T> AnkoContext<T>.integer(@IntegerRes value: Int) = ctx.resources.getInteger(value)

fun selectableItemBackground(context: Context): Drawable? {
  val typedValue = TypedValue()
  context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
  return ContextCompat.getDrawable(context, typedValue.resourceId)
}

fun FrameLayout.applyClickEffect() {
  foreground = selectableItemBackground(context)
  isClickable = true
  isFocusable = true
}

fun <T> AnkoContext<T>.loadingView(): View = frameLayout {
  lparams(matchParent, matchParent) { padding = dip(16) }
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

inline fun ViewManager.clusterView(
  title: String,
  noinline onClick: (View) -> Unit,
  children: LinearLayout.() -> Unit
): ViewGroup {
  @IdRes val headId = 124
  return ankoView({ _LinearLayout(it) }, theme = 0) {
    lparams(matchParent) {
      // verticalPadding = dip(8)
      orientation = LinearLayout.VERTICAL
      bottomPadding = dip(16)
    }
    frameLayout {
      id = headId
      lparams(matchParent) {
        horizontalPadding = dip(16)
      }
      setOnClickListener(onClick)
      applyClickEffect()
      linearLayout {
        lparams(matchParent) {
          // horizontalMargin = dip(24)
          orientation = LinearLayout.HORIZONTAL
          gravity = Gravity.CENTER
        }
        // head title
        // themedTextView(title, R.style.TextAppearance_AppCompat_Title) { //broken
        textView(title) {
          textSize = 18f
          typeface = Typeface.DEFAULT_BOLD
        }.lparams(width = 0, weight = 1F) {
          verticalPadding = dip(8)
        }
        // head arrow
        imageView(R.drawable.ic_arrow_forward) {
        }.lparams {
          marginStart = dip(8)
        }
      }
    }
    children()
  }
}
