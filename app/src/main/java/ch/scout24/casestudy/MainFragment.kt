package ch.scout24.casestudy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.scout24.casestudy.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment), OnRepoClicked {

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

        recyclerListViewAdapter = RecyclerListViewAdapter(this)

        viewBinding.recyclerView.apply {
            adapter = recyclerListViewAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            itemAnimator?.removeDuration = 50
            setHasFixedSize(true)
        }

        // Observe the repositories
        viewModel.repoList.observe(viewLifecycleOwner, { repos ->
            repos?.apply {
                recyclerListViewAdapter.setListOfRepos(repos)
            }
        })

        // Observe the network state
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

    override fun onRepoClicked(r: RepoDataModelItem) {
        val builder = AlertDialog.Builder(requireContext())

        val items = arrayOf(
            "Title: " + r.name,
            "Language: " + r.language,
            "Number of Watchers: " + r.watchers,
            "Description: " + r.description,
            "Login Name: " + r.owner.login,
            "Repository Update Date: " + r.updated_at
        )

        with(builder)
        {
            setTitle("Repository Details")
            setItems(items) { dialog, which ->
            }

            show()
        }

    }

}