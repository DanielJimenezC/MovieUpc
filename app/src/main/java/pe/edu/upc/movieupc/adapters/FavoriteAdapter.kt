package pe.edu.upc.movieupc.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.movieupc.R
import pe.edu.upc.movieupc.model.Movie

class FavoriteAdapter(private val context: Context): RecyclerView.Adapter<FavoriteAdapter.PrototypeFavorite>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var movies = emptyList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrototypeFavorite {
        val itemView = inflater.inflate(R.layout.prototype_favorite, parent, false)
        return PrototypeFavorite(itemView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: PrototypeFavorite, position: Int) {
        holder.onBind(movies[position])
    }

    inner class PrototypeFavorite(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMovie = itemView.findViewById<TextView>(R.id.tvMovie)
        private val tvMovieOverview = itemView.findViewById<TextView>(R.id.tvMovieOverview)

        fun onBind(movie: Movie) {
            tvMovie.text = movie.title
            tvMovieOverview.text = movie.overview
        }
    }

    internal fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}