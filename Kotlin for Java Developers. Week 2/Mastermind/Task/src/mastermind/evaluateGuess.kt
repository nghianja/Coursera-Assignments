package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var right = 0
    var wrong = 0

    for (i in guess.indices) {
        when {
            secret[i] == guess[i] -> right++
            secret.contains(guess[i]) -> wrong++
        }
    }

    return Evaluation(right, wrong)
}
