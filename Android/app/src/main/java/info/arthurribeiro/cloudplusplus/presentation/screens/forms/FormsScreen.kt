package info.arthurribeiro.cloudplusplus.presentation.screens.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.arthurribeiro.cloudplusplus.presentation.theme.Blue
import info.arthurribeiro.cloudplusplus.presentation.theme.DarkBlue
import info.arthurribeiro.cloudplusplus.presentation.theme.LightGray

@Composable
fun FormsScreen(
    popBackStack: () -> Unit,
    navigate: (formId: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FormsViewModel
) {

    val uiState by viewModel.uiState

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.createNewForm()
                },
                containerColor = Blue,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Form")
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier.padding(paddingValues)
        ) {
            item {
                Text(
                    modifier = Modifier
                        .padding(top = 56.dp, bottom = 22.dp)
                        .padding(horizontal = 32.dp),
                    text = "Forms",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
            }

            items(
                items = uiState.forms,
                key = { it.id }
            ) { form ->
                FormItem(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(bottom = 14.dp),
                    id = form.id,
                    backgroundColor = uiState.backgroundColor,
                    textColor = uiState.textColor,
                    onClick = {
                        navigate(form.id)
                    }
                )
            }
        }
    }
}


@Composable
private fun FormItem(
    modifier: Modifier = Modifier,
    id: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .background(backgroundColor, shape = RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .clip(RoundedCornerShape(20.dp))
            .padding(horizontal = 20.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = 100.dp),
            text = id,
            fontSize = 14.sp,
            color = textColor
        )

        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = textColor
        )
    }
}