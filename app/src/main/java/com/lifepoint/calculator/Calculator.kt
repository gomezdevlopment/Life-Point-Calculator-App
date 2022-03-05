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
import org.w3c.dom.Text
import java.lang.StringBuilder
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import androidx.core.animation.doOnEnd


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [Calculator.newInstance] factory method to
 * create an instance of this fragment.
 */
class Calculator : Fragment() {
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
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playerOneLifePoints: TextView = view.findViewById(R.id.playerOneLifePoints)
        val playerTwoLifePoints: TextView = view.findViewById(R.id.playerTwoLifePoints)
        val playerOneAddButton: Button = view.findViewById(R.id.playerOneAdditionButton)
        val playerOneSubtractButton: Button = view.findViewById(R.id.playerOneSubtractButton)
        val playerTwoAddButton: Button = view.findViewById(R.id.playerTwoAdditionButton)
        val playerTwoSubtractButton: Button = view.findViewById(R.id.playerTwoSubtractButton)

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
            val color = ContextCompat.getColor(context, R.color.calculatorTextColor)

            if (numberView.text.isNotBlank()) {
                number = Integer.valueOf(numberView.text.toString())
            }

            if (function == "add") {
                currentLifePoints += number
                dialog.dismiss()
                //Life Point Animation
                lifePoints.setTextColor(ContextCompat.getColor(context, R.color.green))
                lifePointAnimation(lifePoints, currentLifePoints - number, currentLifePoints, color)

            } else if (function == "subtract") {
                currentLifePoints -= number
                if (currentLifePoints < 0) {
                    currentLifePoints = 0
                }
                dialog.dismiss()
                //Life Point Animation
                lifePoints.setTextColor(ContextCompat.getColor(context, R.color.red))
                lifePointAnimation(lifePoints, currentLifePoints + number, currentLifePoints, color)
            }
        }

        val clearButton: Button = dialog.findViewById(R.id.clearButton)
        clearButton.setOnClickListener {
            numberView.text = ""
        }
    }

    private fun lifePointAnimation(textView: TextView, startValue: Int, endValue: Int, color: Int) {
        val animator = ValueAnimator.ofInt(startValue, endValue)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            textView.text = (animation.animatedValue.toString())
        }
        animator.start()
        animator.doOnEnd {
            textView.setTextColor(color)
        }
    }

    private fun addButtonListeners(buttons: ArrayList<Button>, numberView: TextView) {
        for (button in buttons) {
            button.setOnClickListener {
                val buttonNumber = button.text
                numberView.append(buttonNumber)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment calculator.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Calculator().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}