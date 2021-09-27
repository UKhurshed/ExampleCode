package com.amazoni.islandot.refacContent.refFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.amazoni.islandot.R
import com.amazoni.islandot.databinding.FragmentRefStartBinding


class RefStartFragment : Fragment() {

    private var refStartBinding: FragmentRefStartBinding? = null
    private val startBinding get() = refStartBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pulsedButton()

        startBinding.bonusGame.setOnClickListener {
            replaceFragment(RefRoundFragment())
        }

        startBinding.playing.setOnClickListener {
            replaceFragment(RefTableFragment())
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.refac_container, fragment)
        transaction.commit()
    }

    private fun pulsedButton() {
        YoYo.with(Techniques.Pulse)
            .duration(5000)
            .repeat(20)
            .playOn(startBinding.bonusGame)
//        pulsedButton()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        refStartBinding = FragmentRefStartBinding.inflate(inflater, container, false)
        return startBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        refStartBinding = null
    }

}