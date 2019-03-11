package no.ab.tictactoev02.IO

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.util.*

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userID")
    var id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "win")
    var win: Int,
    @ColumnInfo(name = "draw")
    var draw: Int,
    @ColumnInfo(name = "loose")
    var loose: Int){

    @Ignore
    constructor(name: String, win: Int, draw: Int, loose: Int) :
            this(0,name,win,draw,loose)

    public fun incrementWin(value: Int){
        val win = this.win
        this.win = win+value
    }

    public fun incrementDraw(value: Int){
        val draw = this.draw
        this.draw = draw+value
    }

    public fun incrementLoose(value: Int){
        val loose = this.loose
        this.loose = loose+value
    }

}