package br.ufpe.cin.walletshare.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Item {

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
    val name: String = ""
    val price: Double = 0.0
    val people: MutableList<Friend> = mutableListOf()

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