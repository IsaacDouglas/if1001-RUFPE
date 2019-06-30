package br.ufpe.cin.walletshare.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class Item: Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
    var name: String = ""
    var price: Double = 0.0
    var people: MutableList<Friend> = mutableListOf()

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray? = null

    fun dividedPrice(): Double? {
        if (people.isEmpty()) {
            return null
        }else{
            return price / people.count()
        }
    }

    fun remove(friend: Friend) {
        people.remove(friend)
    }

}