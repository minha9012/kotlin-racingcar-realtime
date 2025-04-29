
package controller

import domain.Car
import kotlinx.coroutines.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import view.OutputView
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

class CarRaceController(
    private val carNames: List<String>,
    private val goal: Int
) {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val raceFinished = AtomicBoolean(false)
    private val cars = carNames.map { Car(it) }.toMutableList()
    private val jobs =  CopyOnWriteArrayList<Job>()

    private val pauseMutex = Mutex(locked = false)

    suspend fun start() {
        cars.forEach { car ->
            scope.launch {
                startCar(car)
            }
        }
         scope.launch { getNewCar() }

        jobs.joinAll()
    }

    private fun startCar(car: Car) {
        val job = scope.launch {
            while (!raceFinished.get() && isActive) {
                pauseMutex.withLock {
                    car.move()
                    OutputView.printCarPosition(car.name, car.position)

                    if (car.position >= goal && raceFinished.compareAndSet(false, true)) {
                        OutputView.printWinner(car.name)
                        cancelRace()
                    }
                }

            }
        }
        jobs.add(job)
    }

    private fun cancelRace() {

        jobs.forEach { it.cancel() }
    }

    private suspend fun getNewCar() {
        withContext(Dispatchers.Default) {
            while (!raceFinished.get()) {
                val inputWait = readln()
                if(inputWait.isEmpty()) {
                    pauseMutex.withLock {
                        val input = readln()
                        if (input.startsWith("add ")) {
                            val newCarName = input.removePrefix("add ").trim()
                            if (newCarName.isNotEmpty()) {
                                addCar(newCarName)
                            }
                        }
                    }
                }

            }
        }
    }
    private fun addCar(name: String) {
        val newCar = Car(name)
        cars.add(newCar)
        startCar(newCar)
        OutputView.printAddCar(name)
    }
}