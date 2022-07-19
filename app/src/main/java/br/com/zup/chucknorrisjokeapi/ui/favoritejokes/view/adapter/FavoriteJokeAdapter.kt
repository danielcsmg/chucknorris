package br.com.zup.chucknorrisjokeapi.ui.cathegoryjoke.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.chucknorrisjokeapi.data.model.JokeResponse
import br.com.zup.chucknorrisjokeapi.databinding.JokeItemBinding

class FavoriteJokeAdapter(
    private var jokes: HashMap<String, String> = hashMapOf(),
    private val clickDelete: (idJoke: String) -> Unit
) : RecyclerView.Adapter<FavoriteJokeAdapter.ViewHolder>() {
    private var listJoke = mutableListOf<String>()
    private var listKeys = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val joke = listJoke[position]
        val keys = listKeys[position]
        holder.showCharacterInfo(joke)
        holder.binding.ivDelete.setOnClickListener {
            clickDelete(keys)
        }
    }

    override fun getItemCount() = listJoke.size

    fun updateListJoke(newList: HashMap<String, String>) {
        listJoke = newList.values.toMutableList()
        listKeys = newList.keys.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: JokeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun showCharacterInfo(joke: String) {
            binding.tvJoke.text = joke
        }
    }
}