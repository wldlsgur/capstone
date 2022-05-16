package com.example.issueproject.res.Medicine
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.issueproject.Adapterimport.MedicineListAdapter
import com.example.issueproject.databinding.ActivityMedicineListBinding
import com.example.issueproject.dto.MedicineManage
import com.example.issueproject.dto.MedicineManagementResult
import com.example.issueproject.res.Medicine.Parents_MedicineInfo
import com.example.issueproject.retrofit.RetrofitCallback
import com.example.issueproject.service.ResponseService

private const val TAG = "SchoolTeacherActivity"
class ParentsMedicineList : AppCompatActivity() {
    lateinit var MedicineListAdapter: MedicineListAdapter

    private val binding by lazy {
        ActivityMedicineListBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val id = intent.getStringExtra("id").toString()
        val cname = intent.getStringExtra("cname").toString()
        ShowRecycler(id, cname)

        binding.medicinelistButtonAdd.setOnClickListener {
            val add : Boolean = true
            var intent = Intent(this@ParentsMedicineList, Parents_MedicineInfo::class.java).apply {
                putExtra("add", add)
            }
            startActivity(intent)
        }
    }

    private fun initRecycler(list: MutableList<MedicineManagementResult>) {
        MedicineListAdapter = MedicineListAdapter(list)
        binding.RoomMedicineListRV.apply {
            adapter = MedicineListAdapter
            layoutManager = LinearLayoutManager(context)

            MedicineListAdapter.setItemClickListener(object: MedicineListAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    val add : Boolean = false
                    var intent = Intent(this@ParentsMedicineList, Parents_MedicineInfo::class.java).apply {
                        putExtra("add", add)
                        putExtra("id",MedicineListAdapter.MedicineListViewHolder(v).id.toString())
                        putExtra("cname", MedicineListAdapter.MedicineListViewHolder(v).cname.toString())
                        putExtra("mname", MedicineListAdapter.MedicineListViewHolder(v).mname.toString())
                    }
                    startActivity(intent)
                }
            })
        }
    }

    private fun ShowRecycler(id: String, child_name : String) {
        ResponseService().ParentsMedicineListShow(
            id, child_name,
            object : RetrofitCallback<MutableList<MedicineManagementResult>> {
                override fun onError(t: Throwable) {
                    Log.d(TAG, "onError: $t")
                }

                override fun onSuccess(code: Int, responseData: MutableList<MedicineManagementResult>) {
                    Log.d(TAG, "onSuccess: $responseData")
                    initRecycler(responseData)
                }

                override fun onFailure(code: Int) {
                    Log.d(TAG, "onFailure: $code")
                }

            })

    }
}