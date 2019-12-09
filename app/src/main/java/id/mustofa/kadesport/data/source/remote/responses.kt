/*
 * Mustofa on 11/2/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.source.remote

import android.util.Log
import com.google.gson.annotations.SerializedName
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.data.source.remote.ResponseResult.Failure
import id.mustofa.kadesport.data.source.remote.ResponseResult.Success
import retrofit2.Response

data class LeagueResponse(
  val leagues: List<League>?
)

data class LeagueEventResponse(
  // Search will using alternate value although the result is json array.
  @SerializedName(value = "events", alternate = ["event"])
  val events: List<Event>?
)

data class TeamResponse(
  val teams: List<Team>?
)

sealed class ResponseResult<out R> {
  class Success<out T>(val data: T) : ResponseResult<T>()
  class Failure(val message: Int) : ResponseResult<Nothing>()
}

inline fun <reified T> handleResponse(response: () -> Response<T>): ResponseResult<T> {
  return try {
    val res = response()
    if (res.isSuccessful) {
      val result = res.body() ?: return Failure(R.string.msg_response_empty)
      Success(result)
    } else {
      Failure(R.string.msg_response_failure)
    }
  } catch (e: Exception) {
    Log.e(T::class.qualifiedName, "handleResponse: ", e)
    Failure(R.string.msg_response_error)
  }
}

inline fun <T, R> successOf(
  response: ResponseResult<T>,
  success: T.() -> State.Success<R>
) = when (response) {
  is Success -> success(response.data)
  is Failure -> State.Error(response.message)
}