package pe.edu.upc.movieupc.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite.*
import pe.edu.upc.movieupc.R
import pe.edu.upc.movieupc.adapters.FavoriteAdapter
import pe.edu.upc.movieupc.viewModel.MovieViewModel

class FavoriteFragment : Fragment() {

    private lateinit var movieViewModel: MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFavorites.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        loadFavorites()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    private fun loadFavorites(){
        val favoriteAdapter = FavoriteAdapter(activity!!.applicationContext)
        rvFavorites.adapter = favoriteAdapter

        movieViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
            .get(MovieViewModel::class.java)
        movieViewModel.movies.observe(this, Observer { movies ->
            movies?.let {
                favoriteAdapter.setMovies(it)
            } })
    }
}