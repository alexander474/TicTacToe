package no.ab.tictactoev02.Fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import no.ab.tictactoev02.Adapter.UsersAdapter
import no.ab.tictactoev02.R
import no.ab.tictactoev02.ViewModel.UserViewModel

class HighScoreFragment : FragmentHandler() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_highscore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.highscoore_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val userModel = UserViewModel(activity!!.application)


        // Appends all the users in the UsersAdapter to the recyclerView
        userModel.allUsersLive.observe(this, Observer { liveData ->
            liveData?.let { data ->
                recyclerView.adapter = UsersAdapter(data.toCollection(ArrayList()))
            }
        })

    }

}