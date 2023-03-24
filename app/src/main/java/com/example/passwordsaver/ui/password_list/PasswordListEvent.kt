package com.example.passwordsaver.ui.password_list

import com.example.passwordsaver.data.Password

//From the ui, to the view model
sealed class PasswordListEvent {
    data class OnPasswordCopiedClick(val password: Password): PasswordListEvent()
    data class OnPasswordVisibleClick(val password: Password, val isVisible: Boolean): PasswordListEvent()
    data class OnPasswordEditClick(val password: Password): PasswordListEvent()
    data class OnPasswordDeleteClick(val password: Password): PasswordListEvent()
    object OnAddPasswordClick: PasswordListEvent()
    object OnUndoDeleteClick: PasswordListEvent()
}
