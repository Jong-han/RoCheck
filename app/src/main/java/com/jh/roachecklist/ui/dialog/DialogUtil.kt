package com.jh.roachecklist.ui.dialog

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import com.jh.roachecklist.Const
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.*
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.service.AlarmReceiver
import com.jh.roachecklist.utils.DefaultNotification
import java.util.*

object DialogUtil {

    fun showAddCharacterDialog(context: Context, layoutInflater: LayoutInflater, onOk: (String, Int, String, Int)->(Unit) ) {

        val binding = ActivityCharacterAddDialogBinding.inflate( layoutInflater )
        val builder = AlertDialog.Builder( context )

        builder.setView( binding.root )
        builder.setCancelable( false )

        val dialog = builder.create()
        dialog?.window?.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT )
        dialog.show()

        binding.run {

            btnOk.setOnClickListener {
                val favorite = when (radioGroup.checkedRadioButtonId) {

                    R.id.radio_main -> Const.Favorite.MAIN
                    R.id.radio_sub -> Const.Favorite.SUB
                    else -> Const.Favorite.BARRACK

                }
                if ( !etLevel.text.isNullOrBlank() && !etName.text.isNullOrBlank() )
                    onOk.invoke( etName.text.toString(), etLevel.text.toString().toInt(), (spinner.selectedItem as String), favorite )
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            val classList = context.resources.getStringArray(R.array.klass)

            spinner.adapter = ArrayAdapter( context, android.R.layout.simple_spinner_dropdown_item, classList )



        }

    }

    fun showEditMenuDialog(context: Context, layoutInflater: LayoutInflater, item: CharacterEntity, onUpdate: (CharacterEntity, Int) -> Unit, onDelete: (CharacterEntity)->(Unit), onEditType: (CharacterEntity, Int)->(Unit) ) {

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
            btnEditType.setOnClickListener {

                showEditTypeDialog( context, layoutInflater, item, onEditType )
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

    fun showSettingDialog( context: Context, layoutInflater: LayoutInflater, onOk: ( Int, Int, Long, AlarmManager, PendingIntent )->(Unit), requestCode: Int ) {

        val binding = ActivityCharacterSettingAlarmBinding.inflate( layoutInflater )
        val builder = AlertDialog.Builder( context )

        builder.setView( binding.root )
        builder.setCancelable( false )

        val dialog = builder.create()
        dialog?.window?.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT )
        dialog.show()

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent( context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, requestCode, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        binding.run {

            btnOk.setOnClickListener {

//                DefaultNotification.startNotification( context, "로첵", "타입" )

                val hour = timePicker.hour
                val minute = timePicker.minute

                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                onOk.invoke( hour, minute, calendar.timeInMillis, alarmManager, pendingIntent )
                dialog.dismiss()

            }
            btnCancel.setOnClickListener {

                dialog.dismiss()

            }

        }

    }

    fun showSettingRestDialog( context: Context, layoutInflater: LayoutInflater, onModify: (Int, Int, Int) -> Unit ) {

        val binding = ActivityCheckListDailyRestEditDialogBinding.inflate( layoutInflater )
        val builder = AlertDialog.Builder( context )

        builder.setView( binding.root )
        builder.setCancelable( false )

        val dialog = builder.create()
        dialog?.window?.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT )
        dialog.show()

        binding.run {

            etEfona.doOnTextChanged { _, _, _, _ ->

                btnOk.isEnabled = !etEfona.text.isNullOrBlank() && !etGuardian.text.isNullOrBlank() && !etChaos.text.isNullOrBlank()
            }

            etChaos.doOnTextChanged { _, _, _, _ ->

                btnOk.isEnabled = !etEfona.text.isNullOrBlank() && !etGuardian.text.isNullOrBlank() && !etChaos.text.isNullOrBlank()

            }

            etGuardian.doOnTextChanged { _, _, _, _ ->

                btnOk.isEnabled = !etEfona.text.isNullOrBlank() && !etGuardian.text.isNullOrBlank() && !etChaos.text.isNullOrBlank()

            }

            btnOk.setOnClickListener {
                onModify.invoke( etEfona.text.toString().toInt(), etGuardian.text.toString().toInt(), etChaos.text.toString().toInt() )
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

        }

    }

    private fun showEditTypeDialog(context: Context, layoutInflater: LayoutInflater, item: CharacterEntity, onOk: (CharacterEntity, Int)->(Unit) ) {

        val binding = ActivityCharacterEditTypeDialogBinding.inflate( layoutInflater )
        val builder = AlertDialog.Builder( context )

        builder.setView( binding.root )
        builder.setCancelable( false )

        val dialog = builder.create()
        dialog?.window?.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT )
        dialog.show()

        binding.run {

            btnOk.setOnClickListener {
                val favorite = when (radioGroup.checkedRadioButtonId) {

                    R.id.radio_main -> Const.Favorite.MAIN
                    R.id.radio_sub -> Const.Favorite.SUB
                    else -> Const.Favorite.BARRACK

                }
                onOk.invoke( item, favorite )
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

        }

    }

}
