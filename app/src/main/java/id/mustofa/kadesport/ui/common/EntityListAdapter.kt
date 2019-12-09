/*
 * Mustofa on 12/7/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.common


import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.ui.common.EntityListAdapter.EntityViewHolder
import id.mustofa.kadesport.view.base.EntityView

abstract class EntityListAdapter : ListAdapter<Entity, EntityViewHolder<View>>(EntityDiffCallback) {

  final override fun onBindViewHolder(holder: EntityViewHolder<View>, position: Int) {
    bindItem(holder, getItem(position))
  }

  class EntityViewHolder<out T : View>(val view: T) : RecyclerView.ViewHolder(view)

  private object EntityDiffCallback : DiffUtil.ItemCallback<Entity>() {
    override fun areItemsTheSame(old: Entity, new: Entity) = old.id == new.id
    override fun areContentsTheSame(old: Entity, new: Entity) = old.sameWith(new)
  }

  private inline fun <V, reified X : EntityView<V>> bindItem(holder: EntityViewHolder<*>, item: V) {
    if (holder.view is X) holder.view.bind(item)
  }
}