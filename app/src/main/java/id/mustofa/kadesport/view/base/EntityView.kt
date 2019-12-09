/*
 * Mustofa on 12/7/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.view.base

import id.mustofa.kadesport.data.entity.base.Entity

interface EntityView<T : Entity> {

  fun bind(e: T)
}

