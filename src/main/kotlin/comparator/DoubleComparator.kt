package comparator

import java.io.Serializable

class DoubleComparator : Comparator, Serializable {
    override fun compare(o1: Any, o2: Any): Double {
        return (o1 as Double) - (o2 as Double)
    }
}