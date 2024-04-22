package com.example.recipes_app_prev

import android.annotation.SuppressLint
import android.content.Intent
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
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var clearButton: Button
    private lateinit var savedfromsearchButton: Button

    private lateinit var categoriesSpinner: Spinner
    private lateinit var recipeList: MutableList<List<String>>
    private lateinit var rvRecipes: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private var categoriesList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvRecipes = findViewById(R.id.recipe_list)
        recipeList = mutableListOf()
        recipeAdapter = RecipeAdapter(recipeList, object : RecipeAdapter.OnItemClickListener {
            override fun onItemClick(imageUrl: String, recipeName: String, recipeInstructions: String, ingredients: String) {
                val intent = Intent(this@MainActivity, RecipeDetailActivity::class.java)
                intent.putExtra("imageUrl", imageUrl)
                intent.putExtra("recipeName", recipeName)
                intent.putExtra("instructions", recipeInstructions)
                intent.putExtra("ingredients", ingredients)
                startActivity(intent)
            }
        })
        rvRecipes.adapter = recipeAdapter
        rvRecipes.layoutManager = LinearLayoutManager(this)

        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
        clearButton = findViewById(R.id.clearButton)
        categoriesSpinner = findViewById(R.id.categoriesSpinner)
        savedfromsearchButton=findViewById(R.id.savedfromsearch)

        fetchCategories()
        setupButtonListeners()
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
                Log.e("Category Fetch Error", errorResponse)
            }
        })
    }

    private fun setupButtonListeners() {
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
            recipeList.clear()
            recipeAdapter.notifyDataSetChanged()
        }

        categoriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCategory = categoriesList[position]
                fetchMealsByCategory(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        savedfromsearchButton.setOnClickListener{
            val intent = Intent(this, SavedActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchMealsByCategory(category: String) {
        val url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=$category"
        val client = AsyncHttpClient()
        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                recipeList.clear()
                val mealsArray = json.jsonObject.getJSONArray("meals")
                for (i in 0 until mealsArray.length()) {
                    val meal = mealsArray.getJSONObject(i)
                    val mealId = meal.getString("idMeal")
                    fetchMealDetails(mealId)
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.e("Meals Fetch Error", errorResponse)
            }
        })
    }

    private fun searchMeal(mealName: String) {
        val url = "https://www.themealdb.com/api/json/v1/1/search.php?s=$mealName"
        val client = AsyncHttpClient()
        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                recipeList.clear()
                val mealsArray = json.jsonObject.getJSONArray("meals")
                for (i in 0 until mealsArray.length()) {
                    val meal = mealsArray.getJSONObject(i)
                    val mealId = meal.getString("idMeal")
                    fetchMealDetails(mealId)
                }
                recipeAdapter.notifyDataSetChanged()
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.e("Search Failure", errorResponse)
                Toast.makeText(this@MainActivity, "Failed to fetch data: $errorResponse", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fetchMealDetails(mealId: String) {
        val url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$mealId"
        val client = AsyncHttpClient()
        client.get(url, object : JsonHttpResponseHandler() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val mealsArray = json.jsonObject.getJSONArray("meals")
                for (i in 0 until mealsArray.length()) {
                    val meal = mealsArray.getJSONObject(i)
                    val imageUrl = meal.getString("strMealThumb")
                    val name = meal.getString("strMeal")
                    val area = meal.getString("strArea")  // Fetch the area correctly
                    val instructions = meal.getString("strInstructions")
                    val ingredients = StringBuilder()

                    for (j in 1..20) {
                        val ingredient = meal.optString("strIngredient$j", "")
                        if (ingredient.isNotBlank()) ingredients.append("$ingredient, ")
                    }

                    if (ingredients.isNotEmpty()) ingredients.setLength(ingredients.length - 2)

                    val recipeInfoList = listOf(imageUrl, name, area, instructions, ingredients.toString())
                    recipeList.add(recipeInfoList)
                }
                recipeAdapter.notifyDataSetChanged()
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                Log.e("Meal Detail Fetch Error", errorResponse)
            }
        })
    }

}
