package hung.deptrai.mycomic.feature.explore_manga.presentation

sealed class HomePageAction {
    data class PullToRefresh(val isRefresh: Boolean): HomePageAction()
}