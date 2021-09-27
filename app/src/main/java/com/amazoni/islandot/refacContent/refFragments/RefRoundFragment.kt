package com.amazoni.islandot.refacContent.refFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import androidx.fragment.app.viewModels
import com.amazoni.islandot.R
import com.amazoni.islandot.databinding.FragmentRefRoundBinding
import com.amazoni.islandot.refacContent.refFragments.viewModel.RoundViewModel
import kotlin.math.floor
import kotlin.random.Random


class RefRoundFragment : Fragment(), Animation.AnimationListener {

    private var refRoundBinding: FragmentRefRoundBinding? = null
    private val roundBinding get() = refRoundBinding!!
    private val refRoundTag = "RefRoundFragment"
    private var refSel: Long = 0
    private lateinit var refList: Array<String>
    private val roundViewModel: RoundViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refList = resources.getStringArray(R.array.refItem)



        roundBinding.spin.setOnClickListener {
            randomRound()
            if((1..4).random() == 2){
                replaceFragment(RefTableFragment())
            }
        }

        roundViewModel.controller.observe(viewLifecycleOwner, {
            roundBinding.controllerTxt.text = it.toString()
        })

        roundBinding.plus.setOnClickListener {
            roundViewModel.addController()
        }

        roundBinding.minus.setOnClickListener {
            roundViewModel.minusController()
        }

        roundViewModel.bet.observe(viewLifecycleOwner, {
            roundBinding.betTxt.text = it.toString()
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        refRoundBinding = FragmentRefRoundBinding.inflate(inflater, container, false)
        return roundBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        roundViewModel.zeroFields()
        refRoundBinding = null
    }

    override fun onAnimationStart(animation: Animation?) {

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.refac_container, fragment)
        transaction.commit()
    }

    override fun onAnimationEnd(animation: Animation?) {
        val place =
            ((refList.size).toDouble() - floor(refSel.toDouble() / (360.0 / (refList.size).toDouble()))).toInt()

        Log.d(refRoundTag, "Place: $place")


        when (place) {
            5, 8, 11 -> {
                roundViewModel.addBet((5..12).random())
            }
            1, 4, 7 -> {
                roundViewModel.addBet((1..7).random())
            }
            else -> {
                roundViewModel.addBet(-1)
            }
        }
    }

    override fun onAnimationRepeat(animation: Animation?) {

    }

    private fun randomRound() {
        val random = Random.nextInt(360) + 3600
        val rotation: RotateAnimation = RotateAnimation(
            refSel.toFloat(),
            (refSel + random.toLong()).toFloat(),
            1,
            0.5f,
            1,
            0.5f
        )
        refSel = (refSel + random.toLong()) % 360

        rotation.duration = random.toLong()
        rotation.fillAfter = true
        rotation.interpolator = DecelerateInterpolator()
        rotation.setAnimationListener(this)
        roundBinding.numbers.animation = rotation
        roundBinding.numbers.startAnimation(rotation)
    }

}