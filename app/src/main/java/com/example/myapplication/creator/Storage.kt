package com.example.myapplication.creator

import com.example.myapplication.data.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(
            trackId = 1L,
            trackName = "Владивосток 2000",
            artistName = "Мумий Тролль",
            trackTimeMillis = 158000,
            artworkUrl100 = null,
            previewUrl = null
        ),
        TrackDto(
            trackId = 2L,
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283000,
            artworkUrl100 = null,
            previewUrl = null
        ),
        TrackDto(
            trackId = 3L,
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312000,
            artworkUrl100 = null,
            previewUrl = null
        ),
        TrackDto(
            trackId = 4L,
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225000,
            artworkUrl100 = null,
            previewUrl = null
        ),
        TrackDto(
            trackId = 5L,
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272000,
            artworkUrl100 = null,
            previewUrl = null
        ),
        TrackDto(
            trackId = 6L,
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230000,
            artworkUrl100 = null,
            previewUrl = null
        ),
        TrackDto(
            trackId = 7L,
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296000,
            artworkUrl100 = null,
            previewUrl = null
        ),
        TrackDto(
            trackId = 8L,
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195000,
            artworkUrl100 = null,
            previewUrl = null
        ),
        TrackDto(
            trackId = 9L,
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222000,
            artworkUrl100 = null,
            previewUrl = null
        ),
        TrackDto(
            trackId = 10L,
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTimeMillis = 241000,
            artworkUrl100 = null,
            previewUrl = null
        )
    )

    fun search(request: String): List<TrackDto> {
        return listTracks.filter {
            it.trackName.lowercase().contains(request.lowercase())
        }
    }
}
