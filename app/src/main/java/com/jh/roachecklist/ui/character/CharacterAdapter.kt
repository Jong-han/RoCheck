package com.jh.roachecklist.ui.character

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCharacterItemBinding
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.utils.CheckListUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterAdapter( private val onClick: (Int)->(Unit), private val onLongClick: (Int)->Unit ): ListAdapter<CharacterEntity, CharacterAdapter.CharacterViewHolder>( CharacterDiffUtil() ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ActivityCharacterItemBinding>( inflater, R.layout.activity_character_item, parent, false )
        return CharacterViewHolder( binding )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        holder.bind( currentList[position] )

    }

    inner class CharacterViewHolder( private val binding: ActivityCharacterItemBinding ): RecyclerView.ViewHolder( binding.root ) {

        fun bind( item: CharacterEntity ) {
            Log.i("asdf","++++++++++++++++++START BIND+++++++++++++++++++")

            binding.run {

                this@run.item = item

                Log.i("asdf","${item.nickName} dailySuccess :: ${item.dailySuccess}")
                Log.i("asdf","${item.nickName} weeklySuccess :: ${item.weeklySuccess}")

                container.setOnClickListener {

                    onClick.invoke( adapterPosition )

                }
                container.setOnLongClickListener {

                    onLongClick.invoke( adapterPosition )
                    true

                }

//                CoroutineScope( Dispatchers.IO ).launch {
//
//                    item.dailySuccess = !checkListUtil.alarmDaily( listOf( item ) )
//                    item.weeklySuccess = !( checkListUtil.alarmWeekly( listOf( item ) ) && checkListUtil.alarmRaid( listOf( item ) ) )
//
//                    Log.i("zxcv","dailySuccess :: ${item.dailySuccess}")
//                    Log.i("zxcv","weeklySuccess :: ${item.weeklySuccess}")
//
//                    withContext( Dispatchers.Main ) {
//
//                    }
//                }

            }
            Log.i("asdf","++++++++++++++++++END BIND+++++++++++++++++++")

        }

    }

    class CharacterDiffUtil: DiffUtil.ItemCallback<CharacterEntity>() {
        override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
            return oldItem == newItem
        }

    }

}