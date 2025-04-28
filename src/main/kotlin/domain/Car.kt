package domain



import kotlinx.coroutines.delay
import kotlin.random.Random

class Car(
    val name: String,
    var position: Int = 0
) {
    suspend fun move() {
        delay(Random.nextLong(0, 501))
        position += 1
    }
}
