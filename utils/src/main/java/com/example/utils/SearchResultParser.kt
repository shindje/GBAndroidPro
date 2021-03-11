package com.example.gbandroidpro.model

import com.example.model.AppState

fun parseSearchResults(data: AppState): AppState {
    val newSearchResults = arrayListOf<com.example.model.DataModel>()
    when (data) {
        is AppState.Success -> {
            val searchResults = data.data
            if (!searchResults.isNullOrEmpty()) {
                for (searchResult in searchResults) {
                    parseResult(searchResult, newSearchResults)
                }
            }
        }
    }

    return AppState.Success(newSearchResults)
}

private fun parseResult(dataModel: com.example.model.DataModel, newDataModels: ArrayList<com.example.model.DataModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<com.example.model.Meanings>()
        if (dataModel.meanings != null) {
            for (meaning in dataModel.meanings!!) {
                if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                    newMeanings.add(
                        com.example.model.Meanings(
                            meaning.translation,
                            meaning.imageUrl
                        )
                    )
                }
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(
                com.example.model.DataModel(
                    dataModel.text,
                    newMeanings,
                    dataModel.isFavorite
                )
            )
        }
    }
}

fun convertMeaningsToString(meanings: List<com.example.model.Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
        }
    }
    return meaningsSeparatedByComma
}