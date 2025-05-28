package hung.deptrai.mycomic.core.presentation

interface BaseEvent {
    object Loading : BaseEvent
    object Success : BaseEvent
}