package com.doaa.mosalam.librarymanagementsystem.common

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BasicActivity<MVBinding : ViewBinding>(private val bindingInflater: (inflater: LayoutInflater) -> MVBinding) :
    AppCompatActivity() {

    private var _binding: MVBinding? = null
//    val binding:MVBinding get() = _binding as MVBinding

    val binding: MVBinding
        get() = checkNotNull(_binding) {
            "Binding is not initialized. Make sure to call setContentView() before accessing the binding."
        }

//    override fun attachBaseContext(newBase: Context) {
//        val localeUpdatedContext = LocaleManager.setLocale(newBase, LocaleManager.getLanguage(newBase))
//        super.attachBaseContext(localeUpdatedContext)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // Clear the binding reference to avoid memory leaks
    }

}
