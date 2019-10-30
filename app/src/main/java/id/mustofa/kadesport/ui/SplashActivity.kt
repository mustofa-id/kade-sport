/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    startActivity<MainActivity>()
    finish()
  }
}