package com.example.recipes_app_prev

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView





class SavedActivity: AppCompatActivity() {
    private lateinit var rvSaved: RecyclerView
    private lateinit var savedList: MutableList<List<String>>
    lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.saved_activity)
        rvSaved = findViewById(R.id.saved_list)
        savedList= SavedItems.getItems()

        val adapter = SavedAdapter(savedList,object : SavedAdapter.OnItemClickListener {
            override fun onItemClick(imageUrl: String, recipeName: String, recipeInstructions: String, ingredients: String) {
                val intent = Intent(this@SavedActivity, RecipeDetailActivity::class.java)
                intent.putExtra("imageUrl", imageUrl)
                intent.putExtra("recipeName", recipeName)
                intent.putExtra("instructions", recipeInstructions)
                intent.putExtra("ingredients", ingredients)
                startActivity(intent)
            }
        })
        rvSaved.adapter = adapter
        rvSaved.layoutManager = LinearLayoutManager(this@SavedActivity)
        searchButton=findViewById(R.id.search)


        searchButton.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }


}
