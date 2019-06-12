package br.ufpe.cin.walletshare.db

import android.arch.persistence.room.*
import br.ufpe.cin.walletshare.entity.Command

@Dao
interface CommandDao {

    @Query("SELECT * FROM command ORDER BY date DESC")
    fun all(): List<Command>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg command: Command)

    @Delete
    fun remove(vararg command: Command)
}