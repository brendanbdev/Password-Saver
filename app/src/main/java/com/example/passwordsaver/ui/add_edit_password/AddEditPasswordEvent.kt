package com.example.passwordsaver.ui.add_edit_password

sealed class AddEditPasswordEvent {
    data class OnTitleChange(val title: String): AddEditPasswordEvent()
    data class OnPasswordValueChange(val passwordValue: String): AddEditPasswordEvent()
    object OnSavePasswordClick: AddEditPasswordEvent()
}