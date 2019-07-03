package br.ufpe.cin.walletshare.db

import android.arch.persistence.room.*
import br.ufpe.cin.walletshare.entity.OrderSheet
import android.arch.persistence.room.OnConflictStrategy

@Dao
interface OrderSheetDao {

    @Query("SELECT * FROM orderSheet ORDER BY date DESC")
    fun all(): List<OrderSheet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(orderSheet: OrderSheet): Long

    @Delete
    fun remove(vararg orderSheet: OrderSheet)
}