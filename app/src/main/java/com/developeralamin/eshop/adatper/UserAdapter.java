package com.developeralamin.eshop.adatper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developeralamin.eshop.R;
import com.developeralamin.eshop.model.UserModel;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> list;
    private Context context;

    public UserAdapter(List<UserModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_item_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {

        holder.t1.setText(list.get(position).getName());
        holder.t2.setText(list.get(position).getAddress());
        holder.t3.setText(list.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView t1, t2, t3;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.userName);
            t2 = itemView.findViewById(R.id.userAddress);
            t3 = itemView.findViewById(R.id.userEmail);

        }
    }
}
