package ch.scout24.casestudy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.scout24.casestudy.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var viewBinding: MainFragmentBinding

    private lateinit var recyclerListViewAdapter: RecyclerListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = MainFragmentBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recyclerListViewAdapter = RecyclerListViewAdapter()

        viewBinding.recyclerView.apply {
            adapter = recyclerListViewAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            itemAnimator?.removeDuration = 50
            setHasFixedSize(true)
        }

        // Observing the repos for changes
        viewModel.repoList.observe(viewLifecycleOwner, { repos ->
            repos?.apply {
                recyclerListViewAdapter.setListOfRepos(repos)
            }
        })

        // Beobachten ob ein Netzwerkproblem besteht
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner, { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })

        viewModel.isNetworkErrorShown.observe(
            viewLifecycleOwner,
            { isNetworkErrorShown ->
                if (!isNetworkErrorShown) {
                    viewBinding.recyclerView.visibility = View.VISIBLE
                }
            })

        viewBinding.swipeRefresh.setOnRefreshListener {
            toggleRefreshingAnimation(true)
            viewModel.getRepositoryListFromApi()
            toggleRefreshingAnimation(false)
        }
    }

    fun toggleRefreshingAnimation(isRefreshing: Boolean) {
        viewBinding.swipeRefresh.isRefreshing = isRefreshing
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(context, "Wir haben grade ein Netzwerkfehler...", Toast.LENGTH_LONG)
                .show()

            viewBinding.recyclerView.visibility = View.GONE
            viewModel.onNetworkErrorShown()
        }
    }

}