/*
 * Mustofa on 10/31/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import android.util.TypedValue
import android.widget.FrameLayout
import androidx.core.content.ContextCompat

fun FrameLayout.applyClickEffect() {
  val typedValue = TypedValue()
  context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)
  foreground = ContextCompat.getDrawable(context, typedValue.resourceId)
  isClickable = true
  isFocusable = true
}