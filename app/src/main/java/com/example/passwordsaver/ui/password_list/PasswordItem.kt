package com.example.passwordsaver.ui.password_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import com.example.passwordsaver.R
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordsaver.data.Password
import com.example.passwordsaver.ui.theme.Amber
import com.example.passwordsaver.ui.theme.Green
import com.example.passwordsaver.ui.theme.Red

@Composable
fun PasswordItem(
    password: Password,
    /*
    The argument for this onEvent parameter will be PasswordListViewModel's
    onEvent function (when this PasswordItem composable is implemented in
    PasswordListScreen). In this composable, with this onEvent parameter,
    we choose which PasswordListEvent function we want to use, and
    PasswordListViewModel's onEvent function will handle that.
    */
    onEvent: (PasswordListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = password.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text =
            if (password.isVisible) {
                password.passwordValue
            } else {
                "********"
            })
        }
        IconButton(onClick = {
            onEvent(PasswordListEvent.OnPasswordCopiedClick(password))
        }) {
            Icon(
                painter = painterResource(R.drawable.baseline_content_copy_24),
                contentDescription = "This is the button to copy the associated password to your clipboard."
            )
        }
        IconToggleButton(
            checked = password.isVisible,
            onCheckedChange = { isToggled ->
                onEvent(PasswordListEvent.OnPasswordVisibleClick(password, isToggled))
            }
        ) {
            Icon(
                painter =
                if(password.isVisible) {
                    painterResource(R.drawable.outline_visibility_24)
                } else {
                    painterResource(R.drawable.baseline_visibility_24)
                },
                tint = Green,
                contentDescription = "This is the button that makes the associated password visible."
            )
        }
        IconButton(onClick = {
            onEvent(PasswordListEvent.OnPasswordEditClick(password))
        }) {
            Icon(
                imageVector = Icons.Default.Edit,
                tint = Amber,
                contentDescription = "This is the button to edit the associated password."
            )
        }
        IconButton(onClick = {
            onEvent(PasswordListEvent.OnPasswordDeleteClick(password))
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                tint = Red,
                contentDescription = "This is the button to delete the associated password."
            )
        }
    }
}
