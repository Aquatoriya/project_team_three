package spb.cool_practice.cybermap

import android.R
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.annotation.Nullable

import java.util.ArrayList


class AutoCompleteAdapter(context: Context, private val resourceId: Int, items: ArrayList<String>) :
    ArrayAdapter<String>(context, resourceId, items) {
    private var items : ArrayList<String> = items
    private var tempItems : List<String> = ArrayList(items)
    private var suggestions = mutableListOf<String>()
    private val StringFilter = object : Filter() {
        override fun convertResultToString(resultValue: Any): CharSequence {
            return resultValue as String
        }

        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            if (charSequence != null) {
                suggestions.clear()
                for (s in tempItems) {
                    if (s.toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(s)
                    }
                }
                if (suggestions.size == 0) {
                    suggestions.add("No such computer club")
                }
                val filterResults = FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions.size
                return filterResults
            } else {
                return FilterResults()
            }
        }

        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            if (filterResults.count > 0 && filterResults.values != null) {
                val tempValues = filterResults.values as ArrayList<*>
                clear()
                for (StringObj in tempValues) {
                    add(StringObj as String?)
                    notifyDataSetChanged()
                }
            } else {
                clear()
                notifyDataSetChanged()
            }
        }
    }

    init {
        suggestions = ArrayList<String>()
    }

    override fun getView(position: Int, @Nullable convertView: View?, parent: ViewGroup): View {
        var view = convertView
        try {
            if (convertView == null) {
                val inflater = (context as Activity).layoutInflater
                view = inflater.inflate(resourceId, parent, false)
            }
            val String = getItem(position)
            val name = view!!.findViewById<View>(R.id.text1) as TextView
            name.text = String
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return view!!
    }

    @Nullable
    override fun getItem(position: Int): String? {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getFilter(): Filter {
        return StringFilter
    }
}