package pe.edu.upc.movieupc.persistance.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import pe.edu.upc.movieupc.model.Movie
import pe.edu.upc.movieupc.persistance.database.AppDataBase

class MovieRepository(private val movieDao: MovieDao) {

    val movies: LiveData<List<Movie>> = movieDao.getAll()

    suspend fun insert(movie: Movie){
        movieDao.insertMovie(movie)
    }
}