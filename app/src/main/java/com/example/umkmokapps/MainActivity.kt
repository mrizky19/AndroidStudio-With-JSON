package com.example.umkmokapps

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umkmokapps.model.DataItem
import com.example.umkmokapps.model.ResponseUser
import com.example.umkmokapps.network.ApiConfig
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity(){

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = UserAdapter(mutableListOf())

        rv_users.setHasFixedSize(true)
        rv_users.layoutManager   = LinearLayoutManager(this)
        rv_users.adapter = adapter

        getUser()
    }
    private fun getUser() {
        var client = ApiConfig.getApiService().getListUsers("1")

        client.enqueue(object : Callback<ResponseUser>, response: Response<ResponseUser>) {
            if (response.isSuccessful) {
                val dataArray = response.body()?.data as List<DataItem>
                for (data in dataArray)
                    adapter.addUser(data)
            }
        }
    }

    override fun onFailure(call: Call<Responuser>, t: Throwable) {
        Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
        t.printStackTrace()
    }
}