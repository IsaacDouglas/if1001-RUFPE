package br.ufpe.cin.walletshare.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class Friend: Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
    var name: String = ""
}