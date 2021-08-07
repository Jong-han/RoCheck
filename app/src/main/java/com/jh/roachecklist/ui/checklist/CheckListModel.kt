package com.jh.roachecklist.ui.checklist

data class CheckListModel( val work: String, val minLevel: Int?, val maxLevel: Int?, val gold: Int?, val state: Int, val count: Int )