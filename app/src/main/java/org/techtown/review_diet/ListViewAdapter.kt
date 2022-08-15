package org.techtown.review_diet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/*리스트뷰 어댑터를 만들었으면, 리스트 뷰 아이템도 만든다. -> listview_item.xml 만들기*/

class ListViewAdapter(val List : MutableList<DataModel>): BaseAdapter() {
    override fun getCount(): Int {
        return List.size
    }

    override fun getItem(position: Int): Any {
        return List[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var convertView = convertView

        if(convertView==null){

            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.listview_item,parent,false)
        }

        val date = convertView?.findViewById<TextView>(R.id.listViewDateArea)
        val memo = convertView?.findViewById<TextView>(R.id.listViewMemoArea)

        date!!.text = List[position].date
        date!!.text = List[position].memo

        return convertView!!

        //리스트뷰 다 만들었다. Main에 연결해주면 된다!
    }
}