package org.journey.android.main.dto


import com.google.gson.annotations.SerializedName

data class ResponseHomeDTO(
    @SerializedName("data")
    val challengeProgressDTO: ChallengeProgressDTO,
    @SerializedName("status")
    val status: Int
){
    data class ChallengeProgressDTO(
        @SerializedName("characterLottie")
        val characterLottie: String,
        @SerializedName("characterSkin")
        val characterSkin: String,
        @SerializedName("course")
        val courseProgressDTO: CourseProgressDTO?,
        @SerializedName("fullHappy")
        val fullHappy: Int,
        @SerializedName("happy")
        val happy: Int,
        @SerializedName("isBadgeNew")
        val isBadgeNew: Boolean,
        @SerializedName("isStyleNew")
        val isStyleNew: Boolean,
        @SerializedName("level")
        val level: Int,
        @SerializedName("nicknema")
        val nicknema: String
    )
    {
        data class CourseProgressDTO(
            @SerializedName("challengeTitle")
            val challengeTitle: String,
            @SerializedName("percent")
            val percent: Int
        )
    }
}