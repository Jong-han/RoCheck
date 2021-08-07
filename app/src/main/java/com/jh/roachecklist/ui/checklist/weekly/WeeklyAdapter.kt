package com.jh.roachecklist.ui.checklist.weekly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListItemBinding
import com.jh.roachecklist.ui.checklist.CheckListModel

class WeeklyAdapter: ListAdapter<CheckListModel, WeeklyAdapter.WeeklyViewHolder>( WeeklyDiffUtil() ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ActivityCheckListItemBinding>( inflater, R.layout.activity_check_list_item, parent, false )
        return WeeklyViewHolder( binding )
    }

    override fun onBindViewHolder(holder: WeeklyViewHolder, position: Int) {

        holder.bind( currentList[position] )

    }

    inner class WeeklyViewHolder( private val binding: ActivityCheckListItemBinding): RecyclerView.ViewHolder( binding.root ) {

        fun bind( item: CheckListModel) {

            binding.run {

                model = item
                executePendingBindings()

            }

        }

    }

    class WeeklyDiffUtil: DiffUtil.ItemCallback<CheckListModel>() {
        override fun areItemsTheSame(oldItem: CheckListModel, newItem: CheckListModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CheckListModel, newItem: CheckListModel): Boolean {
            return oldItem == newItem
        }

    }

}