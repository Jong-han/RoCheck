package com.jh.roachecklist.ui.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCharacterItemBinding

class CharacterAdapter: ListAdapter<CharacterModel, CharacterAdapter.CharacterViewHolder>( CharacterDiffUtil() ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ActivityCharacterItemBinding>( inflater, R.layout.activity_character_item, parent, false )
        return CharacterViewHolder( binding )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        holder.bind( currentList[position] )

    }

    inner class CharacterViewHolder( private val binding: ActivityCharacterItemBinding ): RecyclerView.ViewHolder( binding.root ) {

        fun bind( item: CharacterModel ) {

            binding.run {

                this.item = item

            }

        }

    }

    class CharacterDiffUtil: DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }

    }

}