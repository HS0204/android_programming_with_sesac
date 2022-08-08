package hs.project.secondweek.Service

import hs.project.secondweek.Adapter.mediaPlayer
import hs.project.secondweek.Adapter.songe

class ServiceData {
    companion object {
        fun playSong(songIndex: Int = 0) {
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(songe!!.path)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        }
    }
}