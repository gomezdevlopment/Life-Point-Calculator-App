package com.lifepoint.calculator

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import android.animation.ValueAnimator
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private var sound: MediaPlayer? = null
private var soundFX: Boolean = true

class Calculator : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = activity?.getSharedPreferences("preferences", AppCompatActivity.MODE_PRIVATE)
        soundFX = preferences?.getBoolean("soundFX", true) == true

        val playerOneLifePoints: TextView = view.findViewById(R.id.playerOneLifePoints)
        val playerTwoLifePoints: TextView = view.findViewById(R.id.playerTwoLifePoints)
        val playerOneAddButton: Button = view.findViewById(R.id.playerOneAdditionButton)
        val playerOneSubtractButton: Button = view.findViewById(R.id.playerOneSubtractButton)
        val playerTwoAddButton: Button = view.findViewById(R.id.playerTwoAdditionButton)
        val playerTwoSubtractButton: Button = view.findViewById(R.id.playerTwoSubtractButton)
        val resetButton: Button = view.findViewById(R.id.resetButton)

        playerOneLifePoints.text = playerOneLifePointTotal
        playerTwoLifePoints.text = playerTwoLifePointTotal

        playerOneAddButton.setOnClickListener {
            openCalculatorNumberPad(view.context, "add", playerOneLifePoints)
        }

        playerOneSubtractButton.setOnClickListener {
            openCalculatorNumberPad(view.context, "subtract", playerOneLifePoints)
        }

        playerTwoAddButton.setOnClickListener {
            openCalculatorNumberPad(view.context, "add", playerTwoLifePoints)
        }

        playerTwoSubtractButton.setOnClickListener {
            openCalculatorNumberPad(view.context, "subtract", playerTwoLifePoints)
        }

        resetButton.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(view.context)
            builder.setTitle("Reset game")
            builder.setMessage("Are you sure you want to start a new game?")
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, _ ->
                    playerOneLifePointTotal = "8000"
                    playerTwoLifePointTotal = "8000"
                    playerOneLifePoints.text = playerOneLifePointTotal
                    playerTwoLifePoints.text = playerTwoLifePointTotal
                    dialog.dismiss()
                }.setNegativeButton("No") {dialog, _ ->
                    dialog.dismiss()
                }

            val alert = builder.create()
            alert.show()
        }
    }

    private fun openCalculatorNumberPad(
        context: Context,
        function: String,
        lifePoints: TextView
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.calculator_number_pad)
        dialog.show()

        var currentLifePoints: Int = Integer.valueOf(lifePoints.text.toString())

        val numberView: TextView = dialog.findViewById(R.id.number)

        val button0: Button = dialog.findViewById(R.id.button0)
        val button00: Button = dialog.findViewById(R.id.button00)
        val button000: Button = dialog.findViewById(R.id.button000)
        val button1: Button = dialog.findViewById(R.id.button1)
        val button2: Button = dialog.findViewById(R.id.button2)
        val button3: Button = dialog.findViewById(R.id.button3)
        val button4: Button = dialog.findViewById(R.id.button4)
        val button5: Button = dialog.findViewById(R.id.button5)
        val button6: Button = dialog.findViewById(R.id.button6)
        val button7: Button = dialog.findViewById(R.id.button7)
        val button8: Button = dialog.findViewById(R.id.button8)
        val button9: Button = dialog.findViewById(R.id.button9)

        val buttons: ArrayList<Button> = arrayListOf(
            button0, button00, button000, button1, button2, button3,
            button4, button5, button6, button7, button8, button9
        )

        addButtonListeners(buttons, numberView)

        val enterButton: Button = dialog.findViewById(R.id.enterButton)
        enterButton.setOnClickListener {

            var number = 0
            var color = ContextCompat.getColor(context, R.color.calculatorTextColor)

            if (numberView.text.isNotBlank()) {
                number = Integer.valueOf(numberView.text.toString())
            }

            if(number != 0){
                if (function == "add") {
                    currentLifePoints += number
                    if (currentLifePoints >= 999999) {
                        currentLifePoints = 999999
                    }
                    dialog.dismiss()
                    //Life Point Animation
                    lifePoints.setTextColor(ContextCompat.getColor(context, R.color.green))
                    lifePointAnimation(lifePoints, currentLifePoints - number, currentLifePoints, color)

                } else if (function == "subtract") {
                    currentLifePoints -= number
                    if (currentLifePoints <= 0) {
                        currentLifePoints = 0
                        color = ContextCompat.getColor(context, R.color.red)
                    }
                    dialog.dismiss()
                    //Life Point Animation
                    lifePoints.setTextColor(ContextCompat.getColor(context, R.color.red))
                    lifePointAnimation(lifePoints, currentLifePoints + number, currentLifePoints, color)
                }
            }
            if(lifePoints.id == R.id.playerOneLifePoints){
                playerOneLifePointTotal = currentLifePoints.toString()
            }else{
                playerTwoLifePointTotal = currentLifePoints.toString()
            }
        }

        val clearButton: Button = dialog.findViewById(R.id.clearButton)
        clearButton.setOnClickListener {
            numberView.text = ""
        }
    }

    private fun lifePointAnimation(textView: TextView, startValue: Int, endValue: Int, color: Int) {
        if(soundFX){
            sound = MediaPlayer.create(textView.context, R.raw.point_change_sound_fx)
            sound?.start()
        }
        val animator = ValueAnimator.ofInt(startValue, endValue)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            textView.text = (animation.animatedValue.toString())
        }
        animator.start()
        animator.doOnEnd {
            textView.setTextColor(color)
            if(sound!=null){
                sound?.stop()
                sound?.release()
            }
        }
    }

    private fun addButtonListeners(buttons: ArrayList<Button>, numberView: TextView) {
        for (button in buttons) {
            button.setOnClickListener {
                val buttonNumber = button.text
                //Limit calculator function to 6 digits
                if(numberView.length() + buttonNumber.length <= 6){
                    numberView.append(buttonNumber)
                }
            }
        }
    }

    companion object {
        var playerOneLifePointTotal: String = "8000"
        var playerTwoLifePointTotal: String = "8000"
    }
}