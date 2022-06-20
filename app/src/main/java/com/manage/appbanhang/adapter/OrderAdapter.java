package com.manage.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.manage.appbanhang.Interface.ItemClickListener;
import com.manage.appbanhang.model.EventBus.OrderEvent;
import com.manage.appbanhang.model.Order;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHoldel> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHoldel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_order, parent, false);
        return new MyViewHoldel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoldel holder, int position) {
        Order order = orderList.get(position);
        holder.txtdonhang_view_order.setText("Đơn hàng: " + order.getId());
        holder.trangthai.setText(trangThaiDon(order.getTrangthai()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rcvdetailview_order.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(order.getItem().size());
        // adapter chitiet
        DetailOrderAdapter detailOrderAdapter = new DetailOrderAdapter(context, order.getItem());
        holder.rcvdetailview_order.setLayoutManager(layoutManager);
        holder.rcvdetailview_order.setAdapter(detailOrderAdapter);
        holder.rcvdetailview_order.setRecycledViewPool(viewPool);

        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (isLongClick){
                    EventBus.getDefault().postSticky(new OrderEvent(order));
                }
            }
        });

    }

    private String trangThaiDon(int status){
        String result = "";
        switch (status){
            case 0:
                result = "Đơn hàng đang được xử lý";
                break;
            case 1:
                result = "Đơn hàng đã được chấp nhận";
                break;
            case 2:
                result = "Đang giao hàng";
                break;
            case 3:
                result = "Giao thành công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHoldel extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtdonhang_view_order, trangthai;
        RecyclerView rcvdetailview_order;
        ItemClickListener listener;


        public MyViewHoldel(@NonNull View itemView) {
            super(itemView);
            txtdonhang_view_order = itemView.findViewById(R.id.iddonhang_vieworder);
            trangthai = itemView.findViewById(R.id.tinhtrang);
            rcvdetailview_order = itemView.findViewById(R.id.recycleview_detail_view_order);

            itemView.setOnLongClickListener(this);
        }

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onClick(view, getAdapterPosition(), true);
            return false;
        }
    }
}
