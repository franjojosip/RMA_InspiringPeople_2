package ht.ferit.fjjukic.rma_lv2_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment


class InspiringPeopleFragment(
    private var inspiringPersonAdapter: InspiringPersonAdapter
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_first, container, false)
        val listView: ListView = view.findViewById(R.id.listView)
        listView.adapter = inspiringPersonAdapter

        return view;
    }
}