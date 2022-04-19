package pruebas.gloriajaureguiapp.app.presentation.graph

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pruebas.gloriajaureguiapp.app.di.ListModule
import pruebas.gloriajaureguiapp.databinding.FragmentGraphBinding
import pruebas.gloriajaureguiapp.mvi.Base
import pruebas.gloriajaureguiapp.app.presentation.graph.GraphModel.Event
import pruebas.gloriajaureguiapp.app.presentation.graph.GraphModel.SideEffect
import pruebas.gloriajaureguiapp.app.presentation.graph.GraphModel.State
import pruebas.gloriajaureguiapp.app.utils.LoadingScreen
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.DataPoint

import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.LegendRenderer

import com.jjoe64.graphview.series.BarGraphSeries
import pruebas.gloriajaureguiapp.apiservice.entities.GraphResultset
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null
    private val binding get() = _binding!!

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

    }

    private fun takeActionOn(effect: SideEffect) {
        when (effect) {
            is SideEffect.ShowData -> {
                graph(effect.result)
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

    fun graph(data : ArrayList<GraphResultset>) {
        val graph = binding.graph
        var dataContent : ArrayList<DataPoint> = arrayListOf()
        for(i in data){
            dataContent.add(DataPoint(strToDate(i.date), i.price))
        }
        val series = LineGraphSeries(
            dataContent.toTypedArray()
        )
        series.isDrawBackground = true
        series.setAnimated(true)

        // set manual X bounds
        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.setMinY(39100.58)
        graph.viewport.setMaxY(39285.85)
        graph.viewport.isScrollable = false

        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMinX(1.0)
        graph.viewport.setMaxX(1.1)

        // enable scaling
        graph.viewport.isScalable = true
        graph.viewport.setScalableY(true)
        graph.addSeries(series)
        graph.gridLabelRenderer.horizontalAxisTitle = null
        graph.legendRenderer.isVisible = true
        graph.legendRenderer.align = LegendRenderer.LegendAlign.TOP
    }

    /**
     * Convierte un string en formato UTC en date
     */
    fun strToDate(date: String?): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        return formatter.parse(date)
    }


    fun prueba() {}

}