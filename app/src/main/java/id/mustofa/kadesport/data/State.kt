/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data

sealed class State<out R> {
  class Success<out T>(val data: T) : State<T>()
  class Error(val message: Int) : State<Nothing>()
  object Loading : State<Nothing>()
  object Empty : State<Nothing>()
}