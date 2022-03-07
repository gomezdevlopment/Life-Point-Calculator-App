package com.lifepoint.calculator

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private var sound: MediaPlayer? = null
private var soundFX: Boolean = true

class DiceRoll : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dice_roll, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = activity?.getSharedPreferences("preferences", AppCompatActivity.MODE_PRIVATE)
        soundFX = preferences?.getBoolean("soundFX", true) == true

        val diceRollResult: TextView = view.findViewById(R.id.diceRollResult)
        val rollDiceButton: Button = view.findViewById(R.id.rollDiceButton)

        rollDiceButton.setOnClickListener {
            if(sound!=null){
                sound?.stop()
                sound?.release()
            }
            if(soundFX){
                sound = MediaPlayer.create(view.context, R.raw.dice_sound_fx)
                sound?.start()
            }
            //Basic Dice Roll Logic - Random number in range 1..6
            val randomNumber: Int = (1..6).random()
            diceRollResult.text = randomNumber.toString()

        }
    }
}