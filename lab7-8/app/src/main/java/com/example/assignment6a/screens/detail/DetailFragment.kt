package com.example.assignment6a.screens.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIconDefaults.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.assignment6a.APP
import com.example.assignment6a.R
import com.example.assignment6a.databinding.FragmentDetailBinding
import com.example.assignment6a.models.TaskModel
import com.example.chartinglibrary.area.AreaFeed
import com.example.chartinglibrary.candle.CandleFeed
import com.example.chartinglibrary.candle.CandlestickChart
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.assignment6a.MainActivity
import com.example.assignment6a.REPOSITORY
import com.example.assignment6a.screens.todolist.ToDoListFragment
import com.example.assignment6a.screens.todolist.ToDoListViewModel
import com.example.chartinglibrary.TimeData
import com.example.chartinglibrary.area.AreaChart

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    lateinit var currentTicker: String

    var toDoList = emptyList<TaskModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        currentTicker = arguments?.getSerializable("task") as String
//        return binding.root


        return ComposeView(requireContext()).apply {
            setContent {
                val viewModel = ViewModelProvider(this@DetailFragment).get(DetailViewModel::class.java)

                val reDraw = remember{ mutableStateOf(false) }
                val liveUpdate = remember{ mutableStateOf(false) }

                val areaFeed = remember { mutableStateOf(mutableListOf<AreaFeed>()) }

                viewModel.initDatabase()

                val timeFormat = remember { mutableStateOf(listOf<String>()) }
                val selectedTimeFormat = remember { mutableStateOf(listOf<String>()) }

                //viewModel.updateFeed(TaskModel(title = "title", description = "description", feed = mutableListOf(125f))) {}

                //val isFull = viewModel.getAllTasks().value.isNullOrEmpty()
                //if (viewModel.getAllTasks().value!![0].feed.isNullOrEmpty()) {
                //Log.d("debug", isFull.toString())
                Log.d("debug", viewModel.getAllTasks().value.toString())

                viewModel.getAllTasks().observe(viewLifecycleOwner) { list ->
                    toDoList = list
                    for (element in list) {
                        Log.d("debug", element.feed.toString())
                        Log.d("debug", element.time.toString())
                    }
                    if (list.isNotEmpty()) {
                        val area = mutableListOf<AreaFeed>()
                        for (i in 0 until list[list.size - 1].feed.size) {
                            area.add(
                                AreaFeed(
                                    list[list.size - 1].feed[i].toFloat(),
                                    list[list.size - 1].time[i]
                                    //"1686491916".toString()
                                )
                            )
                        }
                        areaFeed.value = area
                        timeFormat.value = list[list.size - 1].timeFormat
                    }
                }


                //val ticker = remember{ mutableStateOf("NVDA") }
                val ticker = remember{ mutableStateOf(currentTicker) }
                val from = remember{ mutableStateOf("2023-03-01") }
                val to = remember{ mutableStateOf("2023-05-20") }
                val timespan = remember{ mutableStateOf("hour") }

                val chartWidth = remember{ mutableStateOf(1f) }
                val chartHeight = remember{ mutableStateOf(0.5f) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier
                        .verticalScroll(rememberScrollState())
                    ) {
                        AreaChart(
                            areaFeed = areaFeed.value,
                            timeFormat = timeFormat.value,
                            selectedTimeFormat = selectedTimeFormat.value,
                            chartWidth = GetWidth() * chartWidth.value,
                            chartHeight = GetHeight() * chartHeight.value,
                        )
                        Row {
                            TextField(
                                modifier = Modifier
                                    .width(GetWidth() / 2),
                                label = {Text("Ticker")},
                                value = ticker.value,
                                onValueChange = { newText -> ticker.value = newText },
                                placeholder = { Text("AAPL") }
                            )
                            var expandedTimespan by remember { mutableStateOf(false) }
                            val timespanList = listOf("minute", "hour", "day", "week", "month", "quarter", "year")
                            Column {
                                TextField(
                                    enabled = false,
                                    value = timespan.value,
                                    onValueChange = { timespan.value = it },
                                    modifier = Modifier
                                        .clickable { expandedTimespan = !expandedTimespan }
                                        .fillMaxWidth(),
                                    label = {Text(text = "Timespan")},
                                )
                                DropdownMenu(
                                    expanded = expandedTimespan,
                                    onDismissRequest = { expandedTimespan = false }
                                ) {
                                    timespanList.forEach { label ->
                                        DropdownMenuItem(onClick = {
                                            timespan.value = label
                                            expandedTimespan = false
                                        }) {
                                            Text(text = label)
                                        }
                                    }
                                }
                            }
                        }
                        Row {
                            TextField(
                                modifier = Modifier
                                    .width(GetWidth() / 2),
                                label = {Text("From")},
                                value = from.value,
                                onValueChange = { newText -> from.value = newText },
                                placeholder = { Text("2023-03-01") }
                            )
                            TextField(
                                modifier = Modifier
                                    .width(GetWidth() / 2),
                                label = {Text("To")},
                                value = to.value,
                                onValueChange = {newText -> to.value = newText},
                                placeholder = { Text("2023-05-20") }
                            )
                        }
                        Button(modifier = Modifier
                        .width(GetWidth()),
                        onClick = {
                            //mainHandler.removeCallbacksAndMessages(null)
                            selectedTimeFormat.value = listOf(TimeData.DAY.index, " ", TimeData.MONTH_SHORT.index, " ", TimeData.HOUR.index, ":", TimeData.MINUTE.index)
                            viewModel.requestData(ticker.value, "1", timespan.value, from.value, to.value, "50000", areaFeed, timeFormat, reDraw)
                        }){
                        Text(text = "Upload Stock Data")
                        }

                        Button(modifier = Modifier
                            .width(GetWidth()),
                            onClick = {
                                //mainHandler.removeCallbacksAndMessages(null)
                                val feed = mutableListOf<Float>()
                                val time = mutableListOf<String>()
                                for (i in 0 until areaFeed.value.size) {
                                    feed.add(areaFeed.value[i].price)
                                    time.add(areaFeed.value[i].time)
                                }
                                for (el in toDoList) {
                                    viewModel.delete(el) {}
                                }
                                //viewModel.delete(toDoList[0]) {}
                                viewModel.updateFeed(TaskModel(title = "title", description = "description", feed = feed, time = time,  timeFormat = timeFormat.value)) {}
                            }){
                            Text(text = "Save Data")
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init()
    }



    private fun init() {
        val viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
//        binding.detailTitle.text = currentTask.title
//        binding.detailInfo.text = currentTask.description

        binding.detailTitle.text = currentTicker
        binding.detailInfo.text = currentTicker

//        binding.btnDelete.setOnClickListener {
//            viewModel.delete(currentTask) {}
//            APP.navController.navigate(R.id.action_detailFragment_to_toDoListFragment)
//        }
        binding.btnBack.setOnClickListener {
            APP.navController.navigate(R.id.action_detailFragment_to_toDoListFragment)
        }
    }
}

fun delTask(taskModel: TaskModel) {
    //viewModel.delete(taskModel) {}
    REPOSITORY.delTask(taskModel) {

    }
}

@Composable
fun GetWidth(): Dp {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp.dp
}

@Composable
fun GetHeight(): Dp {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp.dp
}