package com.example.gbandroidpro

import com.example.gbandroidpro.view.AppState
import io.reactivex.Observable

// Ещё выше стоит интерактор. Здесь уже чистая бизнес-логика
interface Interactor<T> {
    // Use Сase: получение данных для вывода на экран
    // Используем RxJava
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}
// Репозиторий представляет собой слой получения и хранения данных, которые он
// передаёт интерактору
interface Repository<T> {

    fun getData(word: String): Observable<T>
}
// Источник данных для репозитория (Интернет, БД и т. п.)
interface DataSource<T> {

    fun getData(word: String): Observable<T>
}