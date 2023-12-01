package com.murray.item.ui.itemcreation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.murray.entities.items.ItemType
import com.murray.item.databinding.FragmentItemCreationBinding
import com.murray.item.ui.itemcreation.usecase.ItemCreationState
import com.murray.item.ui.itemcreation.usecase.ItemCreationViewModel
import com.murray.repositories.ImagesItem
import com.murray.repositories.ItemRepository

class ItemCreationFragment : Fragment() {

    private var _binding: FragmentItemCreationBinding? = null
    private val binding
        get() = _binding!!

    private val itemcreationviewmodel: ItemCreationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemCreationBinding.inflate(inflater, container,false)
        binding.itemcreationviewmodel = this.itemcreationviewmodel
        binding.lifecycleOwner = this
        addTextWatcher()
        return binding.root
    }

    private fun addTextWatcher(){
        val textWatcherName = LayoutTextWatcher(binding.tilItemCreationName)
        val textWatcherRate = LayoutTextWatcher(binding.tilItemCreationRate)
        binding.tietItemCreationName.addTextChangedListener(textWatcherName)
        binding.tietItemCreationRate.addTextChangedListener(textWatcherRate)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //itemviewmodel.isTaxable.observe(viewLifecycleOwner, { isTaxable -> })

        itemcreationviewmodel.getState().observe(viewLifecycleOwner){
            when(it){
                ItemCreationState.NameEmptyError -> setNameEmptyError()
                ItemCreationState.InvalidFormatRateError -> setInvalidFormatRateError()
                ItemCreationState.TypeIsMandatoryError -> setTypeIsMandatoryError()
                else -> onSuccess()
            }
        }
    }

    private fun onSuccess() {
        with(itemcreationviewmodel){
            var name:String = name.value!!
            var type:ItemType =
                when(typeSpinnerPosition.value){
                    0 -> ItemType.PRODUCT
                    1 -> ItemType.SERVICE
                    else -> ItemType.PRODUCT //va a ser uno u otro si o si
                }
            var rate:Double = rate.value!!.toDouble()
            var isTaxable:Boolean = isTaxable.value ?: false
            var description:String = description.value ?: ""
            //todo imagen placeholder
            var image:ImagesItem = ImagesItem.MALETA_CUERO

            ItemRepository.addItem(name, type, rate, isTaxable, description, image)
        }
        Toast.makeText(requireActivity(), "Artículo creado", Toast.LENGTH_SHORT).show()
    }

    private fun setNameEmptyError() {
        binding.tilItemCreationName.error = "Introduce un nombre"
        binding.tilItemCreationName.requestFocus()
    }
    private fun setInvalidFormatRateError() {
        binding.tilItemCreationRate.error = "Introduce un precio válido"
        binding.tilItemCreationRate.requestFocus()
    }
    private fun setTypeIsMandatoryError() {
        binding.tilItemCreationType.error = "Elige un tipo válido"
        binding.tilItemCreationType.requestFocus()
    }
}

class LayoutTextWatcher(private val til: TextInputLayout) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //no se implementa
    }
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //no se implementa
    }
    override fun afterTextChanged(s: Editable?) {
        til.error =null
    }
}
