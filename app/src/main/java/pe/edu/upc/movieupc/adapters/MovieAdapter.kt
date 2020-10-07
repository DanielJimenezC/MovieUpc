package pe.edu.upc.movieupc.adapters

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.prototype_movie.view.*
import pe.edu.upc.movieupc.MainActivity
import pe.edu.upc.movieupc.R
import pe.edu.upc.movieupc.fragments.SearchFragment
import pe.edu.upc.movieupc.model.Movie
import pe.edu.upc.movieupc.viewModel.MovieViewModel

class MovieAdapter(val movies: MutableList<Movie>, val movieViewModel: MovieViewModel, val activity: FragmentActivity) : RecyclerView.Adapter<MovieAdapter.PrototypeMovie>() {

    private var notificationId = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.PrototypeMovie {
        return PrototypeMovie(LayoutInflater.from(parent.context).inflate(R.layout.prototype_movie, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieAdapter.PrototypeMovie, position: Int) {
        holder.bindTo(movies[position])
    }

    inner class PrototypeMovie(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.tvTitle
        private val tvOverview = itemView.tvOverview
        private val cScore = itemView.cScore
        private val ibFavorite = itemView.ibFavorite

        fun bindTo(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            cScore.text = movie.vote_average.toString()

            ibFavorite.setOnClickListener {
                movieViewModel.insert(movie)
                movies.remove(movie)
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition, movies.size)
                val message = "The movie " + movie.title.toString() + " was added to favorites"
                val builder = NotificationCompat.Builder(activity.applicationContext,
                    SearchFragment.CHANNEL_ID
                )
                    .setSmallIcon(R.mipmap.ic_home_round)
                    .setContentTitle("MovieUPC")
                    .setContentText(message)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                with(NotificationManagerCompat.from(activity!!.applicationContext)) {
                    notify(notificationId, builder.build())
                    notificationId++
                }
            }
        }
    }
}