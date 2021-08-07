package com.jh.roachecklist.ui.checklist.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListItemBinding
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.ui.checklist.CheckListModel

class DailyAdapter: ListAdapter<CheckListModel, DailyAdapter.DailyViewHolder>( DailyDiffUtil() ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ActivityCheckListItemBinding>( inflater, R.layout.activity_check_list_item, parent, false )
        return DailyViewHolder( binding )
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {

        holder.bind( currentList[position] )

    }

    inner class DailyViewHolder( private val binding: ActivityCheckListItemBinding ): RecyclerView.ViewHolder( binding.root ) {

        fun bind( item: CheckListModel ) {

            binding.run {

                this.item = item

            }

        }

    }

    class DailyDiffUtil: DiffUtil.ItemCallback<CheckListModel>() {
        override fun areItemsTheSame(oldItem: CheckListModel, newItem: CheckListModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CheckListModel, newItem: CheckListModel): Boolean {
            return oldItem == newItem
        }

    }

}