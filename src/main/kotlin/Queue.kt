class Queue(private val size: Int) {
    var contains = 0
        private set

    fun increment() {
        if (contains < size) {
            contains++
        }
    }

    fun pull(): Boolean {
        if (contains > 0) {
            contains--
            return true
        }
        return false
    }
}