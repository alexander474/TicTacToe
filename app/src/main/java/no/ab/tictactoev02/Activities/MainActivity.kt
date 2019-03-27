package no.ab.tictactoev02.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import no.ab.tictactoev02.Fragments.StartPageFragment
import no.ab.tictactoev02.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()
        setContentView(R.layout.activity_main)
        loadFragment()
    }

    /**
     * Creates the menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Adds logic to the menu select
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_theme_default -> {
                changeToNewTheme(getString(R.string.default_theme))
                return true
            }
            R.id.menu_theme_dark -> {
                changeToNewTheme(getString(R.string.dark_theme))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Sets a new theme and alerts the user with a snackbar that restart is required to apply the new theme
     * The snackbar has a TTL at 5000ms (5seconds)
     */
    private fun changeToNewTheme(name: String){
        setThemePreferences(name)

        //Displays a snackbar to alert user about restart
        Snackbar.make(fragment_container, "Needs to restart for changes to apply", Snackbar.LENGTH_LONG).apply {
            this.setAction("RESTART"){
                restart()
            }.setDuration(5000).show()
        }
    }


    /**
     * Sets the sharedpreferences property of the theme_id to the new theme name and restarts the activity to apply changes
     */
    private fun setThemePreferences(name: String) {
        val editor = getSharedPreferences(getString(R.string.theme_preferences_id), Context.MODE_PRIVATE).edit()
        editor.putString(getString(R.string.theme_id), name)
        editor.apply()
    }

    /**
     * Gets the current theme saved in shared preferences and apply the theme that is currently saved
     */
    private fun setTheme(){
        val pref = getSharedPreferences(getString(R.string.theme_preferences_id), Context.MODE_PRIVATE)
        val themeID = getString(R.string.theme_id)

        // Gets the saved themeID if no value is found use default value from strings
        when(pref.getString(themeID, getString(R.string.default_theme))){
            getString(R.string.default_theme) -> setTheme(R.style.DefaultTheme)
            getString(R.string.dark_theme) -> setTheme(R.style.DarkTheme)
        }
    }

    /**
     * Restarts the activity
     */
    private fun restart(){
        Handler().post {
            val intent = intent
            intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_NO_ANIMATION
            )
            finish()
            startActivity(intent)
        }
    }


    /**
     * Initial load of fragment when the activity starts
     */
    private fun loadFragment(){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, StartPageFragment(), null)
            .commit()
    }


}
