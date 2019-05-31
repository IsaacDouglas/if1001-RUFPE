package br.ufpe.cin.walletshare.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Friend (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)