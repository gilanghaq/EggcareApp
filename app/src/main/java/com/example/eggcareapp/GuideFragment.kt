package com.example.eggcareapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eggcareapp.adapter.InfoNewsAdapter
import com.example.eggcareapp.adapter.NewsAdapter
import com.example.eggcareapp.databinding.FragmentGuideBinding
import com.example.eggcareapp.model.InfoNewsModel
import com.example.eggcareapp.model.NewsModel

class GuideFragment : Fragment() {
    private lateinit var binding: FragmentGuideBinding
    private lateinit var infoNewsList: ArrayList<InfoNewsModel>
    private lateinit var img101News: Array<Int>
    private lateinit var title101News: Array<String>
    private lateinit var newsList: ArrayList<NewsModel>
    private lateinit var imgNews: Array<Int>
    private lateinit var titleNews: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //101 guide
        img101News = arrayOf(
            R.drawable.img_guide_a,
            R.drawable.img_guide_b,
            R.drawable.img_guide_c,
            R.drawable.img_guide_d,
            R.drawable.img_guide_e
        )

        title101News = arrayOf(
            "Profitable Poultry: A Guide to Layer Chicken Farming",
            "Eggcellent Returns: A Comprehensive Layer Chicken Rearing Manual",
            "Feathered Fortune: The Ultimate Guide to Raising Layer Hens",
            "Eggs for Success: A Step-by-Step Layer Chicken Farming Handbook",
            "Flock Forward: A Practical Guide to Successful Layer Poultry Farming"
        )

        binding.eggCare101View.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.eggCare101View.setHasFixedSize(true)

        infoNewsList = arrayListOf()
        getInfoNewsData()

        //your cattle guide
        imgNews = arrayOf(
            R.drawable.img_guide_starter,
            R.drawable.img_guide_baterai
        )

        titleNews = arrayOf(
            "Starter",
            "Baterai"
        )

        binding.yourCattleView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.yourCattleView.setHasFixedSize(true)

        newsList = arrayListOf()
        getNewsData()
    }

    private fun getInfoNewsData() {
        for(i in img101News.indices) {
            val news = InfoNewsModel(img101News[i], title101News[i])
            infoNewsList.add(news)
        }

        binding.eggCare101View.adapter = InfoNewsAdapter(infoNewsList)
    }

    private fun getNewsData() {
        for(i in imgNews.indices) {
            val news = NewsModel(imgNews[i], titleNews[i])
            newsList.add(news)
        }

        binding.yourCattleView.adapter = NewsAdapter(newsList)
    }
}