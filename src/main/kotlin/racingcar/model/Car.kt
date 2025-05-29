package racingcar.model

import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class Car(
    val name: String,
    var position: Int = 0,
    var speed: Int = 1 // 기본 속도 설정
) {
    init {
        require(name.isNotBlank() && name.length <= 5) {
            "자동차 이름은 1자 이상 5자 이하이어야 합니다. : $name"
        }
    }

    // 차의 위치를 전진시키는 메소드
    suspend fun moveForward() {
        delay(RandomMovingRule.getDelayTime().milliseconds)
        position += speed  // 이동 거리가 속도에 비례
        println(this)
    }

    // 차의 속도를 증가시키는 메소드 (부스트)
    fun boost() {
        speed += 1
        println("$name 속도가 증가했습니다! 현재 속도: $speed")
    }

    // 차의 속도를 감소시키는 메소드 (느리게)
    fun slow() {
        if (speed > 1) {  // 최소 속도를 1로 설정
            speed -= 1
            println("$name 속도가 감소했습니다! 현재 속도: $speed")
        } else {
            println("${name}은 이미 최소 속도입니다!")
        }
    }

    // 차를 멈추게 하는 메소드
    fun stop() {
        speed = 0
        println("${name}이 멈췄습니다!")
    }

    override fun toString(): String {
        return "$name : ${"-".repeat(position)}"
    }

    companion object {
        fun of(name: String, position: Int = 0, speed: Int = 1): Car {
            return Car(name, position, speed)
        }
    }
}
