package hr.ferit.bojankojcinovic.kalkulatorzanumerikorjeavanjeintegrala

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val choiceButton1 = view.findViewById<Button>(R.id.ChoiceButton1)
        val choiceButton2 = view.findViewById<Button>(R.id.ChoiceButton2)
        val choiceButton3 = view.findViewById<Button>(R.id.ChoiceButton3)

        choiceButton1.setOnClickListener {
            val fragment = SineFragment()

            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, fragment)
            fragmentTransaction?.commit()
        }

        choiceButton2.setOnClickListener {
            val fragment = CosineFragment()

            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, fragment)
            fragmentTransaction?.commit()
        }

        choiceButton3.setOnClickListener {
            val fragment = PolyFragment()

            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, fragment)
            fragmentTransaction?.commit()
        }


        return view
    }


}