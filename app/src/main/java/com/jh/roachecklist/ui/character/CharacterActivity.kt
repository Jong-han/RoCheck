package com.jh.roachecklist.ui.character

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCharacterBinding
import com.jh.roachecklist.databinding.ActivityCharacterDialogBinding
import com.jh.roachecklist.ui.base.BaseActivity
import com.jh.roachecklist.ui.base.setSupportActionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterActivity : BaseActivity<ActivityCharacterBinding, CharacterViewModel>() {

    override val viewModel: CharacterViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_character

    private val characterAdapter = CharacterAdapter()

    override fun initViewAndEvent() {

        setSupportActionBar( dataBinding.tbCharacter, false )

        dataBinding.rvCharacter.apply {

            layoutManager = LinearLayoutManager( this@CharacterActivity )
            adapter = characterAdapter

        }

        viewModel.clickAddCharacter.observe( this, {

            showDefaultDialog( this, layoutInflater ) { name: String, level: Int, klass: String ->
                viewModel.createCharacter( name, level, klass )
            }

        })

        viewModel.rvItem.observe( this, {

            characterAdapter.submitList( it )

        })

    }

    private fun showDefaultDialog(context: Context, layoutInflater: LayoutInflater, onOk: (String, Int, String)->(Unit) ) {

        val binding = ActivityCharacterDialogBinding.inflate( layoutInflater )
        val builder = AlertDialog.Builder( context )

        builder.setView( binding.root )
        builder.setCancelable( false )

        val dialog = builder.create()
        dialog?.window?.setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT )
        dialog.show()

        binding.run {

            btnOk.setOnClickListener {
                onOk.invoke( etName.text.toString(), etLevel.text.toString().toInt(), etKlass.text.toString() )
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

        }

    }
}