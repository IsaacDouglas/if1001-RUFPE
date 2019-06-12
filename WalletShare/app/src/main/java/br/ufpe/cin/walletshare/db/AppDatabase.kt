package br.ufpe.cin.walletshare.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import br.ufpe.cin.walletshare.entity.Command
import br.ufpe.cin.walletshare.entity.Friend

@Database(entities = [Friend::class, Command::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun friendDao(): FriendDao
    abstract fun commandDao(): CommandDao
}