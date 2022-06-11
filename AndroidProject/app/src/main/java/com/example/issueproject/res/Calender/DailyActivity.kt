package com.example.issueproject.res.Calender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.issueproject.Adapter.DailyAdapter
import com.example.issueproject.Adapter.DailyDateAdapter
import com.example.issueproject.R
import com.example.issueproject.databinding.ActivityDailyBinding
import com.example.issueproject.databinding.ActivityMenuBinding
import com.example.issueproject.dto.*
import com.example.issueproject.res.Add.DailyAddActivity
import com.example.issueproject.res.Medicine.Parents_MedicineInfo
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "DailyActivity"
class DailyActivity : AppCompatActivity() {
    private lateinit var dailyAdapter: DailyAdapter
    private val binding by lazy{
        ActivityDailyBinding.inflate(layoutInflater)
    }
    lateinit var school : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        school = intent.getStringExtra("school").toString()
        lateinit var data : CalenderSelect
        data.school = school
        selectCalender(data)

        binding.floatingActionButtonAddDaily.setOnClickListener {
            var intent = Intent(this, DailyAddActivity::class.java)
            startActivity(intent)
        }
    }
    fun initCalendar(data : MutableList<CalenderResult>){
        var date = arrayListOf<String>("2022년 05월 24일","2021년 05월 28일","2021년 05월 12일")
        var title = arrayListOf<String>("일정1","일정2","일정3")
        var content = arrayListOf<String>("red","blue","green")
        dailyAdapter = DailyAdapter(this, data)
        findViewById<RecyclerView>(R.id.customCalendar).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = dailyAdapter
            scrollToPosition(Int.MAX_VALUE/2)

        }


                dailyAdapter.setItemClickListener(object : DailyAdapter.OnItemClickListener {
                    override fun onClick(int : Intent) {
                        var intent = int

                        startActivity(intent)
                    }
                })

        val snap = PagerSnapHelper()
        if(findViewById<RecyclerView>(R.id.customCalendar).onFlingListener == null){
            snap.attachToRecyclerView(findViewById<RecyclerView>(R.id.customCalendar))
        }
    }
    fun selectCalender(data : CalenderSelect){
        ResponseService().SelectCalender(data, object : RetrofitCallback<MutableList<CalenderResult>> {
            override fun onError(t: Throwable) {
                Log.d(TAG, "onError: $t")
            }

            override fun onSuccess(code: Int, responseData: MutableList<CalenderResult>) {

                Log.d(TAG, "onSuccess: $responseData")
                initCalendar(responseData)
            }

            override fun onFailure(code: Int) {
                Log.d(TAG, "onFailure: $code")
            }

        })
    }

}