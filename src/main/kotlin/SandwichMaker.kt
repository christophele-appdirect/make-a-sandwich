import com.github.kittinunf.fuel.core.await
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.jacksonDeserializerOf
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class SandwichMaker {

		suspend fun makeASandwich(): Sandwich {
				return coroutineScope {
						val bread = async { fetch("baguette") }
						val butter = async { fetch("salted-butter") }
						val ham = async { fetch("lot-of-ham") }

						Sandwich(
										name = "Jambon Beurre",
										ingredients = listOf(bread.await(), butter.await(), ham.await()))
				}
		}

		private suspend fun fetch(ingredient: String) =
						"https://ingredients.getsandbox.com:443/ingredients/$ingredient"
										.httpGet()
										.await(jacksonDeserializerOf<Ingredient>())
}

data class Sandwich(val name: String, val ingredients: List<Ingredient>)
data class Ingredient(val name: String, val calories: Int)
