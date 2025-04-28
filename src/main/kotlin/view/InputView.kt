package view

object InputView {

    fun getCarNames(): List<String> {
        println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)")
        val input = readln()
        return input.split(",").map(String::trim)
    }

    fun getGoal(): Int {
        println("목표 거리를 입력하세요.")
        val input = readln()
        return input.toIntOrNull()
            ?: throw IllegalArgumentException("[ERROR] 숫자를 입력하세요.")
    }

}