package co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")
data class PostResponse(
        @SerializedName("userId")
        @Expose
        val userId: Int,
        @PrimaryKey
        @SerializedName("id")
        @Expose
        val id: Int,
        @SerializedName("title")
        @Expose
        val title: String,
        @SerializedName("body")
        @Expose
        val body: String
)