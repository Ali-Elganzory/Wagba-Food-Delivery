package com.ganzory.wagba

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ganzory.wagba.databinding.FragmentLoginBinding
import com.ganzory.wagba.shared.AuthValidator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var validator: AuthValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Register the validator
        validator = AuthValidator(
            binding.tfEmail, binding.tfPassword,
            validEmailOrg = getString(R.string.valid_email_org),
            validEmailPattern = getString(R.string.valid_email_pattern),
            validPassShort = getString(R.string.valid_pass_short),
        )

        // Go to signup
        binding.btnSignup.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragmentContainerView, SignupFragment.newInstance())
                addToBackStack(null)
                commit()
            }
        }

        // Login
        binding.btnLogin.setOnClickListener {
            it.isEnabled = false
            // Validation
            val (valid, email, password) = validator.validate()
            // If valid
            if (valid) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "login : success")
                            // Go home
                            startActivity(Intent(activity, HomeActivity::class.java))
                            activity?.finish()
                        } else {
                            Log.w(TAG, "login : failure", task.exception)
                            val msg = getString(
                                when (task.exception) {
                                    is FirebaseAuthInvalidUserException -> R.string.valid_auth_no_user
                                    else -> R.string.msg_error_general
                                }
                            )
                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
                            it.isEnabled = true
                        }
                    }
            } else {
                it.isEnabled = true
            }
        }
        return binding.root
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}