package com.example.mid_prep_ps

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(BmiData::class), version = 1, exportSchema = false)
abstract class BmiDatabase : RoomDatabase(){
    abstract fun getBmiDao():BmiDAO

    companion object{

        @Volatile
        private var INSTANCE: BmiDatabase?=null

        fun getDatabase(context: Context) :BmiDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BmiDatabase::class.java,
                    "bmi_database"
                ).build()
                INSTANCE=instance
                instance
            }
        }

    }

}