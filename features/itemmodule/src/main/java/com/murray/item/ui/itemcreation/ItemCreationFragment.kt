package com.murray.item.ui.itemcreation

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.murray.data.items.Item
import com.murray.data.items.ItemType
import com.murray.item.databinding.FragmentItemCreationBinding
import com.murray.item.ui.itemcreation.usecase.ItemCreationState
import com.murray.item.ui.itemcreation.usecase.ItemCreationViewModel

class ItemCreationFragment : Fragment() {

    private var _binding: FragmentItemCreationBinding? = null
    private val binding get() = _binding!!
    private val args: ItemCreationFragmentArgs by navArgs()

    private val viewmodel: ItemCreationViewModel by viewModels()

    private lateinit var getContent: ActivityResultLauncher<String>
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemCreationBinding.inflate(inflater, container, false)
        binding.itemcreationviewmodel = this.viewmodel
        binding.lifecycleOwner = this

        initTextWatcher()
        initImageUri()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemArgs: Item? = args.item
        //Log.d("ShowItemUnchanged", itemArgs.toString())
        if (itemArgs != null) {
            //Log.d("ShowItemUnchanged", "Item: $itemArgs")
            showItemUnchangedValuesEdit(itemArgs)
        }

        viewmodel.getState().observe(viewLifecycleOwner) {
            when (it) {
                ItemCreationState.NameEmptyError -> setNameEmptyError()
                ItemCreationState.InvalidFormatRateError -> setInvalidFormatRateError()
                ItemCreationState.TypeIsMandatoryError -> setTypeIsMandatoryError()
                else -> onSuccess()
            }
        }
    }

    private fun showItemUnchangedValuesEdit(item: Item) {
        with(binding) {
            val viewmodel = itemcreationviewmodel ?: return
            //otra manera: itemcreationviewmodel?.name?.value = item.name
            viewmodel.name.value = item.name
            viewmodel.typeSpinnerPosition.value =
                when (item.type) {
                    ItemType.PRODUCT -> 0
                    ItemType.SERVICE -> 1
                    else -> 0 //va a ser uno u otro si o si
                }
            viewmodel.rate.value = item.rate.toString()
            viewmodel.description.value = item.description
            viewmodel.isTaxable.value = item.isTaxable
            ivAddImage.setImageURI(item.imageUri)
        }
    }

    private fun onSuccess() {
        val itemArgs: Item? = args.item
        if (itemArgs == null) {
            with(viewmodel) {
                val type: ItemType =
                    when (typeSpinnerPosition.value) {
                        1 -> ItemType.SERVICE
                        else -> ItemType.PRODUCT
                    }
                viewmodel.addItem(name.value!!, type,  rate.value!!.toDouble(), isTaxable.value ?: false, description.value ?: "", selectedImageUri)
            }
            Toast.makeText(requireActivity(), "Artículo creado", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            with(binding) {
                itemArgs.name = tietItemCreationName.text.toString()
                itemArgs.type =
                    when (sItemCreationType.selectedItemPosition) {
                        1 -> ItemType.SERVICE
                        else -> ItemType.PRODUCT
                    }
                itemArgs.rate = tietItemCreationRate.text.toString().toDouble()
                itemArgs.isTaxable = cbItemCreationTax.isChecked
                itemArgs.description = tietItemCreationDescr.text.toString()
                itemArgs.imageUri = selectedImageUri
                viewmodel.editItem(itemArgs)
            }

            Toast.makeText(requireActivity(), "Artículo editado", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
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

    private fun initImageUri() {
        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                binding.ivAddImage.setImageURI(uri)
                selectedImageUri = uri
            }
        }
        binding.ivAddImage.setOnClickListener { getContent.launch("image/*") }
    }

    private fun initTextWatcher() {
        val textWatcherName = LayoutTextWatcher(binding.tilItemCreationName)
        val textWatcherRate = LayoutTextWatcher(binding.tilItemCreationRate)
        binding.tietItemCreationName.addTextChangedListener(textWatcherName)
        binding.tietItemCreationRate.addTextChangedListener(textWatcherRate)
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
        til.error = null
    }
}
