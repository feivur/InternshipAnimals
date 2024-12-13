package com.example.project2.utils.animalsStub

import android.os.Parcel
import android.os.Parcelable
import com.example.project2.screens.animals.selection.AnimalType
import com.example.project2.structure.animals.Animal

data class AnimalImpl(
    override val id: Long,
    override val name: String,
    override val color: String,
    override val type: AnimalType
) : Animal, Parcelable {

    override fun describe(): String {
        return "AnimalImpl(id=$id, name=$name, color=$color, type=$type)"
    }

    override fun speak(): String {
        return "This is a $type named $name"
    }

    constructor(parcel: Parcel) : this(
        id = parcel.readLong(),
        name = parcel.readString() ?: "",
        color = parcel.readString() ?: "",
        type = AnimalType.valueOf(parcel.readString() ?: "Mammal")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(color)
        parcel.writeString(type.name)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AnimalImpl> {
        override fun createFromParcel(parcel: Parcel): AnimalImpl {
            return AnimalImpl(parcel)
        }

        override fun newArray(size: Int): Array<AnimalImpl?> {
            return arrayOfNulls(size)
        }
    }
}
