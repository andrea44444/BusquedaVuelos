package com.example.aeropuerto3.data

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.aeropuerto3.model.Favorite


@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class AirportDatabase:RoomDatabase() {

    abstract fun airportDao(): AirportDao


    companion object{
        @Volatile
        private var INSTANCE: AirportDatabase? = null

        fun getDatabase(context: Context): AirportDatabase {
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(context, AirportDatabase::class.java,"flight_search")
                    .createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it  }
            }
        }
    }
}