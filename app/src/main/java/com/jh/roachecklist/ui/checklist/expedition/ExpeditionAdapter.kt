package com.jh.roachecklist.ui.checklist.expedition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListItemBinding
import com.jh.roachecklist.setCheckBox
import com.jh.roachecklist.ui.checklist.CheckListModel

class ExpeditionAdapter( private val onChecked: (View, Int)->(Unit) ): ListAdapter<CheckListModel, ExpeditionAdapter.ExpeditionViewHolder>( ExpeditionDiffUtil() ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpeditionViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ActivityCheckListItemBinding>( inflater, R.layout.activity_check_list_item, parent, false )
        return ExpeditionViewHolder( binding )
    }

    override fun onBindViewHolder(holder: ExpeditionViewHolder, position: Int) {

        holder.bind( currentList[position] )

    }

    inner class ExpeditionViewHolder( private val binding: ActivityCheckListItemBinding): RecyclerView.ViewHolder( binding.root ) {

        fun bind( item: CheckListModel) {

            binding.run {

                model = item
                cb1.setOnClickListener {
                    onChecked.invoke( it, adapterPosition )
                }
                cb1.setCheckBox( item.checkedCount, 1 )

                cb2.setOnClickListener {
                    onChecked.invoke( it, adapterPosition )
                }
                cb2.setCheckBox( item.checkedCount, 2 )

                cb3.setOnClickListener {
                    onChecked.invoke( it, adapterPosition )
                }
                cb3.setCheckBox( item.checkedCount, 3 )

                executePendingBindings()

            }

        }

    }

    class ExpeditionDiffUtil: DiffUtil.ItemCallback<CheckListModel>() {
        override fun areItemsTheSame(oldItem: CheckListModel, newItem: CheckListModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CheckListModel, newItem: CheckListModel): Boolean {
            return oldItem == newItem
        }

    }

}