/*
 * Mustofa on 11/23/19
 * https://mustofa.id
 */
package id.mustofa.kadesport

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PROTECTED
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

@VisibleForTesting(otherwise = PROTECTED)
class SingleFragmentActivity : AppCompatActivity() {

  fun bind(fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
      replace(android.R.id.content, fragment)
      commit()
    }
  }
}