package com.lifepoint.calculator

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private var sound: MediaPlayer? = null
private var soundFX: Boolean = true

class CoinToss : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coin_toss, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = activity?.getSharedPreferences("preferences", AppCompatActivity.MODE_PRIVATE)
        soundFX = preferences?.getBoolean("soundFX", true) == true

        val coinTossResult: TextView = view.findViewById(R.id.coinTossResult)
        val flipCoinButton: Button = view.findViewById(R.id.flipCoinButton)
        val coinImage: ImageView = view.findViewById(R.id.coinImage)

        flipCoinButton.setOnClickListener {
            flipCoin(coinImage, flipCoinButton, coinTossResult)
        }
    }

    private fun flipCoin(coin: ImageView, button: Button, textView: TextView){
        if(soundFX){
            sound = MediaPlayer.create(view?.context, R.raw.coin_sound_fx_1)
            sound?.start()
        }
        var result = "Heads"
        var resultImage = R.drawable.ic_heads
        //Basic Coin Toss Logic - Random number between 0 and 1
        val randomNumber: Int = (0..1).random()
        if(randomNumber == 1){
            result = "Tails"
            resultImage = R.drawable.ic_tails
        }
        coin.animate().apply {
            duration = 500
            rotationXBy(1800f)
            button.isClickable = false
        }.withEndAction{
            coin.setImageResource(resultImage)
            textView.text = result
            if(sound!=null){
                sound?.stop()
                sound?.release()
            }
            button.isClickable = true
        }.start()
    }
}