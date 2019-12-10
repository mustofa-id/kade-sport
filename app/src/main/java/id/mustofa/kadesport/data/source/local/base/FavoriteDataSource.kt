/*
 * Mustofa on 12/9/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source.local.base

import id.mustofa.kadesport.data.entity.base.Entity

interface FavoriteDataSource<T : Entity> {

  suspend fun getFavorites(): List<T>

  suspend fun getFavorite(id: Long): T?

  suspend fun saveFavorite(value: T): Long

  suspend fun deleteFavorite(id: Long): Int
}