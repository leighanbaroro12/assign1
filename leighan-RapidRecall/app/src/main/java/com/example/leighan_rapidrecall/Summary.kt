package com.example.leighan_rapidrecall

class Summary(
    private var _totalAttempts: Int,
    private var _correctAttempts: Int,
) {
    // Accuracy starts @0.0 because no games played yet
    private var _accuracy = 0.0f

    // Read-only variables that use get(),
    // so if it's private component changes the
    // value also changes for this
    val totalAttempts: Int get() = _totalAttempts
    val correctAttempts: Int get() = _correctAttempts
    val accuracy: Float get() = _accuracy

    fun increaseTotalAttempts() { _totalAttempts++ }
    fun increaseCorrectAttempts() { _correctAttempts++ }

    fun setAccuracy(totalAttempts: Int, correctAttempts: Int) {
        _accuracy =  correctAttempts.toFloat() / totalAttempts.toFloat()
    }
}