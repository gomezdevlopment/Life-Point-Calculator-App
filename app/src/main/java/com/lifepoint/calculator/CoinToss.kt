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
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var soundFX: MediaPlayer? = null

/**
 * A simple [Fragment] subclass.
 * Use the [CoinToss.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoinToss : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coin_toss, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coinTossResult: TextView = view.findViewById(R.id.coinTossResult)
        val flipCoinButton: Button = view.findViewById(R.id.flipCoinButton)
        val coinImage: ImageView = view.findViewById(R.id.coinImage)

        flipCoinButton.setOnClickListener {
            flipCoin(coinImage, flipCoinButton, coinTossResult)
        }
    }

    private fun flipCoin(coin: ImageView, button: Button, textView: TextView){
        soundFX = MediaPlayer.create(view?.context, R.raw.coin_sound_fx_1)
        soundFX?.start()
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
            soundFX?.stop()
            soundFX?.release()
            button.isClickable = true
        }.start()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CoinToss.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CoinToss().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}