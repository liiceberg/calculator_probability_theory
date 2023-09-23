package ru.kpfu.itis.gimaletdinova.alculatorprobabilitytheory

import android.os.Bundle
import android.text.method.DigitsKeyListener
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gimaletdinova.alculatorprobabilitytheory.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private var binding: FragmentCalculatorBinding? = null
    private var n = 0
    private var k = 0
    private var m = 0
    private var r = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalculatorBinding.bind(view)

        binding?.run {
            val idButton = arguments?.getInt(BUTTON_ARG)

            selectView(idButton)

            applyBtn.setOnClickListener {
                val value = when (idButton) {
                    R.id.placements_with_rep_btn -> {
                        if (getN() > 0 && getK() > 0) {
                            calculatePlacementsWithRep(n, k)
                        } else {
                            null
                        }
                    }

                    R.id.placements_no_rep_btn -> {
                        if (getN() > 0 && getK() > 0) {
                            calculatePlacementsNoRep(n, k)
                        } else {
                            null
                        }
                    }

                    R.id.permutations_with_rep_btn -> {
                        if (!valueNEt.text.isNullOrEmpty()) {
                            calculatePermutationsWithRep(
                                valueNEt.text.toString().split(" ").map(String::toInt)
                            )
                        } else {
                            null
                        }

                    }

                    R.id.permutations_no_rep_btn -> {
                        if (getN() > 0) {
                            calculatePermutationsNoRep(n)
                        } else {
                            null
                        }
                    }

                    R.id.combinations_with_rep_btn -> {
                        if (getN() > 0 && getK() > 0) {
                            calculateCombinationsWithRep(n, k)
                        } else {
                            null
                        }
                    }

                    R.id.placements_no_rep_btn -> {
                        if (getN() > 0 && getK() > 0) {
                            calculateCombinationsNoRep(n, k)
                        } else {
                            null
                        }
                    }

                    R.id.task1_btn -> {
                        if (getN() > 0 && getK() > 0 && getM() > 0) {
                            calculateProbability1(k, n, m)
                        } else {
                            null
                        }
                    }

                    R.id.task2_btn -> {
                        if (getN() > 0 && getM() > 0 && getR() > 0) {
                            calculateProbability2(k, n, m, r)
                        } else {
                            null
                        }
                    }

                    else -> null
                }
                if (value != null) {
                    var text = getString(R.string.answer) + "\n"
                    if (value is Double) {
                        text += String.format("%.3f", value)
                    } else {
                        text += value
                    }
                    answerTv.text = text
                } else {
                    answerTv.text = getString(R.string.error_text)
                }


            }
        }
    }

    private fun selectView(id: Int?) {
        binding?.run {
            when (id) {
                R.id.permutations_with_rep_btn -> {
                    valueKEt.visibility = View.GONE
                    valueNEt.hint = getString(R.string.perm_with_rep)
                    valueNEt.keyListener = DigitsKeyListener.getInstance("1234567890 ")
                }

                R.id.permutations_no_rep_btn -> {
                    valueKEt.visibility = View.GONE

                }

                R.id.task1_btn -> {
                    valueMEt.visibility = View.VISIBLE

                }

                R.id.task2_btn -> {
                    valueMEt.visibility = View.VISIBLE
                    valueREt.visibility = View.VISIBLE

                }
                else -> {}
            }
        }
    }

    private fun calculatePlacementsWithRep(n: Int, k: Int): Long {
        var answer: Long = 1
        for (i in 1..k) {
            answer *= n
        }
        return answer
    }

    private fun calculatePermutationsNoRep(n: Int): Long {
        return factorial(n)
    }

    private fun calculatePermutationsWithRep(list: List<Int>): Double {
        var answer = factorial(list.sum()) * 1.0
        for (elem in list) {
            answer /= factorial(elem)
        }
        return answer
    }

    private fun calculatePlacementsNoRep(n: Int, k: Int): Long {
        var answer: Long = 1
        for (i in n - k + 1..n) {
            answer *= i
        }
        return answer
    }

    private fun calculateCombinationsWithRep(n: Int, k: Int): Double {
        return calculateCombinationsNoRep(n + k - 1, k)
    }

    private fun calculateCombinationsNoRep(n: Int, k: Int): Double {
        return (calculatePlacementsNoRep(n, k) * 1.0) / (factorial(k) * 1.0)
    }

    private fun calculateProbability1(n: Int, k: Int, m: Int): Double {
        return calculateCombinationsNoRep(m, k) / calculateCombinationsNoRep(n, k)
    }

    private fun calculateProbability2(n: Int, k: Int, m: Int, r: Int): Double {
        return calculateCombinationsNoRep(m, r) *
                calculateCombinationsNoRep(n - m, k - r) / calculateCombinationsNoRep(n, k)
    }

    private fun factorial(n: Int): Long {
        var answer: Long = 1
        for (i in 1..n) {
            answer *= i
        }
        return answer
    }

    private fun getN(): Int {
        binding?.run {
            if (!valueNEt.text.isNullOrEmpty()) {
                n = valueNEt.text.toString().toInt()
                return n
            }
        }
        return 0
    }

    private fun getK(): Int {
        binding?.run {
            if (!valueKEt.text.isNullOrEmpty()) {
                k = valueNEt.text.toString().toInt()
                return k
            }
        }
        return 0
    }

    private fun getM(): Int {
        binding?.run {
            if (!valueMEt.text.isNullOrEmpty()) {
                m = valueNEt.text.toString().toInt()
                return m
            }
        }
        return 0
    }

    private fun getR(): Int {
        binding?.run {
            if (!valueREt.text.isNullOrEmpty()) {
                r = valueNEt.text.toString().toInt()
                return r
            }
        }
        return 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val BUTTON_ARG = "BUTTON"
        fun createBundle(btnName: Int): Bundle {
            return bundleOf(BUTTON_ARG to btnName)
        }

    }
}