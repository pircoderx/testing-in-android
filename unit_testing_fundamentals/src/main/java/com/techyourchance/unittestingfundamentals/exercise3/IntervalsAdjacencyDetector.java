package com.techyourchance.unittestingfundamentals.exercise3;

import com.techyourchance.unittestingfundamentals.example3.Interval;

public class IntervalsAdjacencyDetector {

    /**
     * @return true if the intervals are adjacent, but don't overlap
     */
    public boolean isAdjacent(Interval A, Interval B) {
        return A.getEnd() == B.getStart() || A.getStart() == B.getEnd();
    }

    private boolean isSameIntervals(Interval A, Interval B) {
        return A.getStart() == B.getStart() && A.getEnd() == B.getEnd();
    }

}
