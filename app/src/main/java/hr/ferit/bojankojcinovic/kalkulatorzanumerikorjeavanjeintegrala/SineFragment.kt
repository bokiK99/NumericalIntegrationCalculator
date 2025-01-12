package hr.ferit.bojankojcinovic.kalkulatorzanumerikorjeavanjeintegrala

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin


class SineFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sine, container, false)
        val calculateButton = view.findViewById<Button>(R.id.CalculateButton1)
        val helpButton = view.findViewById<Button>(R.id.HelpButton1)
        val returnButton = view.findViewById<Button>(R.id.ReturnButton4)
        val graph = view.findViewById<GraphView>(R.id.graph1)


        helpButton.setOnClickListener {
            val fragment = SineHelpFragment()

            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, fragment)
            fragmentTransaction?.commit()
        }

        returnButton.setOnClickListener {
            val fragment = MainFragment()

            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, fragment)
            fragmentTransaction?.commit()
        }

        calculateButton.setOnClickListener {
            val a = view.findViewById<EditText>(R.id.editTextA1).text.toString().toInt()
            val b = view.findViewById<EditText>(R.id.editTextB1).text.toString().toInt()
            val c = view.findViewById<EditText>(R.id.editTextJ1).text.toString().toDouble()
            val d = view.findViewById<EditText>(R.id.editTextK1).text.toString().toDouble()
            val h = view.findViewById<EditText>(R.id.editTextH1).text.toString().toInt()

            graph.removeAllSeries()
            val series =
                LineGraphSeries<DataPoint>()
            val ahelp =
                a.toDouble()
            val bhelp = b.toDouble()
            var j = ahelp
            while (j - 1 <= bhelp + 1) {
                val y = c * sin(d * j)
                val datapoint = DataPoint(j, y)
                series.appendData(datapoint, true, 10000)
                j += 0.1
            }
            graph.viewport.setMinX(ahelp - 1)
            graph.viewport.setScalableY(true)
            graph.addSeries(series)


            if (h > (b - a)) {
                view.findViewById<TextView>(R.id.textViewSolutionTrap1).setText("")
                view.findViewById<TextView>(R.id.textViewSolutionSimpson1)
                    .setText("Pogreška! Nepravilan unos. Provjeri granice i korak integriranja!")
                view.findViewById<TextView>(R.id.textViewSolutionExact1).setText("")
            }
            else {
                var trap = 0.0
                trap += ((c * sin(a.toDouble() * d)) + (c * sin(b.toDouble() * d))) / 2
                for (i in a + h..b - h step h) {
                    trap += c * sin(i.toDouble() * d)
                }
                trap *= h
                view.findViewById<TextView>(R.id.textViewSolutionTrap1)
                    .setText("Rješenje preko trapezoidne formule: ${trap.toFloat()}")
                val series2 = LineGraphSeries<DataPoint>()
                var help = a
                val num2 = b - a + 1
                while (help <= b) {
                    val y = c * sin(d * help)
                    val datapoint = DataPoint(help.toDouble(), y)
                    series2.appendData(datapoint, true, num2)
                    help += h
                }
                series2.setColor(Color.RED)
                graph.addSeries(series2)
                if (((b - a) / h) % 2 == 0) {
                    var simpson = 0.0
                    simpson += c * sin(a.toDouble() * d) + c * sin(b.toDouble() * d)
                    for (i in a + h..b - h step 2 * h) {
                        simpson += 4 * (c * sin(i.toDouble() * d))
                    }
                    for (i in a + 2 * h..b - 2 * h step 2 * h) {
                        simpson += 2 * (c * sin(i.toDouble() * d))
                    }
                    simpson *= h.toDouble() / 3
                    view.findViewById<TextView>(R.id.textViewSolutionSimpson1)
                        .setText("Rješenje preko Simpsonove formule: ${simpson.toFloat()}")
                    val series3 = LineGraphSeries<DataPoint>()
                    var help2 = ahelp
                    while (help2.equals(bhelp) == false) {
                        val x1 = help2
                        val y1 = c * sin(d * help2)
                        val x2 = help2 + h
                        val y2 = c * sin(d * (help2 + h))
                        val x3 = help2 + 2 * h
                        val y3 = c * sin(d * (help2 + 2 * h))
                        val denominator = (x1 - x2) * (x1 - x3) * (x2 - x3)
                        val coeff1 =
                            (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denominator
                        val coeff2 =
                            (x3 * x3 * (y1 - y2) + x2 * x2 * (y3 - y1) + x1 * x1 * (y2 - y3)) / denominator
                        val coeff3 =
                            (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denominator

                        var k = help2
                        while (k <= help2 + 2 * h) {
                            val yhelp = coeff1 * (k.pow(2)) + (coeff2 * k) + coeff3
                            val datapoint = DataPoint(k, yhelp)
                            series3.appendData(datapoint, true, 10000)
                            k += 0.01
                        }
                        help2 += 2 * h
                    }
                    series3.setColor(Color.GREEN)
                    graph.addSeries(series3)

                } else {
                    view.findViewById<TextView>(R.id.textViewSolutionSimpson1)
                        .setText("Neparan broj podintervala.")
                }

                val newtonL = ((c * cos(a.toDouble() * d)) / d - (c * cos(b.toDouble() * d)) / d)
                view.findViewById<TextView>(R.id.textViewSolutionExact1)
                    .setText("Rješenje preko Newton-Leibnizove formule: ${newtonL.toFloat()}")

            }
        }

        return view
    }
}

