package com.pan.materialdrawer.model;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.pan.materialdrawer.Drawer;
import com.pan.materialdrawer.interfaces.OnCheckedChangeListener;
import com.pan.materialdrawer.model.interfaces.IDrawerItem;
import com.pan.materialdrawer.model.utils.ViewHolderFactory;

/**
 * Created by mikepenz on 03.02.15.
 */
public class ToggleDrawerItem extends BasePrimaryDrawerItem<ToggleDrawerItem> {
    private boolean toggleEnabled = true;

    private boolean checked = false;
    private OnCheckedChangeListener onCheckedChangeListener = null;

    public ToggleDrawerItem withChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public ToggleDrawerItem withToggleEnabled(boolean toggleEnabled) {
        this.toggleEnabled = toggleEnabled;
        return this;
    }

    public ToggleDrawerItem withOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
        return this;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isToggleEnabled() {
        return toggleEnabled;
    }

    public void setToggleEnabled(boolean toggleEnabled) {
        this.toggleEnabled = toggleEnabled;
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public String getType() {
        return "TOGGLE_ITEM";
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return com.pan.materialdrawer.R.layout.material_drawer_item_toggle;
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder) {
        //get our viewHolder
        final ViewHolder viewHolder = (ViewHolder) holder;

        //bind the basic view parts
        bindViewHelper((BaseViewHolder) holder);

        //handle the toggle
        viewHolder.toggle.setOnCheckedChangeListener(null);
        viewHolder.toggle.setChecked(checked);
        viewHolder.toggle.setOnCheckedChangeListener(checkedChangeListener);
        viewHolder.toggle.setEnabled(toggleEnabled);

        //add a onDrawerItemClickListener here to be able to check / uncheck if the drawerItem can't be selected
        withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (!isSelectable()) {
                    checked = !checked;
                    viewHolder.toggle.setChecked(checked);
                }

                return false;
            }
        });

        //call the onPostBindView method to trigger post bind view actions (like the listener to modify the item if required)
        onPostBindView(this, holder.itemView);
    }

    @Override
    public ViewHolderFactory getFactory() {
        return new ItemFactory();
    }

    public static class ItemFactory implements ViewHolderFactory<ViewHolder> {
        public ViewHolder factory(View v) {
            return new ViewHolder(v);
        }
    }

    private static class ViewHolder extends BaseViewHolder {
        private ToggleButton toggle;

        private ViewHolder(View view) {
            super(view);
            this.toggle = (ToggleButton) view.findViewById(com.pan.materialdrawer.R.id.material_drawer_toggle);
        }
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isEnabled()) {
                checked = isChecked;
                if (getOnCheckedChangeListener() != null) {
                    getOnCheckedChangeListener().onCheckedChanged(ToggleDrawerItem.this, buttonView, isChecked);
                }
            } else {
                buttonView.setOnCheckedChangeListener(null);
                buttonView.setChecked(!isChecked);
                buttonView.setOnCheckedChangeListener(checkedChangeListener);
            }
        }
    };
}
