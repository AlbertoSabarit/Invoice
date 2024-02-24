package com.murray.invoice.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.murray.invoice.R
import com.murray.invoice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    val toolbar: Toolbar get() = binding.content.toolbar
    val drawer: DrawerLayout get() = binding.drawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Sustituir la AppBar por defecto por el Widget Toolbar de nuestro Layout
        setSupportActionBar(binding.content.toolbar)

        //OPCION 1
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //OPCION 2
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        //2. Se crea la configuración de la App Bar con el control de la apertura y  cierre del Drawe Layout
        appBarConfiguration =
            AppBarConfiguration.Builder(navController.graph).setOpenableLayout(binding.drawerLayout)
                .build()


        //3. Que sincronice el DrawerLayout con la AppBar
        NavigationUI.setupWithNavController(
            binding.content.toolbar, navController, appBarConfiguration
        )


        //Configurar el evento click del menú Nav_view
        setupNavigationView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                navController.navigate(R.id.settingsFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Implementar el listener de las opciones del menú del componente Nav_View
     */
    private fun setupNavigationView() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_customer -> {
                    navController.navigate(R.id.nav_graph_customer)
                }
                R.id.action_item -> {
                    navController.navigate(R.id.nav_graph_item)
                }
                R.id.action_invoice -> {
                    navController.navigate(R.id.nav_graph_invoice)
                }
                R.id.action_task -> {
                    navController.navigate(R.id.nav_graph_task)
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }


    /**
     * Se sobreescribe el método que gestiona al pulsación del botón
     */
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else //dejamos que el SO haga su función
            super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    companion object{
        const val CHANNEL_ID = "dummy channel"
    }
}