package com.example.recipes_app_prev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var recipeImageUrl = ""
    var recipeName = ""
    var recipeArea = ""
    var recipeInstructions = ""
    private lateinit var recipeList :  MutableList<List<String>>
    private lateinit var rvRecipes : RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        recipeList = mutableListOf()
//        rvRecipes = findViewById(R.id.recipe_list)
//        recipeAdapter = RecipeAdapter(recipeList)
//
//        rvRecipes.adapter = recipeAdapter
//        rvRecipes.layoutManager = LinearLayoutManager(this@MainActivity)
//        getRVRecipeInfo()
//    }
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    recipeList = mutableListOf()
    rvRecipes = findViewById(R.id.recipe_list)
    recipeAdapter = RecipeAdapter(recipeList) { imageUrl,name, instructions ->
        val intent = Intent(this, RecipeDetailActivity::class.java)
        intent.putExtra("imageUrl", imageUrl)
        intent.putExtra("recipeName", name)
        intent.putExtra("instructions", instructions)
        startActivity(intent)
    }
    rvRecipes.adapter = recipeAdapter
    rvRecipes.layoutManager = LinearLayoutManager(this@MainActivity)
    getRVRecipeInfo()
}


    private fun getRVRecipeInfo() {
        val client = AsyncHttpClient()
        for(id in 1..300) {
            client["https://www.themealdb.com/api/json/v1/1/random.php", object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                    Log.d("Recipe Card Success", "$json")
                    recipeImageUrl = json.jsonObject.getJSONArray("meals").getJSONObject(0).getString("strMealThumb")
                    recipeName = json.jsonObject.getJSONArray("meals").getJSONObject(0).getString("strMeal")
                    recipeArea = json.jsonObject.getJSONArray("meals").getJSONObject(0).getString("strArea")
                    recipeInstructions = json.jsonObject.getJSONArray("meals").getJSONObject(0).getString("strInstructions")
                    val recipeInfoList = listOf(recipeImageUrl, recipeName, recipeArea,recipeInstructions)
                    recipeList.add(recipeInfoList)
                    recipeAdapter.notifyDataSetChanged()
                }
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    throwable: Throwable?
                ) {
                    Log.d("Recipe Card Error", errorResponse)
                }
            }]
        }
    }
}