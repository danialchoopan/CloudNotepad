package ir.danialchoopan.cloudnotepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navController = findNavController(R.id.fragmentHostHomeActivity)
        val bottomNavControllerHomeActivity =
            findViewById<BottomNavigationView>(R.id.bottomNavigationViewHomeActivity)
        bottomNavControllerHomeActivity.setupWithNavController(navController)
        if (intent.extras?.getString("MENU") != null) {
            if (intent.extras?.getString("MENU") == "PUBLIC_NOTES_MENU") {
                bottomNavControllerHomeActivity.selectedItemId = R.id.publicNotesFragment
            }
            //end if menu public menu
        }
        //end if not null menu
    }
}