package ht.ferit.fjjukic.rma_lv2_2.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ht.ferit.fjjukic.rma_lv2_2.adapters.InspiringPersonAdapter
import ht.ferit.fjjukic.rma_lv2_2.R
import ht.ferit.fjjukic.rma_lv2_2.fragments.EditInspiringPersonFragment
import ht.ferit.fjjukic.rma_lv2_2.fragments.InspiringPeopleFragment
import ht.ferit.fjjukic.rma_lv2_2.interfaces.FragmentListener
import ht.ferit.fjjukic.rma_lv2_2.interfaces.InspiringPeopleListener
import ht.ferit.fjjukic.rma_lv2_2.repository.PeopleRepository


class MainActivity : AppCompatActivity() {

    private lateinit var inspiringPeopleFragment: InspiringPeopleFragment
    private lateinit var inspiringPersonAdapter: InspiringPersonAdapter
    private lateinit var editInspiringPersonFragment: EditInspiringPersonFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setFragmentManager()
        }
    }

    private fun setFragmentManager() {
        this.inspiringPersonAdapter =
            InspiringPersonAdapter(
                this@MainActivity,
                createInspiringPeopleListener()
            )
        this.inspiringPeopleFragment =
            InspiringPeopleFragment(
                this.inspiringPersonAdapter,
                createFragmentListener()
            )
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, this.inspiringPeopleFragment)
            .commit()
    }

    private fun createInspiringPeopleListener(): InspiringPeopleListener {
        return object :
            InspiringPeopleListener {
            override fun onShowQuote(index: Int) {
                Toast.makeText(
                    applicationContext,
                    PeopleRepository.getQuote(index),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun checkRemoveItem(index: Int) {
                val alertDialog: AlertDialog =
                    AlertDialog.Builder(this@MainActivity).create()
                alertDialog.setTitle("Delete")
                alertDialog.setMessage("Are you sure you want to delete this item?")
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEGATIVE, "Cancel"
                ) { dialog, which -> dialog.dismiss() }
                alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE, "Erase"
                ) { dialog, which ->
                    PeopleRepository.removeInspiringPerson(index)
                    dialog.dismiss()
                    inspiringPersonAdapter.notifyDataSetChanged()
                }
                alertDialog.show()
            }

            override fun editItem(index: Int) {
                editInspiringPersonFragment =
                    EditInspiringPersonFragment(
                        index,
                        createFragmentListener()
                    )
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, editInspiringPersonFragment)
                    .addToBackStack("FragmentTwo")
                    .commit()
            }
        }
    }

    private fun createFragmentListener(): FragmentListener {
        return object :
            FragmentListener {
            override fun backAction() {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, inspiringPeopleFragment)
                    .commit()
            }

            override fun addNewItem() {
                editInspiringPersonFragment =
                    EditInspiringPersonFragment(
                        -1,
                        createFragmentListener()
                    )
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, editInspiringPersonFragment)
                    .addToBackStack("FragmentTwo")
                    .commit()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
