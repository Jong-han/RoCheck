package com.jh.roachecklist.ui.character

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jh.roachecklist.BR
import com.jh.roachecklist.R
import com.jh.roachecklist.databinding.ActivityCharacterBinding
import com.jh.roachecklist.db.CharacterEntity
import com.jh.roachecklist.ui.base.BaseActivity
import com.jh.roachecklist.ui.base.setSupportActionBar
import com.jh.roachecklist.ui.checklist.CheckListActivity
import com.jh.roachecklist.ui.dialog.DialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterActivity : BaseActivity<ActivityCharacterBinding, CharacterViewModel>() {

    companion object {

        const val EXTRA_LEVEL = "EXTRA_LEVEL"
        const val EXTRA_NICK_NAME = "EXTRA_NICK_NAME"

    }

    override val viewModel: CharacterViewModel by viewModels()

    override val bindingVariable: Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_character

    private val characterAdapter: CharacterAdapter by lazy { CharacterAdapter( startCheckList, longClickListener ) }

    override fun initViewAndEvent() {

        setSupportActionBar( dataBinding.tbCharacter, false )

        dataBinding.rvCharacter.apply {

            layoutManager = GridLayoutManager( this@CharacterActivity, 2 )
            adapter = characterAdapter

        }

        viewModel.clickAddCharacter.observe( this, {

            DialogUtil.showAddCharacterDialog( this, layoutInflater ) { name: String, level: Int, klass: String ->
                viewModel.addCharacter( name, level, klass )
            }

        })

        viewModel.rvItems.observe( this, {

            characterAdapter.submitList( it.toList() )

        })

    }

    private val startCheckList: Function1<Int, Unit> = { pos: Int ->

        val item = characterAdapter.currentList[pos]
        Intent( this, CheckListActivity::class.java ).apply {

            putExtra( EXTRA_LEVEL, item.level )
            putExtra( EXTRA_NICK_NAME, item.nickName )

            val optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation( this@CharacterActivity )

            startActivity( this, optionsCompat.toBundle() )

        }

    }

    private val longClickListener = { pos: Int ->

        val item = characterAdapter.currentList[pos]
        DialogUtil.showEditMenuDialog( this, layoutInflater, item, updateCharacter, deleteCharacter )

    }

    private val updateCharacter = { character: CharacterEntity, level: Int ->

        viewModel.updateCharacter( character, level )
        characterAdapter.notifyItemChanged( characterAdapter.currentList.indexOf( character ) )

    }

    private val deleteCharacter = { character: CharacterEntity ->

        viewModel.deleteCharacter( character )

    }

}