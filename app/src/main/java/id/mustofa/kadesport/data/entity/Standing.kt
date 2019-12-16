/*
 * Mustofa on 12/12/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.data.entity

import com.google.gson.annotations.SerializedName
import id.mustofa.kadesport.data.entity.base.Entity

data class Standing(

  /**
   * Team Id
   */
  @SerializedName("teamid")
  override val id: Long,

  @SerializedName("name")
  val name: String,

  @SerializedName("played")
  val played: Int,

  @SerializedName("goalsfor")
  val goalsFor: Int,

  @SerializedName("goalsagainst")
  val goalsAgainst: Int,

  @SerializedName("goalsdifference")
  val goalsDifference: Int,

  @SerializedName("win")
  val win: Int,

  @SerializedName("draw")
  val draw: Int,

  @SerializedName("loss")
  val loss: Int,

  @SerializedName("total")
  val total: Int
) : Entity {

  companion object {
    val EMPTY = Standing(0, "", 0, 0, 0, 0, 0, 0, 0, 0)
  }
}
