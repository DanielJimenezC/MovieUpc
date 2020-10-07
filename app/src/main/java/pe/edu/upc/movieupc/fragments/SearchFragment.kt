package pe.edu.upc.movieupc.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.prototype_movie.*
import pe.edu.upc.movieupc.R
import pe.edu.upc.movieupc.adapters.MovieAdapter
import pe.edu.upc.movieupc.model.Movie
import pe.edu.upc.movieupc.network.api.MovieApi
import pe.edu.upc.movieupc.network.model.MovieApiResponse
import pe.edu.upc.movieupc.viewModel.MovieViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private var notificationId = 0

    lateinit var movieAdapter: MovieAdapter
    var movies: MutableList<Movie> = ArrayList()
    private lateinit var movieViewModel: MovieViewModel

    companion object {
        private const val ERROR_TAG = "Error"
        const val  CHANNEL_ID = "pe.edu.upc.mvvm.NOTIFICATION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
            .get(MovieViewModel::class.java)
        createNotificationChannel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMovie.layoutManager = LinearLayoutManager(context)
        bnSearch.setOnClickListener {
            hideKeyboard(view)
            searchMovies()
        }
    }

    private fun searchMovies() {
        val apiKey = "3cae426b920b29ed2fb1c0749f258325"
        val query = tMovie.text.toString()
        val retrofit = MovieApi.create().getSearchMovies(apiKey, query)

        retrofit.enqueue(object : Callback<MovieApiResponse> {
            override fun onFailure(call: Call<MovieApiResponse>, t: Throwable) {
                Log.d(ERROR_TAG, t.toString())
            }

            override fun onResponse(
                call: Call<MovieApiResponse>, response: Response<MovieApiResponse>) {
                if (response.isSuccessful) {
                    movies = response.body()!!.results
                    movieAdapter = MovieAdapter(movies, movieViewModel, activity!!)
                    rvMovie.adapter = movieAdapter
                    if (movies.size == 0) {
                        Toast.makeText(activity!!,"We don't found your Movie",Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Shared"
            val descriptionText = "Notification from Phone"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(SearchFragment.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethod: InputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethod.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}