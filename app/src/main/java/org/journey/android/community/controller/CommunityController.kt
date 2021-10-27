package org.journey.android.community.controller

import io.reactivex.rxjava3.core.Single
import org.journey.android.community.data.dto.ResponseCommunityFeedDTO

interface CommunityController {
    fun getCommunityFeed() : Single<ResponseCommunityFeedDTO>
}