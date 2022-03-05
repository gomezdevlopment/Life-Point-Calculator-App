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
import org.w3c.dom.Text
import java.lang.StringBuilder

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
            openCalculatorNumberPad(view.context, "playerOne", "add", playerOneLifePoints)
        }

        playerOneSubtractButton.setOnClickListener {
            openCalculatorNumberPad(view.context, "playerOne", "subtract", playerOneLifePoints)
        }

        playerTwoAddButton.setOnClickListener {
            openCalculatorNumberPad(view.context, "playerTwo", "add", playerTwoLifePoints)
        }

        playerTwoSubtractButton.setOnClickListener {
            openCalculatorNumberPad(view.context, "playerTwo", "subtract", playerTwoLifePoints)
        }
    }

    private fun openCalculatorNumberPad(context: Context, player: String, function: String, lifePoints: TextView) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.calculator_number_pad)
        dialog.show()

        var currentLifePoints: Int = Integer.valueOf(lifePoints.text.toString())

        val number: TextView = dialog.findViewById(R.id.number)

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

        addButtonListeners(buttons, number)

        val enterButton: Button = dialog.findViewById(R.id.enterButton)
        enterButton.setOnClickListener {
            if(number.text.isNotBlank()){
                if(function == "add"){
                    currentLifePoints += Integer.valueOf(number.text.toString())
                }else if(function == "subtract"){
                    currentLifePoints -= Integer.valueOf(number.text.toString())
                    if(currentLifePoints < 0){
                        currentLifePoints = 0
                    }
                }
                dialog.dismiss()
                lifePoints.text = currentLifePoints.toString()
            }else{
                dialog.dismiss()
            }
        }

        val clearButton: Button = dialog.findViewById(R.id.clearButton)
        clearButton.setOnClickListener {
            number.text = ""
        }
    }

    private fun addButtonListeners(buttons: ArrayList<Button>, numberView: TextView) {
        for(button in buttons){
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