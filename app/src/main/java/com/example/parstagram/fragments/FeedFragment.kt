package com.example.parstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parstagram.Post
import com.example.parstagram.PostAdapter
import com.example.parstagram.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

open class FeedFragment : Fragment() {

    lateinit var postRecyclerView: RecyclerView
    lateinit var adapter: PostAdapter

    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This where we setup are views and click listeners

        postRecyclerView = view. findViewById(R.id.postRecyclerView)
        adapter = PostAdapter(requireContext(), allPosts)
        postRecyclerView.adapter = adapter

        postRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()

    }

    // Query for all post in our server
    open fun queryPosts() {
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        //Find all the post objects in our server
        query.include(Post.KEY_USER) //includes the user associated with the post
        query.addDescendingOrder("createdAt")

        //only return the most recent 20 posts
        query.setLimit(20)
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null){
                    // Something is went wrong
                    Log.e(TAG, "Error fetching posts")
                }else{
                    if (posts != null){
                        for (post in posts){
                            Log.i(TAG, "Post: "+post.getDescription()+ " , username: "+post.getUser()?.username)
                        }
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }
    companion object{
        const val TAG = "FeedFragment"
    }
}