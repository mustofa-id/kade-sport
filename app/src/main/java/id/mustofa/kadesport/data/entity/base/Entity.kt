/*
 * Mustofa on 12/6/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.entity.base

interface Entity {
  val id: Long

  fun sameWith(other: Entity) = this == other
}