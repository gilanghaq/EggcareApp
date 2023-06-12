package com.example.eggcareapp

import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.eggcareapp.databinding.ActivityAddNewBinding
import com.example.eggcareapp.model.CattleModel
import com.example.eggcareapp.service.AlarmService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var alarmService: AlarmService
    private lateinit var imageUri: Uri

    companion object {
        private const val REQUEST_CAMERA = 27
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddNewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        alarmService = AlarmService(this)

        dbRef = FirebaseDatabase.getInstance().getReference("Cattles")

        binding.saveButton.setOnClickListener {
            saveCattleData()
        }

        //set alarm
        binding.feedTime.setOnClickListener {
            setFeedTime { alarmService.setRepetitiveAlarm(it) }
        }
        binding.cleanTime.setOnClickListener {
            setCleanTime { alarmService.setRepetitiveAlarm(it) }
        }

        //take a picture
        binding.addImage.setOnClickListener {
            intentCamera()
        }

        //enable back arrow in action bar
        supportActionBar!!.apply {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
        //change action bar color
        supportActionBar!!.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
        )
        //change text in action bar
        supportActionBar!!.title = "Add New Cattle"

        //Spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.cage_type,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spType.adapter = adapter
    }

    private fun setFeedTime(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)

            TimePickerDialog(
                this@AddNewActivity,
                0,
                { _, hour, min ->
                    this.set(Calendar.HOUR_OF_DAY, hour)
                    this.set(Calendar.MINUTE, min)
                    callback(this.timeInMillis)
                    val feedTimeText =
                        SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                            this.time
                        )
                    binding.feedTime.text = feedTimeText
                },
                this.get(Calendar.HOUR_OF_DAY),
                this.get(Calendar.MINUTE),
                false
            ).show()
        }
    }

    private fun setCleanTime(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)

            TimePickerDialog(
                this@AddNewActivity,
                0,
                { _, hour, min ->
                    this.set(Calendar.HOUR_OF_DAY, hour)
                    this.set(Calendar.MINUTE, min)
                    callback(this.timeInMillis)
                    val cleanTimeText =
                        SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                            this.time
                        )
                    binding.cleanTime.text = cleanTimeText
                },
                this.get(Calendar.HOUR_OF_DAY),
                this.get(Calendar.MINUTE),
                false
            ).show()
        }
    }


    private fun intentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager).also {
                startActivityForResult(intent, REQUEST_CAMERA)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val timestamp = System.currentTimeMillis()
        val fileName = "MyCattle_$timestamp.jpg"
        val ref = FirebaseStorage.getInstance().getReference()
            .child("MyCattles/${FirebaseAuth.getInstance().currentUser?.uid}/$fileName")

        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        ref.putBytes(image)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener {
                        it.result?.let {
                            imageUri = it
                            binding.addImage.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

    private fun saveCattleData() {
        val fillName = binding.fillName.text.toString()
        val fillAmount = binding.fillAmount.text.toString()
        val fillAge = binding.fillAge.text.toString()
        val cageType = binding.spType.selectedItem.toString()
        val feedTime = binding.feedTime.text.toString()
        val cleanTime = binding.cleanTime.text.toString()

        if (fillName.isNotEmpty() && fillAmount.isNotEmpty() && fillAge.isNotEmpty() && cageType.isNotEmpty() && feedTime.isNotEmpty() && feedTime.isNotEmpty() && cleanTime.isNotEmpty()) {
            val cattleId = dbRef.push().key
            val cattle = CattleModel(
                cattleId = cattleId,
                fillName = fillName,
                fillAmount = fillAmount,
                fillAge = fillAge,
                cageType = cageType,
                imageUrl = imageUri.toString(),
                feedTime = feedTime,
                cleanTime = cleanTime
            )

            if (cattleId != null) {
                dbRef.child(cattleId).setValue(cattle)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Cattle added successfully", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        } else {
                            Toast.makeText(this, "Failed to add cattle", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        } else {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }
}
