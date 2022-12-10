package factory

import builder.DoubleObjectBuilder
import builder.PolarVectorObjectBuilder
import builder.UserTypeBuilder


class UserFactory {
    private val builders: List<UserTypeBuilder> = listOf(DoubleObjectBuilder(), PolarVectorObjectBuilder())

    fun getTypeNameList(): List<String?>{
        var listOfType: List<String?> = listOf()
        for (t in builders){
            listOfType +=  t.typeName()
        }
        return listOfType
    }

    fun getBuilderByName(name: String): UserTypeBuilder{
        if (name == null) throw NullPointerException()
        for (userType in builders){
            if (name == userType.typeName())
                return userType
        }
        throw IllegalArgumentException()
    }



}