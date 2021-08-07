package com.jh.roachecklist.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jh.roachecklist.databinding.ActivityCharacterAddDialogBinding
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
                if ( !etKlass.text.isNullOrBlank() && !etLevel.text.isNullOrBlank() && !etName.text.isNullOrBlank() )
                    onOk.invoke( etName.text.toString(), etLevel.text.toString().toInt(), etKlass.text.toString() )
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

        }

    }

    fun showEditMenuDialog(context: Context, layoutInflater: LayoutInflater, item: CharacterEntity, onUpdate: (CharacterEntity, Int) -> Unit, onDelete: (CharacterEntity)->(Unit) ) {

        val binding = ActivityCharacterEditMenuDialogBinding.inflate( layoutInflater )
        val builder = AlertDialog.Builder( context )

        builder.setView( binding.root )
        builder.setCancelable( false )

        val dialog = builder.create()
        dialog?.window?.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT )
        dialog.show()

        binding.run {

            btnUpdateLevel.setOnClickListener {
                showEditLevelDialog( context, layoutInflater, item, onUpdate )
                dialog.dismiss()
            }
            btnDeleteCharacter.setOnClickListener {
                onDelete.invoke( item )
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

//
//    /** make Default Alert Dialog */
//    fun showDefaultDialog(context: Context, layoutInflater: LayoutInflater,
//                          title: String?, content: Any, subContent: String?,
//                          cancelText: String?, confirmText: String?, isAutoCancel: Boolean,
//                          dialogClickListener: DialogDefaultClickListener?, obj: Any? ) {
//
//        val binding = ViewDialogDefaultBinding.inflate( layoutInflater )
//        // use Style which you want
//        val builder = AlertDialog.Builder( context, R.style.AppTheme_FullScreenDialog )
//
//        // setting Default Dialog title ( tv_title ) default visibility = gone
//        title?.let {
//            binding.tvTitle.visibility = View.VISIBLE
//            binding.tvTitle.text = it
//        }
//
//        // setting Default Dialog MainContent ( tv_content )
//        if ( content is String || content is CharSequence ) {
//
//            binding.tvContent.text = content.toString()
//
//        }
//
//        // setting Default Dialog SubContent ( tv_sub_content ) default visibility = gone
//        subContent?.let {
//
//            binding.tvSubContent.visibility = View.VISIBLE
//            binding.tvSubContent.text = subContent
//
//        }
//
//        // dialog builder setting
//        builder.setView( binding.root )
//        builder.setCancelable( isAutoCancel )
//
//        // dialog setting
//        val dialog = builder.create()
//        // dialog Full Screen Setting
//        dialog?.window?.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT )
//        dialog.window?.setBackgroundDrawable( ColorDrawable( Color.TRANSPARENT ) )
//        dialog.show()
//
//        // btn right Setting ( btn_right )
//        binding.btnRight.text = confirmText
//        binding.btnRight.setOnClickListener {
//            dialogClickListener?.onOk( obj )
//            dialog.dismiss()
//        }
//
//        // btn_left Setting  ( btn_left ) btn_left default visibility = gone
//        cancelText?.let {
//
//            binding.btnLeft.visibility = View.VISIBLE
//            binding.btnLeft.text = cancelText
//            binding.btnLeft.setOnClickListener {
//                dialogClickListener?.onCancel( obj )
//                dialog.dismiss()
//            }
//
//        }
//
//    }
}