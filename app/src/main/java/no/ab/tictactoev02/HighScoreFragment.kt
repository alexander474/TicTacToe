package no.ab.tictactoev02

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_start_page.*
import kotlinx.coroutines.awaitAll
import no.ab.tictactoev02.IO.UserEntity
import no.ab.tictactoev02.IO.UserModel

class HighScoreFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_highscore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userModel = UserModel(activity!!.application)

        val users: ArrayList<UserEntity> = ArrayList()


        //userModel.getAllUsers(users)


        userModel.allUsersLive.observe(this, Observer { value ->
            value?.forEach { users.add(it) }
        })



        recyclerView = view.findViewById(R.id.highscoore_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = UsersAdapter(users)


    }

}