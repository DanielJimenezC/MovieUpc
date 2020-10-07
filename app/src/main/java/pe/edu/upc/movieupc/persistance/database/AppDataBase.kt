package pe.edu.upc.movieupc.persistance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.movieupc.model.Movie
import pe.edu.upc.movieupc.persistance.repository.MovieDao

@Database(entities = [Movie::class], version = 3, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getDao(): MovieDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, AppDataBase::class.java, "MoviesDB")
                    .addCallback(AppDataBaseCallBack(scope))
                    .build()
            }
            return INSTANCE as AppDataBase
        }
    }

    private class AppDataBaseCallBack(val scope: CoroutineScope): RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDataBase(database.getDao())
                }
            }
        }
        suspend fun populateDataBase(movieDao: MovieDao) {
            movieDao.deleteAll()
        }
    }
}