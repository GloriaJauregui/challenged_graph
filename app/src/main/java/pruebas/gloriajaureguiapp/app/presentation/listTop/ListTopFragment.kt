package pruebas.gloriajaureguiapp.app.presentation.listTop

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import pruebas.gloriajaureguiapp.R
import pruebas.gloriajaureguiapp.app.di.ListModule
import pruebas.gloriajaureguiapp.app.utils.LoadingScreen
import pruebas.gloriajaureguiapp.databinding.FragmentListBinding
import pruebas.gloriajaureguiapp.mvi.Base
import pruebas.gloriajaureguiapp.app.presentation.listTop.ListModel.Event
import pruebas.gloriajaureguiapp.app.presentation.listTop.ListModel.SideEffect
import pruebas.gloriajaureguiapp.app.presentation.listTop.ListModel.State

class ListTopFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var elements: MutableList<ItemList> = mutableListOf()
    private val listAdapter = ListTopAdapter(elements)
    private var oldElements: MutableList<ItemList> = mutableListOf()

    companion object {
        private const val EMPTY_AMOUNT = 0
    }

    private val viewModel by viewModels<ListViewModel> {
        ListModule.providesListViewModelFactory(
            Bundle().apply {
                putParcelable(Base.SAVED_STATE, State())
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvList.layoutManager = LinearLayoutManager(context)
        binding.rvList.setHasFixedSize(true)
        binding.rvList.adapter = listAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewEffect.observe(
            viewLifecycleOwner, { takeActionOn(it) })
        viewModel.viewState.observe(viewLifecycleOwner, { render(it) })
        viewModel.onEvent(Event.OnGetList)
    }

    private fun takeActionOn(effect: SideEffect) {
        when (effect) {
            is SideEffect.ShowList -> {
                effect.let { post ->
                    oldElements.addAll(post.result)
                    setList(post.result)
                }
            }
            is SideEffect.ErrorShowList -> {
                oldElements.clear()
                setList(oldElements)
                val snack = Snackbar.make(binding.root,getString(R.string.lbl_graph_error),
                    Snackbar.LENGTH_LONG)
                snack.show()
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

    /**
     * Actualiza los elementos en la lista.
     * @param post[MutableList<ItemList>] Contenido de la lista.
     */
    private fun setList(post: MutableList<ItemList>) {
        elements.clear()
        if (post.size == EMPTY_AMOUNT) {
            setPlaceholder()
        } else {
            clearErrorContent()
        }
        elements.addAll(post)
        listAdapter.notifyDataSetChanged()
        counterUpdate()
    }

    /**
     * Oculta el layout de lista vacía.
     */
    private fun clearErrorContent() {
        binding.vPlaceholderGeneric.root.isGone
    }

    /**
     * Crea placeholder en caso de que no existan elementos en la lista.
     */
    private fun setPlaceholder() {
        binding.vPlaceholderGeneric.root.isVisible
        binding.vPlaceholderGeneric.root.isClickable = true
        binding.vPlaceholderGeneric.root.isFocusable = true
    }

    private fun counterUpdate() {
        val timer = object: CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                viewModel.onEvent(Event.OnGetList)
            }
        }
        timer.start()
    }
}