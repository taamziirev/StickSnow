package com.project.sticksnow.presentation.bottomnavigation

import androidx.fragment.app.Fragment
import com.project.sticksnow.R
import com.project.sticksnow.presentation.category.RecyclerCategoriesSelected
import com.project.sticksnow.presentation.event.EventFragment
import com.project.sticksnow.presentation.map.MapsFragment


enum class BottomNavigationPosition(val position: Int, val id: Int) {
    CATEGORIES(0, R.id.Categories),
    MAP(1, R.id.Map),
    EVENTS(2, R.id.AllEventsAndFind);
}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when (id) {
    BottomNavigationPosition.CATEGORIES.id -> BottomNavigationPosition.CATEGORIES
    BottomNavigationPosition.MAP.id -> BottomNavigationPosition.MAP
    BottomNavigationPosition.EVENTS.id -> BottomNavigationPosition.EVENTS
    else -> BottomNavigationPosition.CATEGORIES
}

fun BottomNavigationPosition.createFragment(): Fragment = when (this) {
    BottomNavigationPosition.CATEGORIES -> RecyclerCategoriesSelected.newInstance()
    BottomNavigationPosition.MAP -> MapsFragment.newInstance()
    BottomNavigationPosition.EVENTS -> EventFragment.newInstance()
}

fun BottomNavigationPosition.getTag(): String = when (this) {
    BottomNavigationPosition.CATEGORIES -> RecyclerCategoriesSelected.TAG
    BottomNavigationPosition.MAP -> MapsFragment.TAG
    BottomNavigationPosition.EVENTS -> EventFragment.TAG
}

