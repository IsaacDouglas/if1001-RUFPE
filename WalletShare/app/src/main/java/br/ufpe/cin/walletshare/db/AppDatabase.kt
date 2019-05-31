package br.ufpe.cin.walletshare.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.ufpe.cin.walletshare.entity.Friend

@Database(entities = [Friend::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun friendDao(): FriendDao
}