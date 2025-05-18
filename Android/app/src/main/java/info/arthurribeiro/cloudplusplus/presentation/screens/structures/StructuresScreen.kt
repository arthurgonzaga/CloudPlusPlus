package info.arthurribeiro.cloudplusplus.presentation.screens.structures

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.arthurribeiro.cloudplusplus.R
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import info.arthurribeiro.cloudplusplus.presentation.screens.FormsDestination
import info.arthurribeiro.cloudplusplus.presentation.theme.DarkGreen
import info.arthurribeiro.cloudplusplus.presentation.theme.DarkRed
import info.arthurribeiro.cloudplusplus.presentation.theme.DarkYellow
import info.arthurribeiro.cloudplusplus.presentation.theme.LightGray
import info.arthurribeiro.cloudplusplus.presentation.theme.LightGreen
import info.arthurribeiro.cloudplusplus.presentation.theme.LightRed
import info.arthurribeiro.cloudplusplus.presentation.theme.LightYellow
import org.koin.androidx.compose.koinViewModel

@Composable
fun StructuresScreen(
    navigate: (FormsDestination) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StructuresViewModel = koinViewModel()
) {
    val uiState by viewModel.state

    LaunchedEffect(Unit) {
        viewModel.getStructures()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {

        Spacer(Modifier.height(62.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .height(32.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
            )

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .background(
                        color = LightGray,
                        shape = CircleShape
                    )
            )
        }

        Spacer(Modifier.height(57.dp))

        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = "Category",
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) {
            items(10) {
                Box(
                    modifier = Modifier
                        .size(100.dp, 40.dp)
                        .padding(start = 8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = LightGray)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = "Types",
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(8.dp))

        if (uiState.isLoading) {
            Box(
                Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp),
                    color = DarkGreen,
                    strokeWidth = 2.dp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 32.dp),
            ) {
                items(uiState.list) { structure ->
                    Card(
                        structure = structure,
                        onClick = {
                            navigate(FormsDestination(structure = structure))
                        }
                    )
                }
            }
        }

    }
}

@Composable
private fun Card(
    structure: FormStructure,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onClick()
            }
            .background(color = structure.getBackgroundColor())
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = structure.name,
                    fontWeight = FontWeight.Bold,
                    color = structure.getTextColor()
                )
                Text(
                    modifier = Modifier.weight(0.4f),
                    text = "${structure.totalFields} fields",
                    textAlign = TextAlign.End,
                    fontSize = 14.sp,
                    color = structure.getTextColor()
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = structure.id,
                fontSize = 12.sp,
                color = structure.getTextColor()
            )
        }
    }
}
