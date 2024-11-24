package com.ssafy.smartstore_jetpack.presentation.util

enum class InputValidState {
    NONE, VALID
}

enum class DuplicateState {
    DUPLICATE, NONE
}

enum class EmptyState {
    EMPTY, NONE
}

enum class SelectState {
    SELECT, NONE
}

enum class IdState {
    VALID, INIT, NONE
}

enum class PasswordState {
    INIT, VALID, NONE
}

enum class ShopSelectValidState {
    MAP, SEARCH, MAPSELECT, SEARCHSELECT
}