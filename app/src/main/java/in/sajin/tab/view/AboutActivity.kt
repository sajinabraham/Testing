package `in`.sajin.tab.view

import `in`.sajin.tab.R
import `in`.sajin.tab.room.MyAdapter
import `in`.sajin.tab.room.MyViewModel
import `in`.sajin.tab.room.Person
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.launch

class AboutActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AboutActivity::class.java)
            context.startActivity(starter)
        }
    }
    private val adapter by lazy { MyAdapter() }
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        recyclerView = findViewById(R.id.recyclerview)

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)

//        lifecycleScope.launch {
//            val person = Person("Sajin", "Abraham", getBitmap())
//            Log.e("save","save"+getBitmap())
//            myViewModel.insertPerson(person)
//        }

        myViewModel.readPerson.observe(this, {
            adapter.setData(it)
        })

    }

}