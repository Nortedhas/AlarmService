package com.ageone.alarm.External.RxBus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

// Use object so we have a singleton instance
object RxBus {

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}

class RxEvent {
    data class EventAddMeditation(val meditationName: String)
    data class EventChangeDuration(val duration: Long)
    data class EventChangeCurrentTime(val currentTime: Long)
    class EventMediaPlayerEnd()
}