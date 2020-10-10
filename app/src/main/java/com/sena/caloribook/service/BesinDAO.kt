package com.sena.caloribook.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sena.caloribook.model.Besin

@Dao
interface BesinDAO {

    //Data Access Object
    //insert -> Room, insert into
    //suspend -> coroutine scope
    //vargarg -> birden fazla besşn objesi verilebilir
    //long -> id-ler
    @Insert
    suspend fun insertAll(vararg besin: Besin): List<Long>

    @Query("select * from Besin ")
    suspend fun getAllBesin(): List<Besin>

    @Query("select * from Besin where uuid= :besinId")
    suspend fun getBesin(besinId: Int): Besin

    @Query("delete from Besin ")
    suspend fun deleteAll()
}