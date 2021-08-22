package com.jh.roachecklist.ui.checklist.expedition.daily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCheckListItemBinding
import com.jh.roachecklist.db.CheckListEntity
import com.jh.roachecklist.setCheckBox
import com.jh.roachecklist.ui.checklist.expedition.weekly.ExpeditionWeeklyAdapter
import dagger.hilt.android.AndroidEntryPoint

class ExpeditionDailyAdapter( private val onChecked: (View, Int)->(Unit), private val onClickNoti: (Int)->(Unit) ): ListAdapter<CheckListEntity, ExpeditionDailyAdapter.ExpeditionDailyViewHolder>( ExpeditionDiffUtil() ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpeditionDailyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ActivityCheckListItemBinding>( inflater, R.layout.activity_check_list_item, parent, false )
        return ExpeditionDailyViewHolder( binding )
    }

    override fun onBindViewHolder(holder: ExpeditionDailyViewHolder, position: Int) {

        holder.bind( currentList[position] )

    }

    inner class ExpeditionDailyViewHolder( private val binding: ActivityCheckListItemBinding): RecyclerView.ViewHolder( binding.root ) {

        fun bind( item: CheckListEntity) {

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

                ivNoti.setOnClickListener {

                    onClickNoti.invoke( adapterPosition )

                }

                executePendingBindings()

            }

        }

    }

    class ExpeditionDiffUtil: DiffUtil.ItemCallback<CheckListEntity>() {
        override fun areItemsTheSame(oldItem: CheckListEntity, newItem: CheckListEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CheckListEntity, newItem: CheckListEntity): Boolean {
            return oldItem == newItem
        }

    }

}