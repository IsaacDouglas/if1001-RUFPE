package br.ufpe.cin.walletshare.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
class Command: Serializable {
    
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
    var name: String = ""
    var date: Date = Date()
    var people: MutableList<Friend> = mutableListOf()
    var items: MutableList<Item> = mutableListOf()

    fun total(): Double {
        return items.map { it.price }.sum()
    }

    fun split(): Double {
        return total() / people.count()
    }

    fun valueFor(friend: Friend): Double {
        val items = itemFor(friend)
        return items.map { it.dividedPrice() ?: 0.0 }.sum()
    }

    fun itemFor(friend: Friend): List<Item> {
        return items.filter { it.people.contains(friend) }
    }

    fun remove(friend: Friend) {
        people.remove(friend)
        items.forEach {
            it.remove(friend)
        }
    }

    fun remove(item: Item) {
        items.remove(item)
    }
}

