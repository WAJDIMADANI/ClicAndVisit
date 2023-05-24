package com.foodline.ui.menu.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.databinding.ActivityProfileBinding
import com.foodline.global.helper.ImagePicker
import com.foodline.global.helper.Navigation
import com.foodline.global.utils.observeOnlyNotNull
import com.foodline.ui.home.HomeActivity
import com.foodline.ui.menu.help.HelpActivity
import com.foodline.ui.shared.dialog.SimpleImgPickerDialog
import dagger.hilt.android.AndroidEntryPoint

const val REQUEST_CODE_PERMISSIONS = 102
const val CAMERA_PERMISSION = Manifest.permission.CAMERA


@AndroidEntryPoint
class ProfileActivity : BaseActivity() {

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        registerSinUpObservers()
        registerBindingAndBaseObservers(binding)
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        hideKeyboard()
        when (navigationTo) {
            is Navigation.Back -> finish()

            is Navigation.HomeActivityNavigation -> {
                navigateToActivity(HomeActivity::class)
            }

            is Navigation.HelpActivityNavigation -> {
                navigateToActivity(HelpActivity::class)
            }

            is Navigation.CameraNavigation -> showCamera(navigationTo.imageName)

            is Navigation.GalleryNavigation -> showGallery()

        }
    }


    private fun showGallery() {
        val chooseImageIntent: Intent = ImagePicker.getGalleryIntent()
        startActivityForResult(chooseImageIntent, ImagePicker.PICK_IMAGE_GALLERY_ID)
    }


    private fun showCamera(imageName: String) {
        if (ContextCompat.checkSelfPermission(
                baseContext,
                com.foodline.ui.signup.CAMERA_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivityForResult(
                ImagePicker.getCameraIntent(this, imageName),
                ImagePicker.PICK_IMAGE_CAMERA_ID
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(com.foodline.ui.signup.CAMERA_PERMISSION),
                com.foodline.ui.signup.REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == com.foodline.ui.signup.REQUEST_CODE_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.cameraPermissionGranted()
        }
    }


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
        if (!isFinishing) {
            SimpleImgPickerDialog(
                this,
                takePicActionBlock,
                pickPicActionBlock,
                dismissActionBlock
            ).show()
        }
    }
    private fun registerSinUpObservers() {
        viewModel.imagePickerDialog.observeOnlyNotNull(this) { dialog ->
            showSimpleImagePickerDialog(
                dialog.takePicActionBlock,
                dialog.pickPicActionBlock,
                dialog.dismissActionBlock
            )
        }
    }


    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityProfileBinding) {
        binding.viewModel = viewModel
        binding.picasso = getPicasso()
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}