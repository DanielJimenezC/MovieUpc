package pe.edu.upc.movieupc

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import pe.edu.upc.movieupc.fragments.FavoriteFragment
import pe.edu.upc.movieupc.fragments.SearchFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateTo(bnHome.menu.findItem(R.id.iSearch))

        bnHome.setOnNavigationItemSelectedListener { item ->
            navigateTo(item)
            true
        }
    }

    private fun navigateTo(item: MenuItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flContent, fragmentFor(item))
            .commit()
    }

    private fun fragmentFor(item: MenuItem): Fragment {
        return when (item.itemId) {
            R.id.iSearch -> SearchFragment()
            R.id.iFavorite -> FavoriteFragment()
            else -> SearchFragment()
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Shared"
            val descriptionText = "Notification from Phone"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(SearchFragment.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}