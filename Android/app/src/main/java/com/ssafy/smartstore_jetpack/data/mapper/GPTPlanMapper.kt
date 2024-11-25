package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.GPTPlanEntity
import com.ssafy.smartstore_jetpack.domain.model.GPTMenu

object GPTPlanMapper {

    operator fun invoke(gptPlanEntity: GPTPlanEntity): GPTMenu =
        GPTMenu(
            summary = gptPlanEntity.answer ?: "",
            success = gptPlanEntity.success ?: false
        )
}