package com.vaaaninfra.iptmsflyingapp.adapter

import android.content.Context
import android.content.Context.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.vaaaninfra.iptmsflyingapp.R
import com.vaaaninfra.iptmsflyingapp.model.StateCityModel

class StateCityAdapter(
    private val context: Context,
    private val stateCityList: ArrayList<StateCityModel>,
    private val stateCityMap: Map<String, List<String>>
) :
    RecyclerView.Adapter<StateCityAdapter.StateCityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateCityViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.recycler_view_layout, parent, false)
        return StateCityViewHolder(view)
    }

    override fun onBindViewHolder(holder: StateCityViewHolder, position: Int) {
        val stateCity = stateCityList[position]

        var cityList: MutableList<String> = mutableListOf()
        // Populate Spinners for State and City
        val states = stateCityMap.keys.toList()
        val adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, states)
        holder.spinnerState.setAdapter(adapter)
        val adapter1 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, cityList)
        holder.spinnerCity.setAdapter(adapter1)

        holder.spinnerState.setOnClickListener {
            if (!holder.spinnerState.isPopupShowing) {
                holder.spinnerState.showDropDown()
            }
        }
        holder.spinnerState.setOnItemClickListener { parent, view, position, id ->
            hideKeyboard(holder.spinnerState)
            stateCity.selectedState = parent.getItemAtPosition(position) as String
            val cities = stateCityMap[parent.getItemAtPosition(position) as String]
            if (cities != null) {
                cityList.clear()
                cityList.addAll(cities)
                adapter1.notifyDataSetChanged()
            } else {
                Toast.makeText(
                    context,
                    "No cities found for the selected state",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        holder.spinnerCity.setOnClickListener {
            if (!holder.spinnerCity.isPopupShowing) {
                holder.spinnerCity.showDropDown()
            }
        }
        holder.spinnerCity.setOnItemClickListener { parent, view, position, id ->
            hideKeyboard(holder.spinnerCity)
            stateCity.selectedCity = parent.getItemAtPosition(position) as String

        }

    }

    override fun getItemCount(): Int {
        return stateCityList.size
    }

    class StateCityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val spinnerState: MaterialAutoCompleteTextView = view.findViewById(R.id.selectState)
        val spinnerCity: MaterialAutoCompleteTextView = view.findViewById(R.id.selectCity)
    }

    private fun hideKeyboard(view: View) {
//        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
