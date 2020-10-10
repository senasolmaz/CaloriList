package com.sena.caloribook.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sena.caloribook.model.Besin

@Database(entities = arrayOf(Besin::class), version = 1)
abstract class BesinDatabase : RoomDatabase() {

    abstract fun besinDao(): BesinDAO

    //Singleton
    companion object {
        //volatile bu instanceyi diğer thread lere görünür yapıyor(bu değişkeni izlemek için)
        @Volatile
        private var instance: BesinDatabase? = null

        //bu sınıftan nesne olustuysa onu dondur, olusmdıysa olustur
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance
                ?: createDatabase(context).also {   //instance boş ise database olustur ve sunlarıda yap
                    instance = it
                }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BesinDatabase::class.java,
                "BesinDatabase"
            ).build()
    }
}