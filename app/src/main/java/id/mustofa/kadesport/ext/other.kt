/*
 * Mustofa on 11/8/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.ui.common.EntityListAdapter
import id.mustofa.kadesport.ui.leaguedetail.view.ClusterListView
import id.mustofa.kadesport.ui.leaguedetail.view.LoadingView
import id.mustofa.kadesport.ui.leaguedetail.view.MessageView
import java.util.*

fun Int?.str() = if (this == null) "-" else "$this"

fun String?.str() = this ?: "-"

fun String?.splitComma() = this?.split(';')?.dropLastWhile { it.isBlank() }

fun String?.splitLiner() = if (isNullOrBlank()) "-" else splitComma()?.joinToString("\n")

fun isBlank(vararg values: String?) = values.all { it.isNullOrBlank() }

fun currentTimeMillis() = Calendar.getInstance(Locale.getDefault()).timeInMillis

fun State<List<Entity>>.asClusterList(
  id: Long,
  title: String,
  adapter: EntityListAdapter,
  onClick: () -> Unit
) = when (this) {
  is State.Loading -> LoadingView.Model(id)
  is State.Empty -> MessageView.Model(id, R.string.msg_empty_result, R.drawable.ic_empty)
  is State.Error -> MessageView.Model(id, message, R.drawable.ic_error)
  is State.Success -> {
    // val id = data.firstOrNull()?.id ?: 1
    ClusterListView.Model(id, title, adapter) { onClick() }.also {
      adapter.submitList(data)
    }
  }
}