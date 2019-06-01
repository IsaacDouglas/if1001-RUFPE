package br.ufpe.cin.walletshare.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import br.ufpe.cin.walletshare.entity.Command

@Dao
interface CommandDao {

    @Query("SELECT * FROM command")
    fun all(): List<Command>

    @Insert
    fun add(vararg command: Command)

    @Delete
    fun remove(vararg command: Command)
}