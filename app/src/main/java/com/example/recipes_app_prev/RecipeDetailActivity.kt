package com.example.recipes_app_prev

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide



class RecipeDetailActivity : AppCompatActivity() {
    private val backButton = R.id.backButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_detail_activity)


        val imageUrl = intent.getStringExtra("imageUrl")
        val recipeName = intent.getStringExtra("recipeName")
        val instructions = intent.getStringExtra("instructions")
       val ingredients = intent.getStringExtra("ingredients")
        val measures = intent.getStringExtra("measures")


        val backButton = findViewById<ImageButton>(backButton)
        val imageRecipe = findViewById<ImageView>(R.id.image_recipe)
        val textRecipeName = findViewById<TextView>(R.id.text_recipe_name)
        val textRecipeInstructions = findViewById<TextView>(R.id.text_recipe_instructions)
        val textRecipeIngredients = findViewById<TextView>(R.id.text_recipe_ingredients)


        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(imageRecipe)


        // Construct a string representation of ingredients
        textRecipeName.text = recipeName
        textRecipeInstructions.text = instructions

        // Display ingredients in TextView
       textRecipeIngredients.text = ingredients


        backButton.setOnClickListener {
            finish()
        }

    }

}

