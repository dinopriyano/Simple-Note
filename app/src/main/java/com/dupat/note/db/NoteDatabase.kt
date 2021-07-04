package com.dupat.note.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dupat.note.db.dao.NoteDao
import com.dupat.note.db.entities.Converters
import com.dupat.note.db.entities.Note
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [Note::class],version = 1)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private var INSTANCE : NoteDatabase? = null
        fun getInstance(ctx: Context) : NoteDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    //Encrypt database using sqlcipher
                    val passphrase = SQLiteDatabase.getBytes(
                        "dupat.id".toCharArray()
                    )
                    val factory = SupportFactory(passphrase)
                    instance = Room.databaseBuilder(
                        ctx.applicationContext,
                        NoteDatabase::class.java,
                        "notes"
                    )
                        .fallbackToDestructiveMigration()
                        .openHelperFactory(factory)
                        .build()
                }

                return instance
            }
        }
    }
}