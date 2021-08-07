package com.jh.roachecklist.ui.checklist.raid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListItemBinding
import com.jh.roachecklist.ui.checklist.CheckListModel

class RaidAdapter: ListAdapter<CheckListModel, RaidAdapter.RaidViewHolder>( RaidDiffUtil() ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaidViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ActivityCheckListItemBinding>( inflater, R.layout.activity_check_list_item, parent, false )
        return RaidViewHolder( binding )
    }

    override fun onBindViewHolder(holder: RaidViewHolder, position: Int) {

        holder.bind( currentList[position] )

    }

    inner class RaidViewHolder( private val binding: ActivityCheckListItemBinding): RecyclerView.ViewHolder( binding.root ) {

        fun bind( item: CheckListModel) {

            binding.run {

                model = item

            }

        }

    }

    class RaidDiffUtil: DiffUtil.ItemCallback<CheckListModel>() {
        override fun areItemsTheSame(oldItem: CheckListModel, newItem: CheckListModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CheckListModel, newItem: CheckListModel): Boolean {
            return oldItem == newItem
        }

    }

}