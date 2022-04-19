package pruebas.gloriajaureguiapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import pruebas.gloriajaureguiapp.databinding.ActivityAppBinding

/**
 * Muestra la actividad principal de la aplicación.
 */
class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationGraph()

    }

    private fun setNavigationGraph() {
        val navController = findNavController(R.id.main_content_app)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_menu)
        binding.bnNavigationview.selectedItemId = R.id.graphFragment
        binding.bnNavigationview.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.graphFragment -> {
                    if (navController.currentDestination?.id != R.id.graphFragment) {
                        navGraph.startDestination = R.id.graphFragment
                        navController.setGraph(navGraph, null)
                    }
                    true
                }
                R.id.listTopFragment -> {
                    if (navController.currentDestination?.id != R.id.listTopFragment) {
                        navGraph.startDestination = R.id.listTopFragment
                        navController.setGraph(navGraph, null)
                    }
                    true
                }
                R.id.logout -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    this.finish()
                    true
                }
                else -> true
            }
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.graphFragment,
                R.id.listTopFragment,
                R.id.logout
            )
        )
        findNavController(R.id.main_content_app).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.graphFragment -> binding.bnNavigationview.visibility
                R.id.listTopFragment -> binding.bnNavigationview.visibility
                R.id.logout -> binding.bnNavigationview.visibility
                else -> binding.bnNavigationview.isGone
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}