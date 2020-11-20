package ch.scout24.casestudy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository : SafeApiRequest() {

    var repoList: List<RepoDataModelItem> = listOf()

    suspend fun getRepoListFromApi() {
        withContext(Dispatchers.IO) {
            val repoListFromApi: List<RepoDataModelItem> = apiRequest { RestApi().getRepos() }

            repoList = repoListFromApi
        }
    }

}