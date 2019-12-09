/*
 * Mustofa on 11/8/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ext

import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.ui.event.EventAdapter
import id.mustofa.kadesport.ui.leaguedetail.view.ClusterListView
import id.mustofa.kadesport.ui.leaguedetail.view.LoadingView
import id.mustofa.kadesport.ui.leaguedetail.view.MessageView
import id.mustofa.kadesport.util.GlideRequests

fun Int?.str() = if (this == null) "-" else "$this"

fun String?.str() = this ?: "-"

fun String?.splitComma() = this?.split(';')?.dropLastWhile { it.isBlank() }

fun String?.splitLiner() = if (isNullOrBlank()) "-" else splitComma()?.joinToString("\n")

fun isBlank(vararg values: String?) = values.all { it.isNullOrBlank() }

fun State<List<Event>>.asClusterEvents(
  title: String,
  glide: GlideRequests,
  onClick: () -> Unit
) = when (this) {
  is State.Loading -> LoadingView.Model
  is State.Empty -> MessageView.Model(0, R.string.msg_empty_result, R.drawable.ic_empty)
  is State.Error -> MessageView.Model(-1, message, R.drawable.ic_error)
  is State.Success -> {
    val id = data.firstOrNull()?.id ?: 1
    val adapter = EventAdapter(glide)
    adapter.submitList(data)
    ClusterListView.Model(id, title, adapter) { onClick() }
  }
}