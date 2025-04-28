package racingcar.controller

import kotlinx.coroutines.*
import racingcar.view.InputView
import racingcar.view.OutputView
import racingcar.model.RacingCar
import racingcar.model.RaceGame

object RacingGameController {

    fun start() {
        val inputView = InputView()
        val outputView = OutputView()

        // 사용자로부터 입력 받기
        val carNames : List<String> = inputView.getCarNames()
        val goalDistance = inputView.getRoundCount()

        val cars = carNames.map { RacingCar(it) }
        val raceGame = RaceGame(cars)

        runBlocking {
            val jobs = mutableListOf<Job>()
            for(car in cars) {
                val job = launch {
                    runRace(raceGame, car, goalDistance, outputView)
                }
                jobs.add(job)
            }

            val winnerJob = launch {
                while(isActive) {
                    val winners = raceGame.findWinners(goalDistance)

                    if (winners.isNotEmpty()) {
                        for (job in jobs) {
                            job.cancel()
                            this.cancel()
                        }
                        // 우승자 출력
                        outputView.announceWinners(winners)
                    }
                }
            }


        }
    }

    // 경주 실행 및 출력
    private suspend fun runRace(raceGame: RaceGame, car: RacingCar, goalDistance: Int, outputView: OutputView) =
        CoroutineScope(Dispatchers.Default).launch {
            repeat(goalDistance) {
                raceGame.runOneRound(car)
            }
        }
}
