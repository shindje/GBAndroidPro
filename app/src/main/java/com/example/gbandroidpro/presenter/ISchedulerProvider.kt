package com.example.gbandroidpro.presenter

import io.reactivex.Scheduler

//For the sake of testing
interface ISchedulerProvider {

    fun ui(): Scheduler

    fun io(): Scheduler
}