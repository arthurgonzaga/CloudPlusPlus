package info.arthurribeiro.cloudplusplus.data.model.responses

import info.arthurribeiro.cloudplusplus.data.model.responses.typeadapter.FieldSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FormResponse(
    val title: String,
    val fields: List<Field>,
    val sections: List<Section>
)

@Serializable(with = FieldSerializer::class)
sealed class Field {

    @Serializable
    data class TextField(
        @SerialName("uuid")
        val id: String,
        val label: String,
        val required: Boolean,
    ) : Field()


    @Serializable
    data class Description(
        @SerialName("uuid")
        val id: String,
        @SerialName("label")
        val htmlDescription: String,
        val required: Boolean,
    ) : Field()


    @Serializable
    data class Number(
        @SerialName("uuid")
        val id: String,
        val label: String,
        val required: Boolean,
    ) : Field()

    @Serializable
    data class Dropdown(
        @SerialName("uuid")
        val id: String,
        val label: String,
        val required: Boolean,
        val options: List<Option>
    ) : Field() {

        @Serializable
        data class Option(
            val label: String,
            val value: String
        )
    }

}

@Serializable
data class Section(
    @SerialName("uuid")
    val id: String,
    val title: String,
    val from: Int,
    val to: Int,
    val index: Int,
)
