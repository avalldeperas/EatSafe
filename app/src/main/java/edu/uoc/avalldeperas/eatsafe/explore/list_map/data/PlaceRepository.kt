package edu.uoc.avalldeperas.eatsafe.explore.list_map.data

import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import javax.inject.Inject

class PlaceRepository @Inject constructor() {
    fun getDummyPlaces(): List<Place> {
        return listOf(
            Place(
                placeId = "1",
                name = "Xorús",
                address = "Rambla del Poblenou, 105, Barcelona",
                latitude = 41.4030615965374,
                longitude = 2.1986885043302578
            ),
            Place(
                placeId = "2",
                name = "Can Recasens",
                address = "Rambla del Poblenou, 102, Barcelona",
                latitude = 41.40269141325675, longitude = 2.199825760890306
            ),
            Place(
                placeId = "3",
                name = "Restaurant Puerto Escondido",
                address = "Carrer de Marià Aguiló, 26, Sant Martí, 08005 Barcelona",
                latitude = 41.40352834633225, longitude = 2.1997828455445676
            ),
            Place(
                placeId = "4",
                name = "Can Culleres",
                address = "C/ de Bilbao, 79, Sant Martí, 08005 Barcelona",
                latitude = 41.403866335474774, longitude = 2.2022504779200354
            ),
            Place(
                placeId = "5",
                name = "Sala Beckett",
                address = "Carrer de Bilbao, 79, Sant Martí, 08005 Barcelona",
                latitude = 41.40352834633225, longitude = 2.1997828455445676
            ),
            Place(
                placeId = "6",
                name = "ApriBocca - Italian Restaurant",
                address = "Plaça de Sant Bernat Calbó, 6, Sant Martí, 08005 Barcelona",
                41.40010007133809, 2.2050399753930434
            )
        )
    }
}