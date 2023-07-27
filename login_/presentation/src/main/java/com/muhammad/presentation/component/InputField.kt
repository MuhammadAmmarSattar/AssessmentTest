package com.muhammad.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammad.presentation.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputField(
    text: String,
    modifier: Modifier = Modifier,
    error: Boolean? = true,
    valueState: (String) -> Unit,
    labelId: String,
    enable: Boolean,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
) {
    val (focusRequester) = FocusRequester.createRefs()

    TextField(
        value = text,
        onValueChange = valueState,
        label = { Text(text = labelId) },
        isError = error ?: true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
        ),
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        enabled = enable,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onNext = { focusRequester.requestFocus() }
        )

    )
}

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = { onClick() },
        shape = CircleShape,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = com.muhammad.core_ui.R.color.light_blue), // Set transparent background for ripple effect
            contentColor = colorResource(id = com.muhammad.core_ui.R.color.white) // Set your custom text color
        )
    ) {
        Text(text = text, modifier = Modifier.padding(top = 18.dp, bottom = 18.dp))
    }
}