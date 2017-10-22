package iii_conventions
import iv_properties.toMillis

data class MyDate(var year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        val i = toMillis() - other.toMillis()
        return when {
            i == 0L -> 0
            i > 0L -> 1
            else -> -1
        }
    }
}

operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)

class DateRange(
        override var start: MyDate,
        override val endInclusive: MyDate
) : ClosedRange<MyDate>, Iterator<MyDate> {

    override fun hasNext(): Boolean = start.toMillis() <= endInclusive.toMillis()

    override fun next(): MyDate {
        val previous = start.copy()
        start = start.nextDay()
        return previous
    }
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)
operator fun TimeInterval.times(number: Int) = RepeatedTimeInterval(this, number)

operator fun MyDate.plus(timeInterval: TimeInterval) = addTimeIntervals(timeInterval, 1)
operator fun MyDate.plus(interval: RepeatedTimeInterval): MyDate = this.addTimeIntervals(interval.timeInterval, interval.number)
