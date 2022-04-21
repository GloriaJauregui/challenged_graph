package pruebas.gloriajaureguiapp.app.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import pruebas.gloriajaureguiapp.AppActivity
import pruebas.gloriajaureguiapp.R
import pruebas.gloriajaureguiapp.databinding.FragmentLoginBinding
import java.util.concurrent.Executor

/**
 * Muestra el login donde se ingresa por biometrico.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { ctx ->
            executor = ContextCompat.getMainExecutor(ctx)
        }
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(context, getString(R.string.msg_authentication_failed) , Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(context,
                        getString(R.string.msg_authentication_succeeded), Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(context, AppActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context,  getString(R.string.msg_authentication_failed),
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.lbl_login_title))
            .setSubtitle(getString(R.string.lbl_used))
            .setNegativeButtonText(getString(R.string.lbl_cancel))
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners(){
        binding.ivBiometric.setOnClickListener {
           biometricPrompt.authenticate(promptInfo)
        }
    }

}