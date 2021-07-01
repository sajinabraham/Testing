package `in`.sajin.tab.view

import `in`.sajin.tab.R
import `in`.sajin.tab.databinding.ActivityProfileBinding
import `in`.sajin.tab.model.User
import `in`.sajin.tab.room.MyAdapter
import `in`.sajin.tab.room.MyViewModel
import `in`.sajin.tab.viewmodel.UserViewModel
import `in`.sajin.tab.viewmodel.factory.UserViewModelFactory
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ProfileActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context, user: User) {
            val starter = Intent(context, ProfileActivity::class.java)
            starter.putExtra("USER_OBJ", user)
            context.startActivity(starter)
        }
    }

    private val adapter by lazy { MyAdapter() }
    private lateinit var myViewModel: MyViewModel
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        //assert(supportActionBar != null)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        val user: User? = intent.getSerializableExtra("USER_OBJ") as User?

        /**
         * Create instance for data binding auto generated class file
         */
        val binding: ActivityProfileBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_profile)


        recyclerView = findViewById(R.id.recyclerview)

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        myViewModel.readPerson.observe(this, {
            adapter.setData(it)
        })
        /**
         * Create instance for ViewModel class
         */
        val userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(this, user!!)
        ).get(UserViewModel::class.java)


        /**
         * Set ViewModel instance to binding class
         */
        binding.userViewModel = userViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, CameraActivity::class.java)

        when (item.itemId) {

            R.id.more_info -> MoreInfoActivity.start(this)
            R.id.camera -> startActivity(intent)
            //R.id.about -> AboutActivity.start(this)
            R.id.about -> AboutActivity.start(this)
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }
}