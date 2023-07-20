package com.example.gecp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gecp.daos.PostDao
import com.example.gecp.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity(), IPostAdapter {

   private lateinit var adapter: PostAdapter
    private lateinit var postDao: PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null) {
            val editText = findViewById<EditText>(R.id.numberInput)
            val editTextValue = savedInstanceState.getString("editTextValue")
            val dta = editText.setText(editTextValue)

        }
val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }

setUpRecyclerView()
    }
    private fun setUpRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        postDao = PostDao()
        val postsCollections = postDao.postCollections
        val query = postsCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
    fun clickChat(view: View){
//        val editText = findViewById<EditText>(R.id.numberInput)
//        val lastdta = editText.text.toString()
//        val receivedData1 = intent.getStringExtra("numData")
////        var functionVariable1 = receivedData1
//        Toast.makeText(this, "number is $lastdta", Toast.LENGTH_SHORT).show()
        val chat = findViewById<Button>(R.id.Chatbutton)
        val uri: Uri = Uri.parse("http://wa.me/917999779347")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}