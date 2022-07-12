package br.com.zup.chucknorrisjokeapi.ui.cathegoryjoke.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.chucknorrisjokeapi.data.model.JokeResponse
import br.com.zup.chucknorrisjokeapi.databinding.JokeItemBinding

class JokeAdapter(
    private var listJoke: MutableList<JokeResponse> = mutableListOf(),
) : RecyclerView.Adapter<JokeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val joke = listJoke[position]
        holder.showCharacterInfo(joke)

    }

    override fun getItemCount() = listJoke.size

    fun updateListJoke(newList: MutableList<JokeResponse>) {
        listJoke = newList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: JokeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun showCharacterInfo(jokeResponse: JokeResponse) {
            binding.tvJoke.text = jokeResponse.value
        }
    }
}