package com.ganzory.wagba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
    }

    override fun onResume() {
        super.onResume()

        // If a user is logged in go to home.
        Timer().schedule(500) {
            val currentUser = auth.currentUser
            val nextActivity =
                if (currentUser == null) LoginActivity::class.java else HomeActivity::class.java
            startActivity(Intent(this@MainActivity, nextActivity))
            finish();
        }
    }
}