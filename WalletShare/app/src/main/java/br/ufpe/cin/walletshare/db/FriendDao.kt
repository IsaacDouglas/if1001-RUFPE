package br.ufpe.cin.walletshare.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import br.ufpe.cin.walletshare.entity.Friend

@Dao
interface FriendDao {

    @Query("SELECT * FROM friend ORDER by name")
    fun all(): List<Friend>

    @Insert
    fun add(vararg friend: Friend)

    @Delete
    fun remove(vararg friend: Friend)
}