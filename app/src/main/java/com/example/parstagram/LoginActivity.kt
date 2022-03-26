package com.example.parstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseUser


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Simply check the parsed object is send to Back4app server
        /*
        val firstObject = ParseObject("FirstClass")
        firstObject.put("message","Hey ! First message from android. Parse is now connected")
        firstObject.saveInBackground {
            if (it != null){
                it.localizedMessage?.let { message -> Log.e("LoginActivity", message) }
            }else{
                Log.d("LoginActivity","Object saved.")
            }
        }
         */

        //check if the user is already logged in
        //If there is then take it to the MainActivity
        if (ParseUser.getCurrentUser() != null){
            goToMainActivity()
        }

        findViewById<Button>(R.id.loginbtn).setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R .id.password).text.toString()
            loginUser(username, password)
        }

        findViewById<Button>(R.id.signupbtn).setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R .id.password).text.toString()
            signUpUser(username, password)
        }
    }

    private fun signUpUser(username: String, password: String) {
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                //User has successfully created the account

                goToMainActivity()
                Toast.makeText(this, "Signed Up Successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Signed Up Unsuccessful. Please try again", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        //makes the call in the background
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log .i(TAG, "Successfully Logged-in")
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error Loggging in", Toast.LENGTH_SHORT).show()
            }})
        )
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()   //closes the login activity, avoiding going back to login page on clicking back
    }

    private
    companion object{
        val TAG = "LoginActivity"
    }
}