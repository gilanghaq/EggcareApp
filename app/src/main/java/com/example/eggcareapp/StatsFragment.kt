package com.example.eggcareapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eggcareapp.databinding.FragmentStatsBinding
import com.google.firebase.auth.FirebaseAuth

class StatsFragment : Fragment(R.layout.fragment_stats) {

    private lateinit var binding: FragmentStatsBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            barChart.animation.duration = animationDuration
            barChart.animate(barSet)

            lineChart.gradientFillColors = intArrayOf(
                Color.parseColor("#FFCC29"),
                Color.TRANSPARENT
            )
            lineChart.animation.duration = animationDuration
            lineChart.animate(lineSet)

            binding.apply {
                donutChart.donutColors = intArrayOf(
                    Color.parseColor("#027E4A")
                )
                donutChart.animation.duration = animationDuration
                donutChart.animate(donutSet)
            }

            binding.apply {
                donutChart2.donutColors = intArrayOf(
                    Color.parseColor("#5DDDF9")
                )
                donutChart2.animation.duration = animationDuration
                donutChart2.animate(donutSet2)
            }
        }
    }

    companion object {
        private val barSet = listOf(
            "Starter" to 12F,
            "Grower" to 8F,
            "Layer" to 8F,
            "Baterai" to 4F,
            "Semi-Baterai" to 1F
        )

        private val lineSet = listOf(
            "Week 1" to 0F,
            "Week 2" to 80F,
            "Week 3" to 120F,
            "Week 4" to 160F
        )

        private val donutSet = listOf(
            80F
        )

        private val donutSet2 = listOf(
            90F
        )

        private const val animationDuration = 1000L
    }
}