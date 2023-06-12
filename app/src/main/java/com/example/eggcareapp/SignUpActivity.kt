package com.example.eggcareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat.RegisterReceiverFlags
import com.example.eggcareapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_EggcareApp)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.signInTv.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.signUpButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            //validasi email
            if (email.isEmpty()){
                binding.email.error = "Email cannot be empty"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            //validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.email.error = "Invalid email"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            //validasi password
            if (password.isEmpty()){
                binding.password.error = "Password cannot be empty"
                binding.password.requestFocus()
                return@setOnClickListener
            }

            //validasi panjang password
            if (password.length < 8){
                binding.password.error = "Minimum 8 characters"
                binding.password.requestFocus()
                return@setOnClickListener
            }
            
            RegisterFirebase(email, password)

            val currentUser = auth.currentUser
            if (currentUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun RegisterFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}