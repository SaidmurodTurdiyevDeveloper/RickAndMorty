package com.example.demoforajob.zzz_utills

import androidx.recyclerview.widget.DiffUtil
import com.example.demoforajob.data.model.ItemData

class MyDiffUtils constructor(private var oldlist: List<ItemData>, private var newList: List<ItemData>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldlist.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldlist[oldItemPosition].id.equals(newList[newItemPosition].id)


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldlist[oldItemPosition].id == newList[newItemPosition].id
            && oldlist[oldItemPosition].name == newList[newItemPosition].name
            && oldlist[oldItemPosition].firstSeen == newList[newItemPosition].firstSeen
            && oldlist[oldItemPosition].loacation == newList[newItemPosition].loacation


}