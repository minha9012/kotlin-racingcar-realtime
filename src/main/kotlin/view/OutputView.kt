package view

object OutputView {
    fun printCarPosition(name: String, position: Int) {
        println("$name : ${"-".repeat(position)}")
    }

    fun printWinner(name: String) {
        println("\n${name}가 최종 우승했습니다.")
    }

    fun printAddCar(name: String) {
        println("${name} 참가 완료!")
    }
}
