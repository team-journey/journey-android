package org.journey.android.course.data.dto


import com.google.gson.annotations.SerializedName
import org.journey.android.course.data.entity.CourseCatalogEntity

data class CatalogCourseDTO(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("property")
    val property: Int,
    @SerializedName("situation")
    val situation: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("totalDays")
    val totalDays: Int
){
    fun convertToCourseCatalogEntity() : CourseCatalogEntity {
        return CourseCatalogEntity(
            property,
            title,
            totalDays,
            description
        )
    }
}