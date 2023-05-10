package com.example.todolistnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.todolistnavigation.adapter.AffairsAdapter
import com.example.todolistnavigation.databinding.FragmentSecondBinding
import com.example.todolistnavigation.model.AffairsModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private var newTaskText: EditText? = null
    private var newTaskSaveButton: Button? = null
    private lateinit var adapter: AffairsAdapter
    private var affairsList: MutableList<AffairsModel> = ArrayList<AffairsModel>()

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        affairsList = (this.activity as MainActivity).affairsList
        adapter = (this.activity as MainActivity).adapter
        newTaskText = getView()?.findViewById(R.id.newTaskText)
        newTaskSaveButton = getView()?.findViewById(R.id.button_second)
        val finalIsUpdate = false
        newTaskSaveButton!!.setOnClickListener(View.OnClickListener {
            val text = newTaskText!!.getText().toString()
            if (finalIsUpdate) {
                //db!!.updateTask(bundle!!.getInt("id"), text)
            } else {
                val affair = AffairsModel()
                affair.affairs = text
                affair.status = false
                affairsList.reverse()
                affairsList.add(affair)
                affairsList.reverse()
                adapter.setList(affairsList)
                adapter.notifyDataSetChanged()
                //db!!.insertTask(task)
            }
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}