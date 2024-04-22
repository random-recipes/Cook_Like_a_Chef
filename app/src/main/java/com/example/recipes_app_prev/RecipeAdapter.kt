package com.example.recipes_app_prev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecipeAdapter(
    private val recipeList: MutableList<List<String>>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeImage: ImageView = view.findViewById(R.id.recipe_image)
        val recipeName: TextView = view.findViewById(R.id.recipe_name)
        val recipeArea: TextView = view.findViewById(R.id.recipe_area)
        val saveButton: Button =view.findViewById(R.id.save)


        init {
            view.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipes_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = recipeList[position]
        Glide.with(holder.itemView.context).load(item[0]).centerCrop().into(holder.recipeImage)
        holder.recipeName.text = item[1]
        holder.recipeArea.text = item[2]

        holder.saveButton.setOnClickListener{

            var item = recipeList[position][1]
            SavedItems.addItem(recipeList[position].toList())

            Toast.makeText(holder.itemView.context, item + " Saved", Toast.LENGTH_SHORT).show()}

        holder.itemView.setOnClickListener {
            // Pass all details including area for detailed view
            listener.onItemClick(item[0], item[1], item[3], item[4])
        }


    }

    interface OnItemClickListener {
        fun onItemClick(imageUrl: String, recipeName: String, instructions: String, ingredients: String)
    }

    override fun getItemCount() = recipeList.size
}
