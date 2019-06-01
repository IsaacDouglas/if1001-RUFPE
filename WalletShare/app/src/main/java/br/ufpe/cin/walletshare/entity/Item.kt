package br.ufpe.cin.walletshare.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Item {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
    var name: String = ""
    var price: Double = 0.0
    @Embedded
    var people: MutableList<Friend> = mutableListOf()

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