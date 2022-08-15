package hs.project.secondweek.Adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.RecyclerView

class MusicSwipeCallback(private val recyclerViewAdapter: MusicListAdapter) : ItemTouchHelper.Callback() {
    private var currentPosition: Int? = null    // 현재 선택된 recycler view의 position
    private var previousPosition: Int? = null   // 이전에 선택했던 recycler view의 position
    private var currentDx = 0f                  // 현재 x 값
    private var clamp = 0f                      // 고정시킬 크기

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        // 드래그 방향 : 위, 아래 인식
        // 스와이프 방향 : 왼쪽, 오른쪽 인식
        // 설정 안 하고 싶으면 0
        return makeMovementFlags(UP or DOWN, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // 리사이클러뷰에서 현재 선택된 데이터와 드래그한 위치에 있는 데이터를 교환
        val fromPos: Int = viewHolder.adapterPosition
        val toPos: Int = target.adapterPosition
        recyclerViewAdapter.swapData(fromPos, toPos)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("Not yet implemented")
    }
}