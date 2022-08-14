package hs.project.secondweek.Service

import hs.project.secondweek.mediaPlayer
import hs.project.secondweek.selectedMusic

class ServiceData {
    companion object {
        fun playSong(songIndex: Int = 0) {
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(selectedMusic!!.path)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        }
    }
}