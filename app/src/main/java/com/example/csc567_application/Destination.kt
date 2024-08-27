package com.example.csc567_application

sealed class Destination (val route:String, val icon:Int, val title:String) {
    object Playing : Destination(
        route = "playing", icon = R.drawable.play,
        title = "Playing"
    )

    object Songs : Destination(
        route = "songs", icon = R.drawable.music,
        title = "Songs"
    )

    object Charts : Destination(route = "charts", icon = R.drawable.chart, title = "Charts")

    object Settings : Destination(
        route = "settings", icon = R.drawable.settings_cog,
        title = "Settings"
    )

    companion object {
        val toList = listOf(Playing, Songs, Charts, Settings)
    }
}