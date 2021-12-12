import kotlin.math.E
import kotlin.math.log
import kotlin.random.Random

class Channel(private val t: Double) {
    var result: Int = 0
        private set

    private val deltaTime: Double get() = (-t) * log(Random.nextDouble(), E)

    private var _timeToNextTick = 0.0
    val timeToNextTick: Double
        get() = _timeToNextTick

    val isEmpty get() = timeToNextTick <= 0.0
    fun tick(time: Double) {
        if (timeToNextTick > 0.0) {
            _timeToNextTick -= time
            if (isEmpty) {
                result++
            }
        }
    }

    fun set() {
        if (isEmpty) {
            _timeToNextTick = deltaTime
        } else {
            throw Exception("Attend to set for working channel")
        }
    }
}