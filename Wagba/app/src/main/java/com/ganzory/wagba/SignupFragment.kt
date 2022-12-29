package com.ganzory.wagba

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ganzory.wagba.databinding.FragmentSignupBinding
import com.ganzory.wagba.shared.AuthValidator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var auth: FirebaseAuth
    private var databaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var validator: AuthValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)

        // Register the validator
        validator = AuthValidator(
            binding.tfEmail, binding.tfPassword,
            validEmailOrg = getString(R.string.valid_email_org),
            validEmailPattern = getString(R.string.valid_email_pattern),
            validPassShort = getString(R.string.valid_pass_short),
        )

        // Signup
        binding.btnSignupIn.setOnClickListener {
            it.isEnabled = false
            // Validation
            val (valid, email, password) = validator.validate()
            if (!valid) return@setOnClickListener
            if (binding.tfName.text.toString().length < 3) {
                binding.tfName.error = getString(R.string.valid_name)
                return@setOnClickListener
            }
            // If valid
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Go home
                        Log.d(TAG, "sign up : success")
                        auth.currentUser!!.updateProfile(
                            com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                .setDisplayName(binding.tfName.text.toString()).build()
                        )
                        startActivity(Intent(activity, HomeActivity::class.java))
                        activity?.finish()
                    } else {
                        // Failed
                        Log.w(TAG, "sign up : failure", task.exception)
                        val msg = getString(
                            when (task.exception) {
                                is FirebaseAuthUserCollisionException -> R.string.valid_auth_collision
                                else -> R.string.msg_error_general
                            }
                        )
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
                    }
                    it.isEnabled = true
                }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignupFragment()
        private const val TAG = "SignupFragment"
    }
}