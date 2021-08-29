package com.example.testingkotlin.adapters

interface OnMovieListener {
    fun onMovieClick(position: Int)
    fun onCategory(category: String)
}