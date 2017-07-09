package models

case class Pokemon (index: Long, name: String, category: String)

object Pokemon {
  var pokemons = List(
    Pokemon(1, "Pikachu", "eletric"),
    Pokemon(2, "Jigglypuff", "fairy"),
    Pokemon(3, "Squirtle", "water")
    )
  def findAll = pokemons.toList.sortBy(_.index)
}
