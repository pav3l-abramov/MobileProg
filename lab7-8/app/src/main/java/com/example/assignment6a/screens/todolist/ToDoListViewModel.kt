package com.example.assignment6a.screens.todolist

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

class ToDoListViewModel(application: Application): AndroidViewModel(application) {
    val context = application
    var allTickers: MutableList<String> = mutableListOf()

    private fun requestTickers(
        //tickers: MutableList<String>,
        //reDraw: MutableState<Boolean>
    ) {
        //https://api.polygon.io/v3/reference/tickers?market=stocks&active=true&limit=1000&apiKey=fVYFzwwxhgYGobQCWje8h9oYE5pufXvm
        val url = "https://api.polygon.io/v3/reference/tickers?market=stocks&active=true&limit=1000&apiKey=$API_KEY"
        val queue = Volley.newRequestQueue(this.context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                //result -> Log.d("debug", "Result: $result")
                //result -> parseData(result)
                    result -> allTickers = parseTickersData(result)

                //reDraw.value = !reDraw.value
            },
            {
                    error -> Log.d("debug", "Request Error: $error")
            }
        )
        queue.add(request)
    }

    private fun parseTickersData(result: String): MutableList<String> {
        val root = JSONObject(result)
        try {
            val results = root.getJSONArray("results")
            val allTickers = mutableListOf<String>()
            Log.d("debug", results.length().toString())
            for (i in 0 until results.length()) {
                val currentTicker = results.getJSONObject(i)
                allTickers.add(
                        currentTicker.getString("ticker").toString(),
                )
            }
            //showList(allTickers)
            if (allTickers.size > 3000) allTickers.removeRange(3000..allTickers.size)
            return allTickers
        } catch (e: JSONException) {
            return mutableListOf<String>()
        }
    }

    fun showList(list: MutableList<String>) {
        for (row in list) {
            Log.d("debug", row.toString())
        }
    }

    fun initDatabase() {
        requestTickers();
//        val toDoDao = ToDoDatabase.getInstance(context).getToDoDao()
//        REPOSITORY = ToDoRealization(toDoDao)
    }

//    fun getAllTasks(): LiveData<List<TaskModel>> {
//        return REPOSITORY.allTasks
//    }

    fun getAllTasks(): MutableList<String> {
        return allTickers
    }

    fun delete(taskModel: TaskModel, onSuccess:() -> Unit) =
        viewModelScope.launch (Dispatchers.IO) {
            REPOSITORY.deleteTask(taskModel) {
                onSuccess()
            }
        }

//    fun del(taskModel: TaskModel, onSuccess:() -> Unit) {
//        REPOSITORY.delTask(taskModel) {
//            onSuccess()
//        }
//        viewModelScope.launch (Dispatchers.IO) {
//            REPOSITORY.deleteTask(taskModel) {
//                onSuccess()
//            }
//        }
//    }

//    fun updateActionCheckStatus(taskModel: TaskModel, onSuccess:() -> Unit) {
//        val newTaskModel = taskModel.copy(checkStatus = !taskModel.status)
//        // action.checkStatus = !action.checkStatus // areContentsTheSame returns true in this case, so don't use it
//        viewModelScope.launch (Dispatchers.IO) {
//            REPOSITORY.updateTask(newTaskModel) {
//                onSuccess()
//            }
//        }
//    }

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