package com.amazoni.islandot.refacContent.refFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.amazoni.islandot.R.drawable.*
import com.amazoni.islandot.databinding.FragmentRefTableBinding
import com.amazoni.islandot.refacContent.refFragments.viewModel.RoundViewModel


class RefTableFragment : Fragment() {

    private var refTableBinding: FragmentRefTableBinding? = null
    private val tableBinding get() = refTableBinding!!
    private lateinit var topElements: List<ImageView>
    private lateinit var lastElements: List<ImageView>
    private val roundViewModel: RoundViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roundViewModel.mini.observe(viewLifecycleOwner, {
            tableBinding.smallFieldTxt.text = it.toString()
        })

        roundViewModel.total.observe(viewLifecycleOwner, {
            tableBinding.bigFieldTxt.text = it.toString()
        })

        val topImages = mutableListOf(
            k_first_gold,
            k_first_gold,
            a_gold,
            q_golden
        )

        topElements = listOf(
            tableBinding.firstTop,
            tableBinding.secondTop,
            tableBinding.thirdTop,
            tableBinding.firstTop,
        )

        topImages.shuffle()

        val goldImages = mutableListOf(
            gold_parrot,
            gold_parrot,
            a_gold,
            a_gold,
            k_first_gold,
            bid_parrot_gold,
            pantera_gold,
            nine_gold,
            q_golden,
            q_golden,
            a_big_gold,
            ten_gold,
            ten_gold,
            monkey_gold,
            gold_jet,
            gold_soun,
            human
        )

        val lastImages = mutableListOf(
            q_first,
            q_first,
            ten_first,
            ten_first,
            nine,
            monkey,
            big_parrot,
            first_parrot,
            human,
            first_parrot,
            pantera,
            soun,
            a_big,
            a_first,
            a_first,
            jey,
            k_first,
        )

        lastImages.shuffle()
        goldImages.shuffle()

        lastElements = listOf(
            tableBinding.first,
            tableBinding.second,
            tableBinding.third,
            tableBinding.fourth,
            tableBinding.fifth,
            tableBinding.sixth,
            tableBinding.seventh,
            tableBinding.eighth,
            tableBinding.ninth,
            tableBinding.tenth,
            tableBinding.eleventh,
            tableBinding.twelveth,
            tableBinding.thirteenth,
            tableBinding.fourteenth,
            tableBinding.fifteenth,
            tableBinding.sixteenth,
            tableBinding.seventeenth,
        )


        tableBinding.spin.setOnClickListener {
            spunImages(goldImages)
        }

        tableBinding.smallSpin.setOnClickListener {
            spinImages(topImages)
        }


    }

    private fun spinImages(image: MutableList<Int>) {
        image.shuffle()
        topElements.forEachIndexed { index, imageView ->
            imageView.setImageResource(image[index])
        }

        roundViewModel.addMini((1..9).random())
    }

    private fun spunImages(image: MutableList<Int>) {
        image.shuffle()
        lastElements.forEachIndexed { index, imageButton ->
//            YoYo.with(Techniques.Pulse)
//                .duration(100)
//                .playOn(imageButton)
            imageButton.setImageResource(image[index])
        }

        roundViewModel.addTotal((1..20).random())

//        firstChunk(image)
//        secondChunk()
//        thirdChunk()
    }

    private fun firstChunk(image: MutableList<Int>) {
        image.forEach {
            when (it) {
                q_first -> {

                }
            }
        }
//        when(image){
//            q_first -> {
//
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        refTableBinding = FragmentRefTableBinding.inflate(inflater, container, false)
        return tableBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        roundViewModel.zeroFields()
        refTableBinding = null
    }

}