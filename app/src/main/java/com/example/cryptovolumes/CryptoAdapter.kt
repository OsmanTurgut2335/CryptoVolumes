package com.example.cryptovolumes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Random

class CryptoAdapter(private val cryptoList: List<CryptoVolumeData>) : RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {


    val colors : Array<String> = arrayOf("#fcba03","#fc7303","#03fc4a","#03e7fc","#7303fc","#fc034a","#fc8803")

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cryptoNameTextView: TextView = itemView.findViewById(R.id.cryptoNameTextView)
        val volumeChangeTextView: TextView = itemView.findViewById(R.id.volumeChangeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recylerrow, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cryptoData = cryptoList[position]
        val randomInt = kotlin.random.Random.nextInt(0,7)

     //   holder.itemView.setBackgroundColor(Color.parseColor(colors[randomInt]))


       // holder.cryptoNameTextView.setBackgroundResource(R.drawable.border)

        holder.cryptoNameTextView.text = "${cryptoData.name} (${cryptoData.symbol}) "
        holder.volumeChangeTextView.text = "24 hour volume change : ${cryptoData.market_cap_change_percentage_24h}%"
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}
