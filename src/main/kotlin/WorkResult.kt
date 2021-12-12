data class WorkResult (val positiveMoneyAll:Double, val feeAll:Double, val timeInHours:Double, var label:String = ""){
    val moneyPerHour get() = (positiveMoneyAll - feeAll)/timeInHours
    val moneyPositivePerHour get() = positiveMoneyAll/timeInHours
    val feePerHour get() = feeAll/timeInHours

    fun print(){
        println(label)
        println("Денег в час в среднем заработано $moneyPositivePerHour")
        println("Расходы на час работы $feePerHour")
        println("Чистая прибыль $moneyPerHour")
        println("------------------------------------------------------")
    }
}