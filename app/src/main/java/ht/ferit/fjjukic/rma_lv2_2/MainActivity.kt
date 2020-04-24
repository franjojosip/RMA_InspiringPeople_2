package ht.ferit.fjjukic.rma_lv2_2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ht.ferit.fjjukic.rma_lv2_2.repository.PeopleRepository


class MainActivity : AppCompatActivity() {

    private lateinit var inspiringPeopleFragment: InspiringPeopleFragment
    private lateinit var inspiringPersonAdapter: InspiringPersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragmentManager()
    }

    private fun setFragmentManager() {
        inspiringPersonAdapter = InspiringPersonAdapter(
            this@MainActivity,
            createInspiringPeopleListener())
        inspiringPeopleFragment = InspiringPeopleFragment(inspiringPersonAdapter)
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, inspiringPeopleFragment)
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
                val alertDialog =
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
                TODO("Not yet implemented")
            }
        }
    }
}
