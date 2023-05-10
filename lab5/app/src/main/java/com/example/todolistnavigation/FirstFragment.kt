package com.example.todolistnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistnavigation.adapter.AffairsAdapter
import com.example.todolistnavigation.databinding.FragmentFirstBinding
import com.example.todolistnavigation.model.AffairsModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var adapter: AffairsAdapter
    lateinit var recyclerView: RecyclerView
    private var affairsList: MutableList<AffairsModel> = ArrayList<AffairsModel>()
    private var addButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addItem.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        affairsList = (this.activity as MainActivity).affairsList

        adapter = (this.activity as MainActivity).adapter
        affairsList= ArrayList<AffairsModel>()
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.adapter = adapter
        addButton = view.findViewById<Button>(R.id.addItem)
        addButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //AddTask.newInstance().show(supportFragmentManager, AddTask.TAG)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}