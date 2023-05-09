package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ThirdFragment {

    /**
     * A simple [Fragment] subclass.
     * Use the [TaskFragment.newInstance] factory method to
     * create an instance of this fragment.
     */
    class TaskFragment : Fragment() {

        private var param1: String? = null
        private var param2: String? = null

        //private var text: TextureView


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.info, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            var text: TextView = view.findViewById(R.id.infotext)
            text.setText("more information about " + requireArguments().getString("text"))
        }
        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment TaskFragment.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                TaskFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }

        //    private fun clickOnActionHandler(id:Int) {
//        val action = TaskFragmentDirectios.actionTasksListFragmentToDetailFragment(id)
//        findNavController().navigate(R.id.action_FirstFragment_to_TaskFragment)
//    }
    }
}