package ht.ferit.fjjukic.rma_lv2_2.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import ht.ferit.fjjukic.rma_lv2_2.interfaces.FragmentListener
import ht.ferit.fjjukic.rma_lv2_2.models.InspiringPerson
import ht.ferit.fjjukic.rma_lv2_2.R
import ht.ferit.fjjukic.rma_lv2_2.repository.CodeRepository
import ht.ferit.fjjukic.rma_lv2_2.repository.PeopleRepository
import java.time.LocalDate
import java.util.*


class EditInspiringPersonFragment(
    private val itemId: Int,
    private var fragmentListener: FragmentListener
) : Fragment() {
    private lateinit var fragmentTitle: TextView
    private lateinit var dateOfBirthField: TextView
    private lateinit var shortDescriptionField: EditText
    private lateinit var quoteOneField: EditText
    private lateinit var quoteTwoField: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var btnChooseImage: Button
    private var imagePath: String = ""
    private lateinit var dateOfBirth: LocalDate
    private lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.edit_inspiring_person, container, false)

        this.fragmentTitle = view.findViewById(R.id.title)
        this.dateOfBirthField = view.findViewById(R.id.tvEnterDate)
        this.shortDescriptionField = view.findViewById(R.id.etDescription)
        this.quoteOneField = view.findViewById(R.id.etQuote1)
        this.quoteTwoField = view.findViewById(R.id.etQuote2)
        this.btnSave = view.findViewById(R.id.btnSave)
        this.btnCancel = view.findViewById(R.id.btnCancel)
        this.btnChooseImage = view.findViewById(R.id.btnChooseImage)
        this.calendar = Calendar.getInstance()

        if (this.itemId != -1) {

            val inspiringPerson: InspiringPerson? = PeopleRepository.findInspiringPerson(itemId)
            when {
                inspiringPerson != null -> {
                    this.fragmentTitle.text = getString(R.string.edit_inspiring_person_title)
                    this.dateOfBirthField.text = inspiringPerson.dateOfBirth.toString()
                    this.shortDescriptionField.setText(inspiringPerson.shortDescription)
                    when (inspiringPerson.quotes.size) {
                        1 -> {
                            this.quoteOneField.setText(inspiringPerson.quotes[0])
                        }
                        2 -> {
                            this.quoteOneField.setText(inspiringPerson.quotes[0])
                            this.quoteTwoField.setText(inspiringPerson.quotes[1])
                        }
                    }
                    this.imagePath = inspiringPerson.imagePath
                    this.dateOfBirth = inspiringPerson.dateOfBirth
                }
            }
        }
        setOnClickListener()

        return view
    }

    private fun setOnClickListener() {
        this.btnSave.setOnClickListener {
            when {
                this.dateOfBirthField.text != "--/--/----" && this.shortDescriptionField.text.isNotEmpty() && this.quoteOneField.text.isNotEmpty() && this.quoteTwoField.text.isNotEmpty() && this.imagePath.isNotEmpty() -> {
                    val person: InspiringPerson = createInspiringPerson()
                    PeopleRepository.addInspiringPerson(person, this.itemId)
                    fragmentListener.backAction()
                }
                else -> Toast.makeText(
                    activity!!,
                    "Some fields are missing, please check it.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        this.btnCancel.setOnClickListener {
            this.fragmentListener.backAction()
        }

        this.btnChooseImage.setOnClickListener {
            if (checkSelfPermission(
                    activity!!,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) ==
                PackageManager.PERMISSION_DENIED
            ) {
                val permissions: Array<String> =
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, CodeRepository.PERMISSION_CODE)
            } else {
                chooseImageFromGallery()
            }
        }

        this.dateOfBirthField.setOnClickListener {

            val year: Int = this.calendar.get(Calendar.YEAR)
            val month: Int = this.calendar.get(Calendar.MONTH)
            val day: Int = this.calendar.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                activity!!,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val newDate: LocalDate = LocalDate.of(year, month, dayOfMonth)
                    this.dateOfBirthField.text = newDate.toString()
                    this.dateOfBirth = newDate
                },
                year,
                month,
                day
            )
            dpd.show()

        }
    }

    private fun createInspiringPerson(): InspiringPerson {

        val quotes: MutableList<String> = mutableListOf()
        if (this.quoteOneField.text.toString().isNotEmpty()) {
            quotes.add(this.quoteOneField.text.toString())
        }
        if (this.quoteTwoField.text.toString().isNotEmpty()) {
            quotes.add(this.quoteTwoField.text.toString())
        }
        return InspiringPerson(
            PeopleRepository.count() + 1,
            LocalDate.of(
                this.dateOfBirth.year,
                this.dateOfBirth.month,
                this.dateOfBirth.dayOfMonth
            ),
            this.shortDescriptionField.text.toString(),
            quotes,
            this.imagePath
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CodeRepository.PERMISSION_CODE -> if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                chooseImageFromGallery()
            } else {
                Toast.makeText(activity!!, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, CodeRepository.IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CodeRepository.IMAGE_PICK_CODE) {
            this.imagePath = data?.data.toString()
        }
    }
}