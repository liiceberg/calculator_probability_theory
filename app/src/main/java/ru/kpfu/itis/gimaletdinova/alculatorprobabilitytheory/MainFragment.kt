package ru.kpfu.itis.gimaletdinova.alculatorprobabilitytheory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.kpfu.itis.gimaletdinova.alculatorprobabilitytheory.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        activateButtons()

    }

    private fun activateButtons() {
        binding?.run {
            listOf(
                placementsNoRepBtn, placementsWithRepBtn,
                permutationsNoRepBtn, permutationsWithRepBtn,
                combinationsWithRepBtn, combinationsNoRepBtn,
                task1Btn, task2Btn
            ).forEach { btn ->
                btn.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_mainFragment_to_calculatorFragment,
                        CalculatorFragment.createBundle(btn.id)
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}