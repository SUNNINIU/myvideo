package com.pan.materialdrawer.model;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.pan.materialdrawer.Drawer;
import com.pan.materialdrawer.interfaces.OnCheckedChangeListener;
import com.pan.materialdrawer.model.interfaces.IDrawerItem;
import com.pan.materialdrawer.model.utils.ViewHolderFactory;

/**
 * Created by mikepenz on 03.02.15.
 */
public class SwitchDrawerItem extends BasePrimaryDrawerItem<SwitchDrawerItem> {

    private boolean switchEnabled = true;

    private boolean checked = false;
    private OnCheckedChangeListener onCheckedChangeListener = null;

    public SwitchDrawerItem withChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public SwitchDrawerItem withSwitchEnabled(boolean switchEnabled) {
        this.switchEnabled = switchEnabled;
        return this;
    }

    public SwitchDrawerItem withOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
        return this;
    }

    public SwitchDrawerItem withCheckable(boolean checkable) {
        return withSelectable(checkable);
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isSwitchEnabled() {
        return switchEnabled;
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    @Override
    public String getType() {
        return "SWITCH_ITEM";
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return com.pan.materialdrawer.R.layout.material_drawer_item_switch;
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder) {
        //get our viewHolder
        final ViewHolder viewHolder = (ViewHolder) holder;

        //bind the basic view parts
        bindViewHelper((BaseViewHolder) holder);

        //handle the switch
        viewHolder.switchView.setOnCheckedChangeListener(null);
        viewHolder.switchView.setChecked(checked);
        viewHolder.switchView.setOnCheckedChangeListener(checkedChangeListener);
        viewHolder.switchView.setEnabled(switchEnabled);

        //add a onDrawerItemClickListener here to be able to check / uncheck if the drawerItem can't be selected
        withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (!isSelectable()) {
                    checked = !checked;
                    viewHolder.switchView.setChecked(checked);
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
        private SwitchCompat switchView;

        private ViewHolder(View view) {
            super(view);
            this.switchView = (SwitchCompat) view.findViewById(com.pan.materialdrawer.R.id.material_drawer_switch);
        }
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isEnabled()) {
                checked = isChecked;
                if (getOnCheckedChangeListener() != null) {
                    getOnCheckedChangeListener().onCheckedChanged(SwitchDrawerItem.this, buttonView, isChecked);
                }
            } else {
                buttonView.setOnCheckedChangeListener(null);
                buttonView.setChecked(!isChecked);
                buttonView.setOnCheckedChangeListener(checkedChangeListener);
            }
        }
    };
}
