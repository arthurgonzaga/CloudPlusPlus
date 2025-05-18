package info.arthurribeiro.cloudplusplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import info.arthurribeiro.cloudplusplus.presentation.screens.FormDetailDestination
import info.arthurribeiro.cloudplusplus.presentation.screens.FormsDestination
import info.arthurribeiro.cloudplusplus.presentation.screens.JsonNavType
import info.arthurribeiro.cloudplusplus.presentation.screens.StructuresDestination
import info.arthurribeiro.cloudplusplus.presentation.screens.detail.FormDetailScreen
import info.arthurribeiro.cloudplusplus.presentation.screens.forms.FormsScreen
import info.arthurribeiro.cloudplusplus.presentation.screens.structures.StructuresScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { Content() }
    }

    @Composable
    fun Content(modifier: Modifier = Modifier) {
        val navController = rememberNavController()

        NavHost(
            modifier = modifier.background(Color.White),
            navController = navController,
            startDestination = StructuresDestination
        ) {
            composable<StructuresDestination> {
                StructuresScreen(
                    navigate = { formStructure ->
                        navController.navigate(formStructure)
                    }
                )
            }

            composable<FormsDestination>(
                typeMap = mapOf(
                    typeOf<FormStructure>() to JsonNavType(FormStructure.serializer())
                )
            ) {
                val args = it.toRoute<FormsDestination>()

                FormsScreen(
                    viewModel = koinViewModel { parametersOf(args.structure) },
                    popBackStack = {
                        navController.popBackStack()
                    },
                    navigate = { formId ->
                        navController.navigate(FormDetailDestination(formId, args.structure))
                    }
                )
            }

            composable<FormDetailDestination>(
                typeMap = mapOf(
                    typeOf<FormStructure>() to JsonNavType(FormStructure.serializer())
                )
            ) {
                val args = it.toRoute<FormDetailDestination>()

                FormDetailScreen(
                    viewModel = koinViewModel { parametersOf(args.formId, args.structure) },
                    onClose = {
                        navController.popBackStack()
                    }
                )
            }
        }

    }
}
