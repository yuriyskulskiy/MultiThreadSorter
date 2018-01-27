package com.example.yuriy.multithredsorter;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.yuriy.multithredsorter.model.Mechanizm;
import com.example.yuriy.multithredsorter.model.TypesConstants;
import com.example.yuriy.multithredsorter.service.manager.AdvancedTimeLinkedList;




public class DataRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private AdvancedTimeLinkedList<Mechanizm> mDataset;



    public DataRecyclerViewAdapter(AdvancedTimeLinkedList<Mechanizm> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        if (viewType == TYPE_HEADER) {
            View headerView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(headerView);
        }
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mechanizm_single_item, parent, false);

            ItemViewHolder vh = new ItemViewHolder(v);

            return vh;
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    public void setTypeLogo(ImageView typeLogo) {
        int imageResourceId;
        String dataType = mDataset.get(0).getMechanizmType();
        switch (dataType) {
            case TypesConstants.CAR:
                imageResourceId = R.drawable.layer_list_car;
                break;
            case TypesConstants.PLANE:
                imageResourceId = R.drawable.layer_list_plane;
                break;
            case TypesConstants.SHIP:
                imageResourceId = R.drawable.layer_list_ship;
                break;
            default:
                imageResourceId = R.drawable.ic_emty_circular;
                break;
        }
        typeLogo.setImageResource(imageResourceId);
    }


    public void updateData(AdvancedTimeLinkedList<Mechanizm> dataset) {
//        mDataset.clear();  dont clear cause this is ref to tmp global multi data to reuse
        mDataset = (dataset);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerholder = (HeaderViewHolder) holder;

            headerholder.mSortTime.setText(String.valueOf(mDataset.getSortingDeltaTime()));
            headerholder.mMechanizmTypeTextView.setText(mDataset.get(0).getMechanizmType());
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            String nameToBind = mDataset.get(position - 1).getName();
            itemHolder.mNameTextView.setText(nameToBind);
            itemHolder.mPositionBeforSorting.setText("position: " + mDataset.get(position - 1).getId());
            setTypeLogo(itemHolder.mMechanizmPicture);
        }


    }

    @Override
    public int getItemCount() {
        return mDataset.size() + 1;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView;
        public ImageView mMechanizmPicture;
        public TextView mPositionBeforSorting;

        public ItemViewHolder(View rootView) {
            super(rootView);
            mMechanizmPicture = rootView.findViewById(R.id.iv_mehanizm_type);
            mNameTextView = rootView.findViewById(R.id.tv_mechanizm_name);
            mPositionBeforSorting = rootView.findViewById(R.id.tv_previus_position);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView mMechanizmTypeTextView;
        TextView mSortTime;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mMechanizmTypeTextView = itemView.findViewById(R.id.tv_mech_type);
            mSortTime = itemView.findViewById(R.id.tv_delta_time_value);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
