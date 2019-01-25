package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    var noCycles = 0
    val mutablePermutation = permutation.toMutableList()
    for (j in 1 until mutablePermutation.size) {
        var i = j - 1
        val v = mutablePermutation[j]
        while (i >=0 && mutablePermutation[i] > v) {
            mutablePermutation[i + 1] = mutablePermutation[i]
            noCycles += 1
            i -= 1
        }
        mutablePermutation[i + 1] = v
    }
    return noCycles % 2 == 0
}