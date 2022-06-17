package com.ildus.debts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ildus.debts.databinding.FragmentAddDeptBinding
import com.ildus.debts.databinding.FragmentMainBinding
import com.ildus.debts.ui.main.PlaceholderFragment

class AddDeptFragment : Fragment() {
    private var _binding: FragmentAddDeptBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddDeptBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.button.setOnClickListener {
            val pref = context!!.getSharedPreferences("depts", Context.MODE_PRIVATE)
            val editor = pref?.edit()
            val size = pref.getInt("size", 0).toString()
            editor?.putInt("count" + size, binding.countAdd.text.toString().toIntOrNull()  ?: 0)
            editor?.putString("whoDept" + size, binding.whoDeptAdd.text.toString() ?: "")
            editor?.putInt("size", size.toInt() + 1)
            editor?.apply()

            Toast.makeText(context, R.string.toast_text_added, Toast.LENGTH_SHORT).show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}