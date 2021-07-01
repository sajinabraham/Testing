package `in`.sajin.tab.room

import `in`.sajin.tab.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    private var person = emptyList<Person>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return person.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val fullname = holder.itemView.findViewById<TextView>(R.id.fullName_txt)
        val year_of_birth = holder.itemView.findViewById<TextView>(R.id.etYear)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageView)
        fullname.text = person[position].firstName +person[position].lastName
        year_of_birth.text = person[position].year_of_birth
        imageView.load(person[position].profilePhoto)
    }

    fun setData(person: List<Person>){
        this.person = person
        notifyDataSetChanged()
    }
}