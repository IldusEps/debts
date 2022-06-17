package com.ildus.debts.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ildus.debts.DeptsRecyclerAdapter
import com.ildus.debts.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar

import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.ItemTouchHelper
import android.view.animation.TranslateAnimation
import android.widget.AbsListView
import androidx.core.view.ViewCompat
import com.ildus.debts.MainActivity

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ildus.debts.R


class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val data: MutableList<Map<String, Any>> = mutableListOf()
    private lateinit var pref: SharedPreferences

    private var countAttach: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.listDepts.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        pref = context!!.getSharedPreferences("depts", Context.MODE_PRIVATE)
        dataUpdated()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedCourse =
                    data[viewHolder.adapterPosition]

                val position = viewHolder.adapterPosition
                val index: Int = deletedCourse["index"] as Int

                pref.edit()
                    .putString("whoDept$index", "")
                    .apply()
                data.removeAt(viewHolder.adapterPosition)
                (binding.listDepts.adapter as DeptsRecyclerAdapter).notifyItemRemoved(position)

                Snackbar.make(
                    binding.constraintLayout,
                    (deletedCourse["count"].toString() ?: "") as String + " - " + (deletedCourse["whoDept"] ?: "") as String,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(R.string.snackbar_text_undo,
                        View.OnClickListener {
                            data.add(position, deletedCourse)
                            (binding.listDepts.adapter as DeptsRecyclerAdapter).notifyItemInserted(
                                position
                            )

                            pref.edit()
                                .putString("whoDept$index", deletedCourse["whoDept"] as String?)
                                .apply()
                        }).show()
            }
        }).attachToRecyclerView(binding.listDepts)
        return root
    }

    override fun onResume() {
        super.onResume()
        dataUpdated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun dataUpdated() {
        data.clear()
        (0..pref.getInt("size", 0)).forEach { i ->
            if ((pref.getString("whoDept$i", "")?.filter { !it.isWhitespace() } ?: "") != "") {
                data.add(
                    mapOf(
                        "index" to i,
                        "count" to pref.getInt("count$i", 0),
                        "whoDept" to pref.getString("whoDept$i", "")
                    ) as Map<String, Any>
                )
            }
        }

        if (binding.listDepts.adapter is DeptsRecyclerAdapter) {
            (binding.listDepts.adapter as DeptsRecyclerAdapter).notifyDataSetChanged()
        } else {
            binding.listDepts.adapter = DeptsRecyclerAdapter(data)
        }

        binding.listDepts.setRecyclerListener { holder ->
            if ((holder.layoutPosition == 0) && (countAttach < 3)) {
                (holder as DeptsRecyclerAdapter.MyViewHolder).view.startAnimation(
                    TranslateAnimation(
                        0f, 100f,
                        0f, 0f
                    )
                        .apply {
                            duration = 500
                        })
                countAttach += 1
            }
        }
    }
}