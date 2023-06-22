package com.clickandvisit.ui.ads.addads.stepfour


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.data.model.property.add.PropertyAdd
import com.clickandvisit.databinding.FragmentFourBinding
import com.clickandvisit.databinding.OneFragmentBinding
import com.clickandvisit.global.helper.ImagePicker
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.observeOnlyNotNull
import com.clickandvisit.ui.ads.addads.stepone.OneViewModel
import com.clickandvisit.ui.shared.dialog.SimpleImgPickerDialog
import com.clickandvisit.ui.user.profile.CAMERA_PERMISSION
import com.clickandvisit.ui.user.profile.REQUEST_CODE_PERMISSIONS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FourFragment(val property: PropertyAdd?) : BaseFragment() {


    val viewModel: FourViewModel by viewModels()

    private lateinit var binding: FragmentFourBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_four, container, false)
        binding = FragmentFourBinding.bind(view)
        binding.picasso = getPicasso()
        binding.viewModel = viewModel
        if (property != null){
            viewModel.onEditProperty(property)
        }

        binding.lifecycleOwner = viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
        registerBaseObserver(viewModel)
    }


    private fun showGallery() {
        val chooseImageIntent: Intent = ImagePicker.getGalleryIntent()
        startActivityForResult(chooseImageIntent, ImagePicker.PICK_IMAGE_GALLERY_ID)
    }


    private fun showCamera(imageName: String) {
        if (ContextCompat.checkSelfPermission(
                requireActivity().baseContext,
                CAMERA_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivityForResult(
                ImagePicker.getCameraIntent(requireActivity(), imageName),
                ImagePicker.PICK_IMAGE_CAMERA_ID
            )
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(CAMERA_PERMISSION),
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.cameraPermissionGranted()
        }
    }

    //@Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            viewModel.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showSimpleImagePickerDialog(
        takePicActionBlock: (() -> Unit)? = null,
        pickPicActionBlock: (() -> Unit)? = null,
        dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!requireActivity().isFinishing) {
            SimpleImgPickerDialog(
                requireActivity(),
                takePicActionBlock,
                pickPicActionBlock,
                dismissActionBlock
            ).show()
        }
    }

    private fun registerObservers() {
        viewModel.imagePickerDialog.observeOnlyNotNull(this) { dialog ->
            showSimpleImagePickerDialog(
                dialog.takePicActionBlock,
                dialog.pickPicActionBlock,
                dialog.dismissActionBlock
            )
        }
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {

            is Navigation.CameraNavigation -> showCamera(navigationTo.imageName)

            is Navigation.GalleryNavigation -> showGallery()

        }
    }

}