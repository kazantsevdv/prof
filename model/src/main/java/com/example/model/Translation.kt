package com.example.model

import com.google.gson.annotations.SerializedName

class Translation(@field:SerializedName("text") val translation: String?){
    override fun toString(): String {
        return translation.toString()
    }
}
