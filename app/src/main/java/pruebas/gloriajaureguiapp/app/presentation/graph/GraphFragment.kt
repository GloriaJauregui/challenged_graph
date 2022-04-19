package pruebas.gloriajaureguiapp.app.presentation.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import pruebas.gloriajaureguiapp.app.di.ListModule
import pruebas.gloriajaureguiapp.databinding.FragmentGraphBinding
import pruebas.gloriajaureguiapp.mvi.Base
import pruebas.gloriajaureguiapp.app.presentation.graph.GraphModel.Event
import pruebas.gloriajaureguiapp.app.presentation.graph.GraphModel.SideEffect
import pruebas.gloriajaureguiapp.app.presentation.graph.GraphModel.State
import pruebas.gloriajaureguiapp.app.utils.LoadingScreen
import pruebas.gloriajaureguiapp.apiservice.entities.GraphResultset
import kotlin.collections.ArrayList

class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!

    private var scoreList = ArrayList<GraphResultset>()

    private val viewModel by viewModels<GraphViewModel> {
        ListModule.providesGraphDataViewModelFactory(
            Bundle().apply {
                putParcelable(Base.SAVED_STATE, State())
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewEffect.observe(
            viewLifecycleOwner, { takeActionOn(it) })
        viewModel.viewState.observe(viewLifecycleOwner, { render(it) })
        viewModel.onEvent(Event.OnGetData)
        initLineChart()
    }

    private fun takeActionOn(effect: SideEffect) {
        when (effect) {
            is SideEffect.ShowData -> {
                setDataToLineChart(effect.result)
            }
            is SideEffect.ErrorShowData -> {
                var a = "1"
            }
        }
    }

    private fun render(state: State) {
        if (state.isLoading) {
            LoadingScreen.displayLoadingWithText(context, false)
        } else {
            LoadingScreen.hideLoading()
        }
    }


    private fun initLineChart() {
        binding.lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = binding.lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        binding.lineChart.axisRight.isEnabled = false
        binding.lineChart.legend.isEnabled = false
        binding.lineChart.description.isEnabled = false
        binding.lineChart.animateX(1000, Easing.EaseInSine)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }


    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < scoreList.size) {
                ""
            } else {
                ""
            }
        }
    }

    private fun setDataToLineChart(data : ArrayList<GraphResultset>) {
        val entries: ArrayList<Entry> = ArrayList()
        scoreList = data
        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(Entry(i.toFloat(), score.price.toFloat()))
        }
        val lineDataSet = LineDataSet(entries, "Hola")
        val data = LineData(lineDataSet)
        binding.lineChart.data = data
        binding.lineChart.invalidate()
    }

}

