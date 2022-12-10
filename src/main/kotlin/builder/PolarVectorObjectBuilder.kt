package builder


import comparator.Comparator
import comparator.PolarVectorComparator
import java.util.Random

class PolarVectorObjectBuilder : UserTypeBuilder {
    override fun typeName(): String {
        return "PolarVector"
    }

    override fun create(): PolarVector {
        val vector = PolarVector()
        vector.angle = Random().nextDouble()
        vector.length = Random().nextDouble()
        return vector
    }

    override fun getComparator(): Comparator{
        return PolarVectorComparator()
    }


    override fun createFromString(s: String?): PolarVector {
        val vector = PolarVector()
        vector.length = s?.split(" ".toRegex())?.toTypedArray()?.get(0)!!.toDouble()
        vector.angle = s?.split(" ".toRegex())?.toTypedArray()?.get(1).toDouble()
        return vector
    }

    override fun toString(`object`: Any?): String {
        return `object`.toString()
    }




}