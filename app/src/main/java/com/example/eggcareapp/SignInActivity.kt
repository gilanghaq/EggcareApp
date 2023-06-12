package com.example.eggcareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.eggcareapp.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySigninBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySigninBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.signUpTv.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.signInButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            //validasi email
            if (email.isEmpty()) {
                binding.email.error = "Email cannot be empty"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            //validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.email.error = "Invalid email"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            //validasi password
            if (password.isEmpty()) {
                binding.password.error = "Password cannot be empty"
                binding.password.requestFocus()
                return@setOnClickListener
            }

            //validasi panjang password
            if (password.length < 8) {
                binding.password.error = "Minimum 8 characters"
                binding.password.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email, password)
        }
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
