import controller.CarRaceController
import kotlinx.coroutines.runBlocking
import view.InputView

fun main() {

    val carNames = InputView.getCarNames()

    val goal = InputView.getGoal()

    runBlocking {
        CarRaceController(carNames, goal).start()
    }
}