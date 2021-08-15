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

            binding.run {

                this@run.item = item

                container.setOnClickListener {

                    onClick.invoke( adapterPosition )

                }
                container.setOnLongClickListener {

                    onLongClick.invoke( adapterPosition )
                    true

                }

            }

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