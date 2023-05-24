package com.foodline.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.data.model.user.User
import com.foodline.databinding.ActivitySigninBinding
import com.foodline.global.helper.Navigation
import com.foodline.global.utils.DebugLog
import com.foodline.global.utils.ExtraKeys
import com.foodline.global.utils.TAG
import com.foodline.ui.home.HomeActivity
import com.foodline.ui.password.ResetPasswordActivity
import com.foodline.ui.signup.SignUpActivity
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity() {

    private val viewModel: SignInViewModel by viewModels()

    private lateinit var binding: ActivitySigninBinding

    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin)

        registerBindingAndBaseObservers(binding)
        registerSinInObservers()
    }


    private fun registerSinInObservers() {
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }

    // Google
    fun onGoogleSignInClicked(view: View) {
        if (!isUserSignedIn()) {
            val signInIntent = getGoogleSinginClient().signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        } else {
            DebugLog.i(TAG, "User already signed-in")
        }
    }

    //region User Google Sign-in and sign-out Code

    private fun getGoogleSinginClient(): GoogleSignInClient {
        /**
         * Configure sign-in to request the user's ID, email address, and basic
         * profile. ID and basic profile are included in DEFAULT_SIGN_IN.
         */
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        /**
         * Build a GoogleSignInClient with the options specified by gso.
         */
        return GoogleSignIn.getClient(this, gso);
    }


    private fun isUserSignedIn(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        return account != null

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            handleSignData(data)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
            /*
            val accessToken: AccessToken = AccessToken.getCurrentAccessToken()
            val isLoggedIn = accessToken != null && !accessToken.isExpired()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
            */

        }
    }

    private fun handleSignData(data: Intent?) {
        // The Task returned from this call is always completed, no need to attach
        // a listener.
        GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnCompleteListener {
                "isSuccessful ${it.isSuccessful}".print()
                if (it.isSuccessful) {
                    // user successfully logged-in
                    "account ${it.result?.account}".print()
                    "displayName ${it.result?.displayName}".print()
                    "Email ${it.result?.email}".print()
                } else {
                    // authentication failed
                    "exception ${it.exception}".print()
                }
            }
    }

    companion object {
        const val RC_SIGN_IN = 0
        const val TAG_KOTLIN = "TAG_KOTLIN"
    }


    // Facebook

    fun onFacebookSignInClicked(view: View) {

        binding.facebookLoginButton.setOnClickListener {

            callbackManager = CallbackManager.Factory.create()


            LoginManager.getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        DebugLog.d("FBLOGIN", loginResult.accessToken.token.toString())
                        DebugLog.d("FBLOGIN", loginResult.recentlyDeniedPermissions.toString())
                        DebugLog.d("FBLOGIN", loginResult.recentlyGrantedPermissions.toString())


                        val request =
                            GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                                try {
                                    //here is the data that you want
                                    DebugLog.d("FBLOGIN_JSON_RES", `object`.toString())

                                    if (`object`.has("id")) {
                                        //handleSignInResultFacebook(`object`)
                                    } else {
                                        DebugLog.e("FBLOGIN_FAILD", `object`.toString())
                                    }

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    //dismissDialogLogin()
                                }
                            }

                        val parameters = Bundle()
                        parameters.putString("fields", "name,email,id,picture.type(large)")
                        request.parameters = parameters
                        request.executeAsync()

                    }

                    override fun onCancel() {
                        DebugLog.e("FBLOGIN_FAILD", "Cancel")
                    }

                    override fun onError(error: FacebookException) {
                        DebugLog.e("FBLOGIN_FAILD", "ERROR", error)
                    }
                })
        }

        binding.facebookLoginButton.performClick()
    }

    /**
     * handling navigation event
     */
    private fun navigateToHome(user: User) {
        Intent(this, HomeActivity::class.java).let {
            it.putExtra(ExtraKeys.HomeActivity.HOME_EXTRA_USER_KEY, user)
            startActivity(it)
            finish()
        }
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.HomeActivityNavigation -> {
                navigateToActivity(HomeActivity::class)
            }
            is Navigation.ResetPasswordActivityNavigation -> navigateToActivity(
                ResetPasswordActivity::class
            )
            is Navigation.SignUpActivityNavigation -> navigateToActivity(
                SignUpActivity::class,
                true
            )
            is Navigation.Back -> finish()
        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivitySigninBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }
}

fun Any.print() {
    DebugLog.v(SignInActivity.TAG_KOTLIN, " $this")
}