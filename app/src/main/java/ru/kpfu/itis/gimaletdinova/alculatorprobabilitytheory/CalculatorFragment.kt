package ru.kpfu.itis.gimaletdinova.alculatorprobabilitytheory

import android.os.Bundle
import android.text.method.DigitsKeyListener
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gimaletdinova.alculatorprobabilitytheory.databinding.FragmentCalculatorBinding
import java.math.BigInteger
import kotlin.math.sign

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private var binding: FragmentCalculatorBinding? = null
    private var n = BigInteger.ZERO
    private var k = BigInteger.ZERO
    private var m = BigInteger.ZERO
    private var r = BigInteger.ZERO

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalculatorBinding.bind(view)

        binding?.run {
            val idButton = arguments?.getInt(BUTTON_ARG)

            selectView(idButton)
            selectImage(idButton)

            applyBtn.setOnClickListener {
                val value = when (idButton) {
                    R.id.placements_with_rep_btn -> {
                        if (getN() > BigInteger.ZERO && getK() > BigInteger.ZERO && k <= n) {
                            calculatePlacementsWithRep(n, k)
                        } else 0
                    }

                    R.id.placements_no_rep_btn -> {
                        if (getN() > BigInteger.ZERO && getK() > BigInteger.ZERO && k <= n) {
                            calculatePlacementsNoRep(n, k)
                        } else 0
                    }

                    R.id.permutations_with_rep_btn -> {
                        if (!valueNEt.text.isNullOrEmpty()) {
                            calculatePermutationsWithRep(
                                valueNEt.text.toString().split(" ").map(String::toBigInteger)
                            )
                        } else 0

                    }

                    R.id.permutations_no_rep_btn -> {
                        if (getN() > BigInteger.ZERO) {
                            calculatePermutationsNoRep(n)
                        } else 0
                    }

                    R.id.combinations_with_rep_btn -> {
                        if (getN() > BigInteger.ZERO &&
                            getK() > BigInteger.ZERO && k <= n) {
                            calculateCombinationsWithRep(n, k)
                        } else 0
                    }

                    R.id.combinations_no_rep_btn -> {
                        if (getN() > BigInteger.ZERO &&
                            getK() > BigInteger.ZERO && k <= n) {
                            calculateCombinationsNoRep(n, k)
                        } else 0

                    }

                    R.id.task1_btn -> {
                        if (getN() > BigInteger.ZERO &&
                            getK() > BigInteger.ZERO &&
                            getM() > BigInteger.ZERO &&
                            k < m && m <= n) {
                            calculateProbability1(n, k, m)
                        } else 0
                    }

                    R.id.task2_btn -> {
                        if (getN() > BigInteger.ZERO &&
                            getK() > BigInteger.ZERO &&
                            getM() > BigInteger.ZERO &&
                            getR() > BigInteger.ZERO &&
                            k < m && m <= n && r <= k) {
                            calculateProbability2(n, k, m, r)
                        } else 0
                    }
                    else -> 0
                }
                if (value is BigInteger || value is Double && sign(value) > 0) {
                    var text = getString(R.string.answer) + "\n"
                    if (value is Double) {
                        text += String.format("%.10f", value)
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

    private fun selectImage(id: Int?) {
        binding?.run {
            val img = when (id) {
                R.id.placements_no_rep_btn -> R.drawable.pl_no_r
                R.id.placements_with_rep_btn -> R.drawable.pl_w_r
                R.id.permutations_with_rep_btn -> R.drawable.perm_w_r
                R.id.permutations_no_rep_btn -> R.drawable.perm_no_r
                R.id.combinations_no_rep_btn -> R.drawable.com_no_r
                R.id.combinations_with_rep_btn -> R.drawable.comb_w_r
                R.id.task1_btn -> R.drawable.task_1
                R.id.task2_btn -> R.drawable.task_2
                else -> 0
            }

            formulaIv.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    img,
                    null
                )
            )
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

    private fun calculatePlacementsWithRep(n: BigInteger, k: BigInteger): BigInteger {
        var answer: BigInteger = BigInteger.ONE
        var i = BigInteger.ONE
        while (i <= k) {
            answer *= n
            i += BigInteger.ONE
        }
        return answer
    }

    private fun calculatePermutationsNoRep(n: BigInteger): BigInteger {
        return factorial(n)
    }

    private fun calculatePermutationsWithRep(list: List<BigInteger>): BigInteger {
        var n = BigInteger.ZERO
        list.forEach { number -> n += number }
        var answer = factorial(n)
        for (elem in list) {
            answer /= factorial(elem)
        }
        return answer
    }

    private fun calculatePlacementsNoRep(n: BigInteger, k: BigInteger): BigInteger {
        var answer: BigInteger = BigInteger.ONE

        var i = n - k + BigInteger.ONE    
        while(i <= n) {
            answer *= i
            i += BigInteger.ONE
        }
        return answer
    }

    private fun calculateCombinationsWithRep(n: BigInteger, k: BigInteger): BigInteger {
        return calculateCombinationsNoRep(n + k - BigInteger.ONE, k)
    }

    private fun calculateCombinationsNoRep(n: BigInteger, k: BigInteger): BigInteger {
        return calculatePlacementsNoRep(n, k) / factorial(k)
    }

    private fun calculateProbability1(n: BigInteger, k: BigInteger, m: BigInteger): Double {
        return calculateCombinationsNoRep(m, k).toDouble() /
                calculateCombinationsNoRep(n, k).toDouble()
    }

    private fun calculateProbability2(n: BigInteger, k: BigInteger, m: BigInteger, r: BigInteger): Double {
        return calculateCombinationsNoRep(m, r).toDouble() *
                calculateCombinationsNoRep(n - m, k - r).toDouble() /
                calculateCombinationsNoRep(n, k).toDouble()
    }

    private fun factorial(n: BigInteger): BigInteger {
        var answer: BigInteger = BigInteger.ONE
        var i = BigInteger.ONE
        while (i <= n) {
            answer *= i
            i += BigInteger.ONE
        }
        return answer
    }

    private fun getN(): BigInteger {
        binding?.run {
            if (!valueNEt.text.isNullOrEmpty()) {
                n = valueNEt.text.toString().toBigInteger()
                return n
            }
        }
        return BigInteger.ZERO
    }

    private fun getK(): BigInteger {
        binding?.run {
            if (!valueKEt.text.isNullOrEmpty()) {
                k = valueKEt.text.toString().toBigInteger()
                return k
            }
        }
        return BigInteger.ZERO
    }

    private fun getM(): BigInteger {
        binding?.run {
            if (!valueMEt.text.isNullOrEmpty()) {
                m = valueMEt.text.toString().toBigInteger()
                return m
            }
        }
        return BigInteger.ZERO
    }

    private fun getR(): BigInteger {
        binding?.run {
            if (!valueREt.text.isNullOrEmpty()) {
                r = valueREt.text.toString().toBigInteger()
                return r
            }
        }
        return BigInteger.ZERO
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