package com.example.listadecompras

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class ItemAdapter(context: Context, private val resource: Int, private val items: MutableList<String>) :
    ArrayAdapter<String>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(resource, parent, false)

        val itemNome = view.findViewById<TextView>(R.id.textViewItemNome)
        val buttonExcluir = view.findViewById<Button>(R.id.buttonExcluir)

        itemNome.text = items[position]

        buttonExcluir.setOnClickListener {
            items.removeAt(position)
            notifyDataSetChanged()
        }

        return view
    }
}