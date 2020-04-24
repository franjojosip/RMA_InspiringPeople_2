package ht.ferit.fjjukic.rma_lv2_2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.inspiring_people_list_item.view.*

class InspiringPersonAdapter(
    private val context: Context,
    private val inspiringPeopleListener: InspiringPeopleListener
) : BaseAdapter() {
    private var inspiringPeople: MutableList<InspiringPerson> =
        PeopleRepository.getListOfInspiringPeople()

    private val inflater: LayoutInflater =
        this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return inspiringPeople.size
    }

    override fun getItem(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inspiringPerson: InspiringPerson = inspiringPeople[position]

        val itemView: View = inflater.inflate(R.layout.inspiring_people_list_item, parent, false)

        itemView.inspiringPersonDateOfBirth.text = ("Date of birth: ${inspiringPerson.dateOfBirth}")
        itemView.inspiringPersonDsc.text = ("Description: ${inspiringPerson.shortDescription}")

        val requestOptions: RequestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(inspiringPerson.imagePath)
            .into(itemView.inspiringPersonImg)

        setClickListeners(itemView, inspiringPerson.id)

        return itemView
    }

    private fun setClickListeners(itemView: View, id: Int) {
        itemView.inspiringPersonImg.setOnClickListener { inspiringPeopleListener.onShowQuote(id) }

        itemView.inspiringPersonDsc.setOnClickListener {
            inspiringPeopleListener.checkRemoveItem(id)
        }
        itemView.inspiringPersonDsc.setOnLongClickListener {
            inspiringPeopleListener.editItem(id)
            true
        }
    }

}