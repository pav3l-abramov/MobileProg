package com.example.assignment6a.screens.detail

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.assignment6a.API_KEY
import com.example.assignment6a.REPOSITORY
import com.example.assignment6a.db.ToDoDatabase
import com.example.assignment6a.db.repository.ToDoRealization
import com.example.assignment6a.models.TaskModel
import com.example.chartinglibrary.TimeData
import com.example.chartinglibrary.area.AreaFeed
import com.example.chartinglibrary.candle.CandleFeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

//class DetailViewModel: ViewModel() {
class DetailViewModel(application: Application): AndroidViewModel(application) {
    val context = application

    fun delete(taskModel: TaskModel, onSuccess:() -> Unit) =
        viewModelScope.launch (Dispatchers.IO) {
            REPOSITORY.deleteTask(taskModel) {
                onSuccess()
            }
        }

    fun initDatabase() {
        Log.d("debug", "initDatabase()")
        //requestTickers();
        val toDoDao = ToDoDatabase.getInstance(context).getToDoDao()
        REPOSITORY = ToDoRealization(toDoDao)
    }

    fun getAllTasks(): LiveData<List<TaskModel>> {
        Log.d("debug", "getAllTasks()")
        return REPOSITORY.allTasks
    }

    fun updateFeed(taskModel: TaskModel, onSuccess:() -> Unit) {
        Log.d("debug", "updateFeed()")
        //val newTaskModel = taskModel.copy(checkStatus = !taskModel.status)
        //val newTaskModel = taskModel.copy(checkStatus = !taskModel.status)
        // action.checkStatus = !action.checkStatus // areContentsTheSame returns true in this case, so don't use it
        viewModelScope.launch (Dispatchers.IO) {
            //REPOSITORY.updateTask(newTaskModel) {
            //REPOSITORY.insertTask(TaskModel(title = "title", description = "description")) {
            REPOSITORY.insertTask(taskModel) {
                onSuccess()
            }
        }
    }

    fun requestData(
        ticker: String,
        multiplier: String,
        timespan: String,
        from: String,
        to: String,
        limit: String,
        areaFeed: MutableState<MutableList<AreaFeed>>,
        timeFormat: MutableState<List<String>>,
        reDraw: MutableState<Boolean>
    ) {
        //https://api.polygon.io/v2/aggs/ticker/AAPL/range/1/day/2023-01-09/2023-01-09?adjusted=true&sort=asc&limit=120&apiKey=fVYFzwwxhgYGobQCWje8h9oYE5pufXvm
        val url = "https://api.polygon.io/v2/aggs/ticker/$ticker/range/$multiplier/$timespan/$from/$to?adjusted=true&sort=asc&limit=$limit&apiKey=$API_KEY"
        val queue = Volley.newRequestQueue(this.context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                //result -> Log.d("debug", "Result: $result")
                //result -> parseData(result)
                    result ->
                val list = parseData(result)
                areaFeed.value = list

                when (timespan) {
                    "minute" -> timeFormat.value = listOf(TimeData.HOUR.index, ":00")
                    "hour" -> timeFormat.value = listOf(TimeData.DAY.index, " ", TimeData.MONTH_SHORT.index)
                    "day" -> timeFormat.value = listOf(TimeData.MONTH_FULL.index)
                    "week" -> timeFormat.value = listOf(TimeData.MONTH_SHORT.index)
                    "month" -> timeFormat.value = listOf(TimeData.YEAR.index)
                    "quarter" -> timeFormat.value = listOf(TimeData.YEAR.index)
                    "year" -> timeFormat.value = listOf(TimeData.YEAR.index)
                    else -> {
                        timeFormat.value = listOf<String>()
                    }
                }
                reDraw.value = !reDraw.value
            },
            {
                    error -> Log.d("debug", "Request Error: $error")
            }
        )
        queue.add(request)
    }

    private fun parseData(result: String): MutableList<AreaFeed> {
        val root = JSONObject(result)
        try {
            val results = root.getJSONArray("results")
            val area = mutableListOf<AreaFeed>()
            Log.d("debug", results.length().toString())
            for (i in 0 until results.length()) {
                val currentCandle = results.getJSONObject(i)
                area.add(
                    AreaFeed(
                        currentCandle.getString("o").toFloat(),
                        currentCandle.getString("t").toString()
                    )
                )
            }
            //Log.d("debug", "Result: ${candles[1][0]}")
            //showList(candles)
            if (area.size > 3000) area.removeRange(3000..area.size)
            return area
        } catch (e: JSONException) {
            return mutableListOf<AreaFeed>()
        }
    }

    inline fun <reified T> MutableList<T>.removeRange(range: IntRange) {
        val fromIndex = range.start
        val toIndex = range.last
        if (fromIndex == toIndex) {
            return
        }

        if (fromIndex >= size) {
            throw IndexOutOfBoundsException("fromIndex $fromIndex >= size $size")
        }
        if (toIndex > size) {
            throw IndexOutOfBoundsException("toIndex $toIndex > size $size")
        }
        if (fromIndex > toIndex) {
            throw IndexOutOfBoundsException("fromIndex $fromIndex > toIndex $toIndex")
        }

        val filtered = filterIndexed { i, t -> i < fromIndex || i > toIndex }
        clear()
        addAll(filtered)
    }
}