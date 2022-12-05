package es.unex.giiis.asee.repositorylabkotlin.data.roombd

import androidx.room.TypeConverter
import es.unex.giiis.asee.repositorylabkotlin.data.model.Owner
import java.util.*

class OwnerConverter {
    @TypeConverter
    fun OwnerToUsername(owner: Owner?): String? {
        return owner?.login?.lowercase()
    }

    @TypeConverter
    fun UsernameToOwner(username: String?): Owner? {
        return username?.let { Owner(it) }
    }
}