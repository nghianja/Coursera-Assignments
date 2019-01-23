package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.subtract(this.trips.map { it.driver })

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers.filter { p -> this.trips.flatMap { it.passengers }.count { it == p } >= minTrips }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.allPassengers.filter { p -> this.trips.filter { it.driver == driver }.flatMap { it.passengers }.count { it == p } > 1 }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers.subtract(this.trips.filter { it.discount == null }.flatMap { it.passengers })

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val list = mutableListOf<IntRange>()
    this.trips.forEach {
        when (it.duration) {
            in 0..9 -> list.add(IntRange(0, 9))
            in 10..19 -> list.add(IntRange(10, 19))
            in 20..29 -> list.add(IntRange(20, 29))
            in 30..39 -> list.add(IntRange(30, 39))
            in 40..49 -> list.add(IntRange(40, 49))
        }
    }
    val map = list.groupingBy { it }.eachCount()
    return map.maxBy { it.value }?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (this.trips.isNotEmpty()) {
        val successfulDrivers = Math.floor(this.allDrivers.count() * 0.2).toInt()
        val driverIncome = this.trips.groupBy { it.driver }.mapValues { it.value.sumByDouble { trip -> trip.cost } }
        val topIncome = driverIncome.toList().sortedByDescending { (_, value) -> value }.subList(0, successfulDrivers).sumByDouble { it.second }
        val totalIncome = this.trips.sumByDouble { it.cost }
        return topIncome / totalIncome >= 0.8
    }
    return false
}