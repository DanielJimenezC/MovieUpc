package pe.edu.upc.movieupc.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.upc.movieupc.model.Movie
import pe.edu.upc.movieupc.persistance.database.AppDataBase
import pe.edu.upc.movieupc.persistance.repository.MovieRepository

class MovieViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MovieRepository
    val movies: LiveData<List<Movie>>

    init {
        val movieDao = AppDataBase.getInstance(application, viewModelScope).getDao()
        repository = MovieRepository(movieDao)
        movies = repository.movies
    }

    fun insert(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(movie)
    }
}