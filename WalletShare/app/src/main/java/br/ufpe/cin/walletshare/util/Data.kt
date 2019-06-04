package br.ufpe.cin.walletshare.util

import android.arch.persistence.room.Room
import android.content.Context
import br.ufpe.cin.walletshare.db.AppDatabase
import br.ufpe.cin.walletshare.db.FriendDao

class Data {

    private val nameDatabase: String = "wallet-database"

    var friendDao: FriendDao

    constructor(context: Context) {
        val database = Room.databaseBuilder(context, AppDatabase::class.java, nameDatabase)
            .allowMainThreadQueries()
            .build()
        friendDao = database.friendDao()
    }

    companion object Factory {
        private var instance: Data? = null
        fun getInstance(context: Context): Data {
            if (instance == null) {
                instance = Data(context)
            }
            return instance!!
        }
    }


}