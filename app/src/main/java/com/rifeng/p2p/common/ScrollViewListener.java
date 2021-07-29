package com.rifeng.p2p.common;

import com.rifeng.p2p.uphidescrollview.ObservableScrollView;

public interface ScrollViewListener {

    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
}
