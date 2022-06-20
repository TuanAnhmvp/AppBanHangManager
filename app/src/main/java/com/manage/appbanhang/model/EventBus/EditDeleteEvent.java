package com.manage.appbanhang.model.EventBus;

import com.manage.appbanhang.model.SanPhamMoi;

public class EditDeleteEvent {
    SanPhamMoi sanPhamMoi;

    public EditDeleteEvent(SanPhamMoi sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }

    public SanPhamMoi getSanPhamMoi() {
        return sanPhamMoi;
    }

    public void setSanPhamMoi(SanPhamMoi sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }
}
