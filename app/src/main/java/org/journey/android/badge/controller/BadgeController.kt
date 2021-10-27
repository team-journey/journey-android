package org.journey.android.badge.controller

import io.reactivex.rxjava3.core.Single
import org.journey.android.badge.data.dto.ResponseAchieveBadgeDTO

interface BadgeController {
    fun putBadgeList() : Single<ResponseAchieveBadgeDTO>
}