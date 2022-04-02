package com.example.parstagram.fragments

import android.util.Log
import com.example.parstagram.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment : FeedFragment() {

    override fun queryPosts(){
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        //Find all the post objects in our server
        query.include(Post.KEY_USER) //includes the user associated with the post
        // Only returns te post of the signed in user
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.addDescendingOrder("createdAt") //returns posts in descending order of the created date

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
}