package no.ab.tictactoev02

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import no.ab.tictactoev02.IO.UserEntity

class UsersAdapter(val users: ArrayList<UserEntity>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.highscore_row, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = "("+users[position].id+") "+users[position].name
        holder.win.text = users[position].win.toString()
        holder.draw.text = users[position].draw.toString()
        holder.loose.text = users[position].loose.toString()

    }

    override fun getItemCount() = users.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.highscore_row_name)
        val win: TextView = itemView.findViewById(R.id.highscore_row_win)
        val draw: TextView = itemView.findViewById(R.id.highscore_row_draw)
        val loose: TextView = itemView.findViewById(R.id.highscore_row_loose)

    }
}