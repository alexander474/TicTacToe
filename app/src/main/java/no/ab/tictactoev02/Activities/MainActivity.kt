package no.ab.tictactoev02.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import no.ab.tictactoev02.Fragments.StartPageFragment
import no.ab.tictactoev02.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment()
    }


    /**
     * Initial load of fragment
     */
    private fun loadFragment(){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, StartPageFragment(), null)
            .commit()
    }


}
