package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger

val zero = Rational(BigInteger.valueOf(0), BigInteger.valueOf(1))

fun lcm(first: BigInteger, second: BigInteger): BigInteger {
    val m = if (first < BigInteger.valueOf(0)) -first else first
    val n = if (second < BigInteger.valueOf(0)) -second else second
    return m * (n / m.gcd(n))
}

class Rational(numerator: BigInteger, denominator: BigInteger) : Number(), Comparable<Rational> {
    private var n: BigInteger = BigInteger.valueOf(0)
    private var d: BigInteger = BigInteger.valueOf(1)

    init {
        if (denominator == BigInteger.valueOf(0))
            throw IllegalArgumentException("Denominator is zero")
        val g = numerator.gcd(denominator)
        n = numerator / g
        d = denominator / g
    }

    override fun compareTo(other: Rational): Int {
        val lhs = this.n * other.d
        val rhs = this.d * other.n
        return when {
            lhs < rhs -> -1
            lhs > rhs -> 1
            else -> 0
        }
    }

    operator fun div(other: Rational): Rational {
        return this.times(Rational(other.d, other.n))
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Rational)
            return false
        return this.compareTo(other) == 0
    }

    override fun hashCode(): Int {
        return this.toString().hashCode()
    }

    operator fun minus(other: Rational): Rational {
        return this.plus(other.unaryMinus())
    }

    operator fun plus(other: Rational): Rational {
        // Special cases
        if (this.compareTo(zero) == 0)
            return other
        if (other.compareTo(zero) == 0)
            return this

        // Find gcd of numerators an denominators
        val nGcd = this.n.gcd(other.n)
        val dGcd = this.d.gcd(other.d)

        // Add cross-product terms for numerator
        val result = Rational((this.n / nGcd) * (other.d / dGcd) + (other.n / nGcd) * (this.d / dGcd), lcm(this.d, other.d))

        // Multiply back in
        result.n = result.n * nGcd
        return result
    }

    operator fun times(other: Rational): Rational {
        val p = Rational(this.n, other.d)
        val q = Rational(other.n, this.d)
        return Rational(p.n * q.n, p.d * q.d)
    }

    override fun toByte(): Byte {
        return (n / d).toByte()
    }

    override fun toChar(): Char {
        return (n / d).toChar()
    }

    override fun toDouble(): Double {
        return (n / d).toDouble()
    }

    override fun toFloat(): Float {
        return (n / d).toFloat()
    }

    override fun toInt(): Int {
        return (n / d).toInt()
    }

    override fun toLong(): Long {
        return (n / d).toLong()
    }

    override fun toShort(): Short {
        return (n / d).toShort()
    }

    override fun toString(): String {
        return if (d == BigInteger.valueOf(1))
            "$n"
        else
            "$n/$d"
    }

    operator fun unaryMinus(): Rational {
        return Rational(-n, d)
    }

    operator fun unaryPlus(): Rational {
        return Rational(n, d)
    }
}

infix fun Int.divBy(divider: Int): Rational {
    return Rational(BigInteger.valueOf(this.toLong()), BigInteger.valueOf(divider.toLong()))
}

infix fun Long.divBy(divider: Long): Rational {
    return Rational(BigInteger.valueOf(this), BigInteger.valueOf(divider))
}

infix fun BigInteger.divBy(divider: BigInteger): Rational {
    return Rational(this, divider)
}

fun String.toRational(): Rational {
    val list = this.split("/")
    return if (list.count() < 2) {
        Rational(list[0].toBigInteger(), BigInteger.valueOf(1))
    } else {
        Rational(list[0].toBigInteger(), list[1].toBigInteger())
    }
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}