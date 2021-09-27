package com.amazoni.islandot.refacContent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.amazoni.islandot.R
import com.amazoni.islandot.databinding.ActivityRefacContentBinding
import com.amazoni.islandot.refacContent.refFragments.RefStartFragment
import kotlin.system.exitProcess

class RefacContent : AppCompatActivity() {

    private lateinit var refacContentBinding: ActivityRefacContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refacContentBinding = ActivityRefacContentBinding.inflate(layoutInflater)
        setContentView(refacContentBinding.root)
        replaceFragment(RefStartFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.refac_container, fragment)
        transaction.commit()
    }


    override fun onBackPressed() {
//        Log.d(boxTag, "BackBoolean: ${boxPref.boxPressed}")
//        if (boxPref.boxPressed) {
//            replaceFragment(BoxStartFragment())
//            boxPref.boxPressed = false
//        } else {
        val publicBuilder = AlertDialog.Builder(this)
        publicBuilder.setTitle("Confirmation")
            .setMessage("Do you want exit app content")
            .setPositiveButton("Yes") { _, _ ->
                finishAffinity()
                exitProcess(0)

            }
            .setNegativeButton("No") { _, _ ->
            }
        val dialog: AlertDialog = publicBuilder.create()
        dialog.show()
//            boxPref.boxPressed = false
    }
//    }
}