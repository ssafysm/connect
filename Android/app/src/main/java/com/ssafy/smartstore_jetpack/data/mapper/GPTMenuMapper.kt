package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.GPTMenuEntity
import com.ssafy.smartstore_jetpack.domain.model.GPTMenu

object GPTMenuMapper {

    operator fun invoke(gptMenuEntity: GPTMenuEntity): GPTMenu =
        GPTMenu(
            summary = gptMenuEntity.summary ?: "",
            success = gptMenuEntity.success ?: false
        )
}