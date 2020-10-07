package pe.edu.upc.movieupc.persistance.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.edu.upc.movieupc.model.Movie

@Dao
interface MovieDao {

    @Query("select * from Movie")
    fun getAll(): LiveData<List<Movie>>

    @Query("delete from Movie")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(vararg movies: Movie)
}