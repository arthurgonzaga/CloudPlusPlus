package info.arthurribeiro.cloudplusplus.presentation

import info.arthurribeiro.cloudplusplus.presentation.screens.detail.FormDetailViewModel
import info.arthurribeiro.cloudplusplus.presentation.screens.forms.FormsViewModel
import info.arthurribeiro.cloudplusplus.presentation.screens.structures.StructuresViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { StructuresViewModel(repository = get()) }
    viewModel { parameters -> FormsViewModel(repository = get(), structure = parameters.get()) }
    viewModel { parameters ->
        FormDetailViewModel(
            repository = get(),
            json = get(),
            formId = parameters.get(),
            structure = parameters.get()
        )
    }
}