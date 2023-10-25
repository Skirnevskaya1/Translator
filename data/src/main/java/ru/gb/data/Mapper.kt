package ru.gb.data

import ru.gb.data.dto.SearchResultDto
import ru.gb.data.room.HistoryEntity
import ru.gb.domain.models.DataModel
import ru.gb.domain.models.Meaning
import ru.gb.domain.models.TranslatedMeaning

fun DataModel.toHistoryEntity(): HistoryEntity =
    HistoryEntity(
        this.text,
        this.meanings[0].translatedMeaning.translatedMeaning,
        this.meanings[0].imageUrl
    )

fun List<HistoryEntity>.toListDataModelConvert(): List<DataModel> {
//    if (this == null) return null
    return this.map {
        it.toDataModel()
    }
}

fun HistoryEntity.toDataModel(): DataModel {
//    if (this == null) return null
    return DataModel(
        text = this.word,
        meanings = listOf(
            Meaning(
                TranslatedMeaning(translatedMeaning = this.description),
                imageUrl = this.imageUrl
            )
        )
    )
}


fun List<SearchResultDto>.toListDataModel(): List<DataModel> {
    return this.map { searchResult ->
        var meanings: List<Meaning> = listOf()
        searchResult.meanings?.let {
            meanings = it.map { meaningsDto ->
                Meaning(
                    TranslatedMeaning(meaningsDto.translation?.translation ?: ""),
                    meaningsDto.imageUrl ?: ""
                )
            }
        }
        DataModel(searchResult.text ?: "", meanings)
    }
}
