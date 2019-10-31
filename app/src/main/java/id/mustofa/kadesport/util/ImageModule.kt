/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.util

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import id.mustofa.kadesport.R

@GlideModule
class ImageModule : AppGlideModule() {

  override fun applyOptions(context: Context, builder: GlideBuilder) {
    super.applyOptions(context, builder)
    val options = RequestOptions()
      .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
      .error(R.drawable.ic_error)
    builder.setDefaultRequestOptions(options)
  }
}