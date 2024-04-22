package com.example.recipes_app_prev

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipes_app_prev.SavedItems


class SavedAdapter(private val savedList: MutableList<List<String>>,
                   private val listener: OnItemClickListener
): RecyclerView.Adapter<SavedAdapter.ViewHolder>(){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val saveImage: ImageView
        val saveName: TextView
        val unsaveButton: Button


        init {
            // Find our RecyclerView item's ImageView for future use
            saveImage = view.findViewById(R.id.save_image)
            saveName = view.findViewById(R.id.save_name)
            unsaveButton=view.findViewById(R.id.unsave)
            view.setOnClickListener {

            }

    }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount()= savedList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val saveName = holder.saveName
        Glide.with(holder.itemView)
            .load(savedList[position][0])
            .centerCrop()
            .into(holder.saveImage)

        saveName.text = savedList[position][1]

        holder.unsaveButton.setOnClickListener {
            val itemToRemove =
                savedList[holder.adapterPosition] // Get the item to remove from the savedList
            savedList.remove(itemToRemove) // Remove the sublist from the list used in the adapter
            SavedItems.deleteItem(itemToRemove) // Remove the sublist from the SavedItems object
            notifyItemRemoved(holder.adapterPosition) // Notify the adapter about the removal of the item
        }




            holder.itemView.setOnClickListener {
                // Pass all details including area for detailed view
                listener.onItemClick(savedList[holder.adapterPosition][0], savedList[holder.adapterPosition][1], savedList[holder.adapterPosition][3], savedList[holder.adapterPosition][4])
            }


        }

        interface OnItemClickListener {
            fun onItemClick(imageUrl: String, recipeName: String, instructions: String, ingredients: String)
        }

    }



