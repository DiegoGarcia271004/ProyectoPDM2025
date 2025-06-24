package com.proyectoPdm.seashellinc.data.model.responses

import com.google.gson.annotations.SerializedName
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.database.entity.UserEntity
import com.proyectoPdm.seashellinc.data.model.compound.Compound
import com.proyectoPdm.seashellinc.data.model.user.User

data class MolarMassData (
    @SerializedName("_id")
    val id : String?,
    val name : String,
    val unit : String,
    val value : Double
) {
    fun toMolarMassEntity(userId : String) : CompoundEntity {
        return CompoundEntity(
            id = id.toString(),
            compound = Compound(name, unit, value),
            userId = userId,
        )
    }
}

data class UserObjectResponse (
    @SerializedName("user")
    val user : UserData,
    val token : String
)

data class UserData (
    @SerializedName("id")
    val id : String,
    val username : String,
    val email : String,
    val isPremium : Boolean,
    val molarMassList : List<MolarMassData>
) {
    fun toUserEntity(token : String) : UserEntity {
        return UserEntity(
            id = id,
            user = User(username, email, isPremium),
            token = token
        )
    }

    fun toMolarMassEntity() : List<CompoundEntity> {
        return molarMassList.map { molarMass ->
            CompoundEntity(
                id = molarMass.id.toString(),
                compound = Compound(molarMass.name, molarMass.unit, molarMass.value),
                userId = id
            )
        }
    }
}