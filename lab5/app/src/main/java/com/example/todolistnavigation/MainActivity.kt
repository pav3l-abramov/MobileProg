package com.example.todolistnavigation

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.todolistnavigation.adapter.AffairsAdapter
import com.example.todolistnavigation.databinding.ActivityMainBinding
import com.example.todolistnavigation.model.AffairsModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: AffairsAdapter
    val affairsList: MutableList<AffairsModel> = ArrayList<AffairsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AffairsAdapter(this@MainActivity)

        val task1 = AffairsModel()
        task1.id = 1
        task1.affairs = "somebody"
        task1.status = false
        val task2 = AffairsModel()
        task2.id = 2
        task2.affairs = "once"
        task2.status = false
        val task3 = AffairsModel()
        task3.id = 3
        task3.affairs = "told"
        task3.status = false
        val task4 = AffairsModel()
        task4.id = 4
        task4.affairs = "me"
        task4.status = false
        val task5 = AffairsModel()
        task5.id = 5
        task5.affairs = "the"
        task5.status = false
        val task6 = AffairsModel()
        task6.id = 6
        task6.affairs = "world"
        task6.status = false
        val task7 = AffairsModel()
        task7.id = 7
        task7.affairs = "is"
        task7.status = false
        val task8 = AffairsModel()
        task8.id = 8
        task8.affairs = "gonna"
        task8.status = false
        val task9 = AffairsModel()
        task9.id = 9
        task9.affairs = "roll"
        task9.status = false
        val task10 = AffairsModel()
        task10.id = 10
        task10.affairs = "me"
        task10.status = false
        val task11 = AffairsModel()
        task11.id = 11
        task11.affairs = "I"
        task11.status = false
        val task12 = AffairsModel()
        task12.id = 12
        task12.affairs = "ain't"
        task12.status = false
        val task13 = AffairsModel()
        task13.id = 13
        task13.affairs = "the"
        task13.status = false

        affairsList.add(task1)
        affairsList.add(task2)
        affairsList.add(task3)
        affairsList.add(task4)
        affairsList.add(task5)
        affairsList.add(task6)
        affairsList.add(task7)
        affairsList.add(task8)
        affairsList.add(task9)
        affairsList.add(task10)
        affairsList.add(task11)
        affairsList.add(task12)
        affairsList.add(task13)

        adapter.setList(affairsList)
        adapter.notifyDataSetChanged()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


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