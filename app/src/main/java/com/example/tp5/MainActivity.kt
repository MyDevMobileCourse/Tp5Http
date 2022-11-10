package com.example.tp5

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp5.api.Offre
import com.example.tp5.api.RestApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var reView : RecyclerView
    private lateinit var mAdapter : OffresListAdapter
    private lateinit var empty : CardView
    private lateinit var offreList : MutableList<Offre>
    private lateinit var add : FloatingActionButton
    private lateinit var delete : FloatingActionButton
    private lateinit var update : FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reView = findViewById(R.id.listOffres)
        reView.layoutManager = LinearLayoutManager(this)
        empty = findViewById(R.id.empty)
        delete = findViewById(R.id.delete)
        update = findViewById(R.id.update)
        offreList = ArrayList()
        mAdapter = OffresListAdapter(this, offreList,R.layout.offre)
        reView.adapter = mAdapter
        add = findViewById(R.id.add)
        add.setOnClickListener(){
            goToForm()
        }
        mAdapter.onItemClick = { offre, position ->
            delete.visibility = View.VISIBLE
            update.visibility = View.VISIBLE
            delete.setOnClickListener(){
                deleteOffre(offre,position)
            }
            update.setOnClickListener(){
                updateOffre(offre)
            }
        }
        fetchOffres()
    }

    fun goToForm(){
        val intent = Intent(this, Form::class.java)
        resultLauncher.launch(intent)

    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        fetchOffres()
        Toast.makeText(this, "Offre ajoutée", Toast.LENGTH_SHORT).show()
    }

    fun fetchOffres(){
        val apiService= RestApiService()
        val call = apiService.fetchOffres()
        call.enqueue(
            object : Callback<List<Offre>> {
                override fun onFailure(call: Call<List<Offre>>, t: Throwable) {
                    reView.isVisible = false
                    empty.isVisible = true
                }

                override fun onResponse(call: Call<List<Offre>>, response: Response<List<Offre>>) {
                    if (response.isSuccessful) {
                        val offres = response.body()
                        if (offres != null) {
                            offreList.clear()
                            offreList.addAll(offres)
                            mAdapter.notifyDataSetChanged()
                            reView.adapter = mAdapter
                            if (offreList.isEmpty()) {
                                empty.visibility = CardView.VISIBLE
                                reView.visibility = RecyclerView.GONE
                            } else {
                                empty.visibility = CardView.GONE
                                reView.visibility = RecyclerView.VISIBLE
                            }
                        }
                    }
                }
            }
        )

    }
    fun deleteOffre(offre : Offre, position : Int){
        if(offre.code != null){
            AlertDialog.Builder(this)
                .setTitle("Supprimer")
                .setMessage("Voulez-vous vraiment supprimer cette offre?")
                .setPositiveButton("Oui") { dialog, which ->
                    val apiService = RestApiService()
                    apiService.deleteOffre(offre.code.toInt()).enqueue(
                        object : retrofit2.Callback<Boolean> {
                            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                offreList.removeAt(position)
                                mAdapter.notifyItemRemoved(position)
                                mAdapter.notifyItemRangeChanged(position, offreList.size)
                            }
                            override fun onResponse( call: Call<Boolean>, response: Response<Boolean>) {
                                offreList.removeAt(position)
                                mAdapter.notifyItemRemoved(position)
                                mAdapter.notifyItemRangeChanged(position, offreList.size)
                            }
                        }
                    )
                }
                .setNegativeButton("Non", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
            delete.visibility = View.GONE
            update.visibility = View.GONE
        }
    }

    fun updateOffre(offre : Offre){
        if(offre.code != null){
            val intent = Intent(this, Form::class.java)
            intent.putExtra("code", offre.code.toInt())
            intent.putExtra("intitulé", offre.intitulé)
            intent.putExtra("specialité", offre.specialité)
            intent.putExtra("société", offre.société)
            intent.putExtra("nbpostes", offre.nbpostes!!.toInt())
            intent.putExtra("logo", offre.logo)
            intent.putExtra("pays", offre.pays)
            startActivity(intent)
            delete.visibility = View.GONE
            update.visibility = View.GONE
        }
    }

}