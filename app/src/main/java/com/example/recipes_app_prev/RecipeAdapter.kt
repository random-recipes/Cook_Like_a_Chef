package com.example.recipes_app_prev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecipeAdapter( private val recipeList: MutableList<List<String>>) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeImage: ImageView
        val recipeName: TextView
        val recipeArea: TextView

        init {
            // Find our RecyclerView item's ImageView for future use
            recipeImage = view.findViewById(R.id.recipe_image)
            recipeName = view.findViewById(R.id.recipe_name)
            recipeArea = view.findViewById(R.id.recipe_area)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipes_card, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipeName = holder.recipeName
        val recipeArea = holder.recipeArea
        Glide.with(holder.itemView)
            .load(recipeList[position][0])
            .centerCrop()
            .into(holder.recipeImage)

        recipeName.text = recipeList[position][1]
        recipeArea.text = recipeList[position][2]
    }
    override fun getItemCount() = recipeList.size
}