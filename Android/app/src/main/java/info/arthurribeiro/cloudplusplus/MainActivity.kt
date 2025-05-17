package info.arthurribeiro.cloudplusplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.arthurribeiro.cloudplusplus.presentation.screens.FormDetailDestination
import info.arthurribeiro.cloudplusplus.presentation.screens.FormsDestination
import info.arthurribeiro.cloudplusplus.presentation.screens.StructuresDestination
import info.arthurribeiro.cloudplusplus.presentation.screens.structures.StructuresScreen
import info.arthurribeiro.cloudplusplus.presentation.theme.CloudPlusPlusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { Content() }
    }

    @Composable
    fun Content(modifier: Modifier = Modifier) {
        CloudPlusPlusTheme {
            val navController = rememberNavController()

            NavHost(
                modifier = modifier,
                navController = navController,
                startDestination = StructuresDestination
            ) {
                composable<StructuresDestination> {
                    StructuresScreen()
                }
//
//                composable<FormsDestination> {
//
//                }
//
//                composable<FormDetailDestination> {
//
//                }
            }
        }
    }
}
