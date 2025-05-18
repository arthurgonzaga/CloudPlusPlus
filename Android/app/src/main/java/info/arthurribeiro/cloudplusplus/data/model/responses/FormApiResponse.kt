package info.arthurribeiro.cloudplusplus.data.model.responses

import info.arthurribeiro.cloudplusplus.data.model.responses.typeadapter.FieldSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = FieldSerializer::class)
sealed class Field {
    @SerialName("uuid")
    abstract val id: String
    abstract val label: String
    open val required: Boolean = false

    @Serializable
    data class TextField(
        @SerialName("uuid")
        override val id: String,
        override val label: String,
        override val required: Boolean = false,
    ) : Field()


    @Serializable
    data class Description(
        @SerialName("uuid")
        override val id: String,
        override val label: String,
        override val required: Boolean = false,
    ) : Field()


    @Serializable
    data class Number(
        @SerialName("uuid")
        override val id: String,
        override val label: String,
        override val required: Boolean = false,
    ) : Field()

    @Serializable
    data class Dropdown(
        @SerialName("uuid")
        override val id: String,
        override val label: String,
        override val required: Boolean = false,
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
