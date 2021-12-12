const val LAMBDA = 4.0
const val CHANNEL_TIME = 0.8
const val MONEY_FROM_ONE = 4.0
const val CHANNEL_FEE = 2.0
const val QUEUE_FEE = 0.3
const val HOURS = 100000.0

fun main() {
    val results = MutableList(20){index ->
        work(2, index).apply { label = "Для 2-х потоков с длинной очереди $index" }
    }
    results.add(work(3, 0).apply { label = "Для 3-х потоков без очереди" })
    results.sortByDescending { it.moneyPerHour }
    results.forEach { it.print() }
}

fun work(channelCount: Int, queueSize: Int): WorkResult {
    val channels = List(channelCount) { Channel(CHANNEL_TIME) }
    val generator = Generator(LAMBDA)
    val queue = Queue(queueSize)
    var time = 0.0
    var deltaTime = 0.0
    while (time < HOURS) {
        val wasGenerated = generator.tick(deltaTime)
        channels.forEach { it.tick(deltaTime) }
        if (wasGenerated) {
            val emptyChannel = channels.find { it.isEmpty }
            if (emptyChannel != null) {
                emptyChannel.set()
            } else {
                queue.increment()
            }
        }
        for (i in 1..queue.contains) {
            val anotherEmptyChannel = channels.find { it.isEmpty }
            if (anotherEmptyChannel != null) {
                anotherEmptyChannel.set()
                queue.pull()
            } else {
                break
            }
        }
        time += deltaTime
        val minimumChannelTime = channels.filter { !it.isEmpty }.minOfOrNull { it.timeToNextTick }
        deltaTime = if (minimumChannelTime != null && minimumChannelTime < generator.timeToNextGenerate) {
            minimumChannelTime
        } else {
            generator.timeToNextGenerate
        }
    }
    val positiveMoney = channels.sumOf { it.result } * MONEY_FROM_ONE
    val fee = channels.size * time * CHANNEL_FEE + queueSize * time * QUEUE_FEE
    return WorkResult(positiveMoney, fee, time)
}