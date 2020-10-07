package pe.edu.upc.movieupc.network.model

import com.google.gson.annotations.SerializedName
import pe.edu.upc.movieupc.model.Movie

class MovieApiResponse (
    @SerializedName("results")
    val results: MutableList<Movie>
)