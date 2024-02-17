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
import com.murray.data.tasks.Task
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
        binding.viewmodel = this.viewmodel
        binding.lifecycleOwner = this

        initTextWatcher()
        initImageUri()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel!!.itemTemp.value = args.item
        val itemTemp = binding.viewmodel!!.itemTemp.value!!

        if (itemTemp.id != -1) {
            initUneditedValues()
        }

        binding.bItemCreationAddItem.setOnClickListener {
            val itemType = binding.sItemCreationType.selectedItem.toString()
            val item = Item(
                    binding.tietItemCreationName.text.toString(),
                    ItemType.valueOf(itemType),
                    binding.tietItemCreationRate.text.toString().toDouble(),
                    binding.cbItemCreationTax.isChecked,
                    binding.tietItemCreationDescr.text.toString(),
                    selectedImageUri
            )
            viewmodel.validateItemCreation(item)
        }


        viewmodel.getState().observe(viewLifecycleOwner) {
            when (it) {
                ItemCreationState.NameEmptyError -> setNameEmptyError()
                ItemCreationState.InvalidFormatRateError -> setInvalidFormatRateError()
                ItemCreationState.TypeIsMandatoryError -> setTypeIsMandatoryError()
                is ItemCreationState.Error -> setError(it.exception)
                else -> onSuccess()
            }
        }
    }

    private fun setError(exception: Exception) {
        Toast.makeText(context, "Se ha producido un error ${exception}", Toast.LENGTH_SHORT).show()
        binding.tietItemCreationName.requestFocus()
    }


    private fun initUneditedValues() {
        val itemTemp = binding.viewmodel!!.itemTemp.value!!
        with(binding) {
            val viewmodel = viewmodel ?: return
            viewmodel.name.value = itemTemp.name
            viewmodel.typeSpinnerPosition.value =
                when (itemTemp.type) {
                    ItemType.Producto -> 0
                    ItemType.Servicio -> 1
                    else -> 0 //va a ser uno u otro si o si
                }
            viewmodel.rate.value = itemTemp.rate.toString()
            viewmodel.description.value = itemTemp.description
            viewmodel.isTaxable.value = itemTemp.isTaxable
            ivAddImage.setImageURI(itemTemp.imageUri)
        }
    }

    private fun onSuccess() {
        val itemTemp = binding.viewmodel!!.itemTemp.value!!
        val toastStr = when(itemTemp.id){
            -1 -> "Artículo creado"
            else -> "Artículo editado"
        }
        Toast.makeText(requireActivity(), toastStr, Toast.LENGTH_SHORT).show()

        val bundle = Bundle()
        bundle.putParcelable(Item.TAG, itemTemp)
        parentFragmentManager.setFragmentResult("editItemResult", bundle)

        findNavController().popBackStack()
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
