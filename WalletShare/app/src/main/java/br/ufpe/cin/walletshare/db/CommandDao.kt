package br.ufpe.cin.walletshare.db

import android.arch.persistence.room.*
import br.ufpe.cin.walletshare.entity.Command
import android.arch.persistence.room.OnConflictStrategy

@Dao
interface CommandDao {

    @Query("SELECT * FROM command ORDER BY date DESC")
    fun all(): List<Command>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(command: Command): Long

    @Delete
    fun remove(vararg command: Command)
}