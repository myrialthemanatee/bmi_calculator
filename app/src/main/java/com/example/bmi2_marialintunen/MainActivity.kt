package com.example.bmi2_marialintunen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.preference.PreferenceManager
import com.example.bmi2_marialintunen.databinding.ActivityMainBinding

var bmi_lista = "";

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val preferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )

        findViewById<Button>(R.id.calculateButton).setOnClickListener {

            var uheight = preferences.getString("height_variable", "").toString().toDouble()
            val uweight = findViewById<EditText>(R.id.weight).text.toString().toDouble()
            uheight = uheight / 100
            val bmi = (uweight / (uheight * uheight))
            findViewById<TextView>(R.id.bmi).text = "Your BMI is: " + "%.2f".format(bmi).toString()

            findViewById<EditText>(R.id.height).setText(preferences.getString("height_variable", ""))

            bmi_lista += (uweight / (uheight * uheight)).toString();
            bmi_lista += '\n'
            findViewById<TextView>(R.id.tuloslista).setText(bmi_lista);
        }



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

        val intent = Intent(this, SettingsActivity::class.java)

        startActivity(intent)

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}