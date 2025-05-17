package info.arthurribeiro.cloudplusplus.presentation.screens

import info.arthurribeiro.cloudplusplus.data.model.entity.Form
import info.arthurribeiro.cloudplusplus.data.model.entity.FormStructure
import kotlinx.serialization.Serializable

@Serializable
object StructuresDestination

@Serializable
data class FormsDestination(val structure: FormStructure)

@Serializable
data class FormDetailDestination(val form: Form)