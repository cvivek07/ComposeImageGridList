package com.vivekchoudhary.imageloadingapp.repository.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.versionedparcelable.VersionedParcelize
import com.google.gson.annotations.SerializedName


/**
{
"id": "4Bo8mxKpPqw",
"urls": {
"raw": "https://images.unsplash.com/photo-1695576514502-cbc0a333f2d9?ixid=M3w1ODk4MDB8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxMzE3ODEwNHw&ixlib=rb-4.0.3",
"full": "https://images.unsplash.com/photo-1695576514502-cbc0a333f2d9?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1ODk4MDB8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxMzE3ODEwNHw&ixlib=rb-4.0.3&q=85",
"regular": "https://images.unsplash.com/photo-1695576514502-cbc0a333f2d9?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1ODk4MDB8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxMzE3ODEwNHw&ixlib=rb-4.0.3&q=80&w=1080",
"small": "https://images.unsplash.com/photo-1695576514502-cbc0a333f2d9?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1ODk4MDB8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxMzE3ODEwNHw&ixlib=rb-4.0.3&q=80&w=400",
"thumb": "https://images.unsplash.com/photo-1695576514502-cbc0a333f2d9?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1ODk4MDB8MHwxfGFsbHwxfHx8fHx8Mnx8MTcxMzE3ODEwNHw&ixlib=rb-4.0.3&q=80&w=200",
"small_s3": "https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1695576514502-cbc0a333f2d9"
},
}
 */

@Stable
@VersionedParcelize
data class Photo(
    @SerializedName("id")
    val id: String,
    @SerializedName("urls")
    val urls: Urls
)

@Stable
@VersionedParcelize
data class Urls(
    @SerializedName("regular")
    val regularUrl: String
)