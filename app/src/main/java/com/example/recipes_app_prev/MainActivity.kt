package com.example.recipes_app_prev

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private var recipeImageUrl = ""
    private var recipeName = ""
    private var recipeArea = ""
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var clearButton: Button
    private lateinit var categoriesSpinner: Spinner
    private lateinit var recipeList: MutableList<List<String>>
    private lateinit var rvRecipes: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private var categoriesList = mutableListOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipeList = mutableListOf()
        rvRecipes = findViewById(R.id.recipe_list)
        recipeAdapter = RecipeAdapter(recipeList)
        rvRecipes.adapter = recipeAdapter
        rvRecipes.layoutManager = LinearLayoutManager(this)

        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
        clearButton = findViewById(R.id.clearButton)
        categoriesSpinner = findViewById(R.id.categoriesSpinner)

        fetchCategories()

        searchButton.setOnClickListener {
            val mealName = searchEditText.text.toString().trim()
            if (mealName.isNotEmpty()) {
                searchMeal(mealName)
            } else {
                Toast.makeText(this, "Please enter a meal name to search.", Toast.LENGTH_SHORT).show()
            }
        }

        clearButton.setOnClickListener {
            searchEditText.text.clear()
            reloadInitialData()
        }

        categoriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCategory = categoriesList[position]
                fetchMealsByCategory(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        getRVRecipeInfo()
    }

    private fun fetchCategories() {
        val url = "https://www.themealdb.com/api/json/v1/1/categories.php"
        val client = AsyncHttpClient()
        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val categoriesArray = json.jsonObject.getJSONArray("categories")
                for (i in 0 until categoriesArray.length()) {
                    val category = categoriesArray.getJSONObject(i)
                    val categoryName = category.getString("strCategory")
                    categoriesList.add(categoryName)
                }
                val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, categoriesList)
                categoriesSpinner.adapter = adapter
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.d("Category Fetch Error", errorResponse)
            }
        })
    }

    private fun fetchMealsByCategory(category: String) {
        val url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=$category"
        val client = AsyncHttpClient()
        client.get(url, object : JsonHttpResponseHandler() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                recipeList.clear()
                val mealsArray = json.jsonObject.getJSONArray("meals")
                for (i in 0 until mealsArray.length()) {
                    val meal = mealsArray.getJSONObject(i)
                    recipeImageUrl = meal.getString("strMealThumb")
                    recipeName = meal.getString("strMeal")
                    recipeArea = ""  // Area information may not be available in this call
                    val recipeInfoList = listOf(recipeImageUrl, recipeName, recipeArea)
                    recipeList.add(recipeInfoList)
                }
                recipeAdapter.notifyDataSetChanged()
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.d("Meals Fetch Error", errorResponse)
            }
        })
    }

    private fun searchMeal(mealName: String) {
        val url = "https://www.themealdb.com/api/json/v1/1/search.php?s=$mealName"
        val client = AsyncHttpClient()
        client.get(url, object : JsonHttpResponseHandler() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                recipeList.clear()
                val mealsArray = json.jsonObject.getJSONArray("meals")
                for (i in 0 until mealsArray.length()) {
                    val meal = mealsArray.getJSONObject(i)
                    recipeImageUrl = meal.getString("strMealThumb")
                    recipeName = meal.getString("strMeal")
                    recipeArea = meal.getString("strArea")
                    val recipeInfoList = listOf(recipeImageUrl, recipeName, recipeArea)
                    recipeList.add(recipeInfoList)
                }
                recipeAdapter.notifyDataSetChanged()
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.d("Search Failure", errorResponse)
                Toast.makeText(this@MainActivity, "Failed to fetch data: $errorResponse", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun reloadInitialData() {
        recipeList.clear()
        getRVRecipeInfo()
    }


    private fun getRVRecipeInfo() {
        val client = AsyncHttpClient()
        for (id in 1..300) {
            client["https://www.themealdb.com/api/json/v1/1/random.php", object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                    Log.d("Recipe Card Success", "$json")
                    val meal = json.jsonObject.getJSONArray("meals").getJSONObject(0)
                    val recipeImageUrl = meal.getString("strMealThumb")
                    val recipeName = meal.getString("strMeal")
                    val recipeArea = meal.getString("strArea")
                    val recipeInstructions = meal.getString("strInstructions")
                    val ingredients = StringBuilder()
                    for (i in 1..20) { // Assuming there are up to 20 ingredients
                        val ingredient = meal.optString("strIngredient$i", "")
                        if (ingredient.isNotBlank()) {
                            ingredients.append("- $ingredient\n")
                        }
                    }
                    val recipeInfoList = listOf(recipeImageUrl, recipeName, recipeArea, recipeInstructions, ingredients.toString())
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
