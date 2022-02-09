package com.example.demoforajob.data.source.remote.model

import com.example.demoforajob.data.source.remote.model.Location
import com.example.demoforajob.data.source.remote.model.Origin

data class Result(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)