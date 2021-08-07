package com.jh.roachecklist.utils

import androidx.lifecycle.MutableLiveData

class ListLiveData<T> : MutableLiveData<ArrayList<T>>() {
    init {
        value = ArrayList()
    }

    fun size(): Int {
        val items: ArrayList<T>? = value
        return items!!.size
    }

    fun get( position: Int ): T {
        val items: ArrayList<T>? = value
        return items!![position]
    }

    fun indexOf( item: T ): Int {
        val items: ArrayList<T>? = value
        return items!!.indexOf( item )
    }

    fun add(item: T) {
        val items: ArrayList<T>? = value
        items!!.add(item)
        value = items
    }

    fun addAll(list: List<T>) {
        val items: ArrayList<T>? = value
        items!!.addAll(list)
        value = items
    }

    fun addAll(index: Int, list: List<T>) {
        val items: ArrayList<T>? = value
        items!!.addAll(index, list)
        value = items
    }

    fun clear(notify: Boolean) {
        val items: ArrayList<T>? = value
        items!!.clear()
        if (notify) {
            value = items
        }
    }

    fun remove(item: T) {
        val items: ArrayList<T>? = value
        items!!.remove(item)
        value = items
    }

    fun removeAt(position: Int): T {
        val items: ArrayList<T>? = value
        val removedItem = items!!.removeAt( position )
        value = items
        return removedItem
    }

    fun notifyChange() {
        val items: ArrayList<T>? = value
        value = items
    }
}