package com.example.eggcareapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eggcareapp.adapter.CattleAdapter
import com.example.eggcareapp.databinding.FragmentHomeBinding
import com.example.eggcareapp.model.CattleModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var cattleList: ArrayList<CattleModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = auth.currentUser
        val email = user?.email
        binding.tvYourName.text = email

        binding.addButton.setOnClickListener {
            val intent = Intent(activity, AddNewActivity::class.java)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        // Initialize cattle list
        cattleList = arrayListOf()

        // Fetch cattle data
        getCattleData()
    }

    private fun getCattleData() {
        binding.recyclerView.visibility = View.GONE
        binding.imgEmpty.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Cattles")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cattleList.clear()
                if (snapshot.exists()) {
                    for (cattleSnap in snapshot.children) {
                        val cattleData = cattleSnap.getValue(CattleModel::class.java)
                        cattleList.add(cattleData!!)
                    }
                    // Initialize adapter
                    val mAdapter = CattleAdapter(cattleList)
                    binding.recyclerView.adapter = mAdapter

                    // Add click listener on item
                    mAdapter.setOnItemClickListener(object : CattleAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireContext(), CattleDetailActivity::class.java)
                            intent.putExtra("name", cattleList[position].fillName)
                            intent.putExtra("amount", "${cattleList[position].fillAmount} Chicken")
                            intent.putExtra("age", "${cattleList[position].fillAge} Week")
                            intent.putExtra("type", cattleList[position].cageType)
                            intent.putExtra("image", cattleList[position].imageUrl)
                            intent.putExtra("feedtime", "Every ${cattleList[position].feedTime}")
                            intent.putExtra("cleantime", "Every ${cattleList[position].cleanTime}")
                            startActivity(intent)
                        }
                    })

                    binding.recyclerView.visibility = View.VISIBLE
                    binding.imgEmpty.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
