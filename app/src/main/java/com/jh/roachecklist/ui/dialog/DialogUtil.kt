package com.jh.roachecklist.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCharacterAddDialogBinding
import com.jh.roachecklist.databinding.ActivityCharacterEditDeleteDialogBinding
import com.jh.roachecklist.databinding.ActivityCharacterEditLevelDialogBinding
import com.jh.roachecklist.databinding.ActivityCharacterEditMenuDialogBinding
import com.jh.roachecklist.db.CharacterEntity

object DialogUtil {

    fun showAddCharacterDialog(context: Context, layoutInflater: LayoutInflater, onOk: (String, Int, String)->(Unit) ) {

        val binding = ActivityCharacterAddDialogBinding.inflate( layoutInflater )
        val builder = AlertDialog.Builder( context )

        builder.setView( binding.root )
        builder.setCancelable( false )

        val dialog = builder.create()
        dialog?.window?.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT )
        dialog.show()

        binding.run {

            btnOk.setOnClickListener {
                if ( !etLevel.text.isNullOrBlank() && !etName.text.isNullOrBlank() )
                    onOk.invoke( etName.text.toString(), etLevel.text.toString().toInt(), (spinner.selectedItem as String) )
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            val classList = context.resources.getStringArray(R.array.klass)

            spinner.adapter = ArrayAdapter( context, android.R.layout.simple_spinner_dropdown_item, classList )



        }

    }

    fun showEditMenuDialog(context: Context, layoutInflater: LayoutInflater, item: CharacterEntity, onUpdate: (CharacterEntity, Int) -> Unit, onDelete: (CharacterEntity)->(Unit) ) {

        val binding = ActivityCharacterEditMenuDialogBinding.inflate( layoutInflater )
        val builder = AlertDialog.Builder( context )

        builder.setView( binding.root )
        builder.setCancelable( true )

        val dialog = builder.create()
        dialog?.window?.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT )
        dialog.show()

        binding.run {

            btnUpdateLevel.setOnClickListener {
                showEditLevelDialog( context, layoutInflater, item, onUpdate )
                dialog.dismiss()
            }
            btnDeleteCharacter.setOnClickListener {
                showDeleteDialog( context, layoutInflater, item, onUpdate, onDelete )
                dialog.dismiss()
            }

        }

    }

    private fun showEditLevelDialog(context: Context, layoutInflater: LayoutInflater, item: CharacterEntity, onUpdate: (CharacterEntity, Int) -> Unit) {

        val binding = ActivityCharacterEditLevelDialogBinding.inflate( layoutInflater )
        val builder = AlertDialog.Builder( context )

        builder.setView( binding.root )
        builder.setCancelable( false )

        val dialog = builder.create()
        dialog?.window?.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT )
        dialog.show()

        binding.run {

            btnOk.setOnClickListener {
                if ( !etLevel.text.isNullOrBlank() )
                    onUpdate.invoke( item, etLevel.text.toString().toInt() )
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

        }

    }

    private fun showDeleteDialog( context: Context, layoutInflater: LayoutInflater, item: CharacterEntity, onUpdate: (CharacterEntity, Int) -> Unit, onDelete: (CharacterEntity)->(Unit) ) {

        val binding = ActivityCharacterEditDeleteDialogBinding.inflate( layoutInflater )
        val builder = AlertDialog.Builder( context )

        builder.setView( binding.root )
        builder.setCancelable( false )

        val dialog = builder.create()
        dialog?.window?.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT )
        dialog.show()

        binding.run {

            btnOk.setOnClickListener {
                onDelete.invoke( item )
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

        }

    }

}