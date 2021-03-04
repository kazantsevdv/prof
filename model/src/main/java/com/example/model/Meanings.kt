package com.example.model

import com.google.gson.annotations.SerializedName

class Meanings(
    @field:SerializedName("translation") val translation: Translation?,
    @field:SerializedName("imageUrl") val imageUrl: String?

) {
    override fun toString(): String {
        return translation.toString()
    }
}
