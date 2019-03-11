package no.ab.tictactoev02

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import android.support.v4.app.Fragment
import no.ab.tictactoev02.IO.UserEntity
import no.ab.tictactoev02.IO.UserModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(R.id.fragment_container, StartPageFragment())

    }

    fun replaceFragment(fragmentId: Int, fragment: Fragment) {
        val fm = supportFragmentManager
        val fragmentTransaction = fm?.beginTransaction()
        fragmentTransaction?.replace(fragmentId, fragment)
        fragmentTransaction?.commit()
    }


}
