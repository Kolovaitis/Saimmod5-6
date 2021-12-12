import kotlin.math.E
import kotlin.math.log
import kotlin.random.Random

class Generator(private val lambda: Double) {
    val nextInterval: Double get() = (1 / -lambda) * log(Random.nextDouble(), E)
    var timeToNextGenerate = nextInterval
        private set

    fun tick(time: Double): Boolean {
        timeToNextGenerate -= time
        if (timeToNextGenerate <= 0.0) {
            timeToNextGenerate = nextInterval
            return true
        }
        return false
    }
}