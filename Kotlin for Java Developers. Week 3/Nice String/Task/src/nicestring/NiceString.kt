package nicestring

fun String.isNice(): Boolean {
    var conditions = 0

    if (!this.contains(Regex("bu|ba|be")))
        conditions++

    if (Regex("[aeiou]").findAll(this).count() >= 3)
        conditions++

    if (this.contains(Regex("([a-z])\\1")))
        conditions++

    return (conditions >= 2)
}
