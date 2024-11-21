package com.ssafy.smartstore_jetpack.presentation.views.main.my

data class MyPageUiState(
    val gradeState: GradeState = GradeState.NONE
) {
    val isCoffeeTree: Boolean = (gradeState == GradeState.TREE)
}

enum class GradeState {
    NONE, TREE
}