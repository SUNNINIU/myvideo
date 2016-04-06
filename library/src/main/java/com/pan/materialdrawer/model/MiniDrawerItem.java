package com.pan.materialdrawer.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pan.materialdrawer.holder.BadgeStyle;
import com.pan.materialdrawer.holder.ImageHolder;
import com.pan.materialdrawer.holder.StringHolder;
import com.pan.materialdrawer.model.utils.ViewHolderFactory;
import com.pan.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;

/**
 * Created by mikepenz on 03.02.15.
 */
public class MiniDrawerItem extends BaseDrawerItem<MiniDrawerItem> {
    private StringHolder mBadge;
    private BadgeStyle mBadgeStyle = new BadgeStyle();

    private boolean mEnableSelectedBackground = false;

    public MiniDrawerItem() {

    }

    public MiniDrawerItem(PrimaryDrawerItem primaryDrawerItem) {
        this.mIdentifier = primaryDrawerItem.mIdentifier;
        this.mTag = primaryDrawerItem.mTag;

        this.mBadge = primaryDrawerItem.mBadge;
        this.mBadgeStyle = primaryDrawerItem.mBadgeStyle;

        this.mEnabled = primaryDrawerItem.mEnabled;
        this.mSelectable = primaryDrawerItem.mSelectable;
        this.mSelected = primaryDrawerItem.mSelected;

        this.icon = primaryDrawerItem.icon;
        this.selectedIcon = primaryDrawerItem.selectedIcon;

        this.iconTinted = primaryDrawerItem.iconTinted;
        this.selectedColor = primaryDrawerItem.selectedColor;

        this.iconColor = primaryDrawerItem.iconColor;
        this.selectedIconColor = primaryDrawerItem.selectedIconColor;
        this.disabledIconColor = primaryDrawerItem.disabledIconColor;
    }

    public MiniDrawerItem(SecondaryDrawerItem secondaryDrawerItem) {
        this.mIdentifier = secondaryDrawerItem.mIdentifier;
        this.mTag = secondaryDrawerItem.mTag;

        this.mBadge = secondaryDrawerItem.mBadge;
        this.mBadgeStyle = secondaryDrawerItem.mBadgeStyle;

        this.mEnabled = secondaryDrawerItem.mEnabled;
        this.mSelectable = secondaryDrawerItem.mSelectable;
        this.mSelected = secondaryDrawerItem.mSelected;

        this.icon = secondaryDrawerItem.icon;
        this.selectedIcon = secondaryDrawerItem.selectedIcon;

        this.iconTinted = secondaryDrawerItem.iconTinted;
        this.selectedColor = secondaryDrawerItem.selectedColor;

        this.iconColor = secondaryDrawerItem.iconColor;
        this.selectedIconColor = secondaryDrawerItem.selectedIconColor;
        this.disabledIconColor = secondaryDrawerItem.disabledIconColor;
    }

    public MiniDrawerItem withEnableSelectedBackground(boolean enableSelectedBackground) {
        this.mEnableSelectedBackground = enableSelectedBackground;
        return this;
    }

    @Override
    public String getType() {
        return "MINI_ITEM";
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return com.pan.materialdrawer.R.layout.material_drawer_item_mini;
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder) {
        Context ctx = holder.itemView.getContext();

        //get our viewHolder
        ViewHolder viewHolder = (ViewHolder) holder;

        //set the identifier from the drawerItem here. It can be used to run tests
        viewHolder.itemView.setId(getIdentifier());

        //set the item selected if it is
        viewHolder.itemView.setSelected(isSelected());

        //
        viewHolder.itemView.setTag(this);

        //get the correct color for the icon
        int iconColor = getIconColor(ctx);
        int selectedIconColor = getSelectedIconColor(ctx);

        if (mEnableSelectedBackground) {
            //get the correct color for the background
            int selectedColor = getSelectedColor(ctx);
            //set the background for the item
            UIUtils.setBackground(viewHolder.view, DrawerUIUtils.getSelectableBackground(ctx, selectedColor));
        }

        //set the text for the badge or hide
        boolean badgeVisible = StringHolder.applyToOrHide(mBadge, viewHolder.badge);
        //style the badge if it is visible
        if (badgeVisible) {
            mBadgeStyle.style(viewHolder.badge);
        }

        //get the drawables for our icon and set it
        Drawable icon = ImageHolder.decideIcon(getIcon(), ctx, iconColor, isIconTinted(), 1);
        Drawable selectedIcon = ImageHolder.decideIcon(getSelectedIcon(), ctx, selectedIconColor, isIconTinted(), 1);
        ImageHolder.applyMultiIconTo(icon, iconColor, selectedIcon, selectedIconColor, isIconTinted(), viewHolder.icon);

        //for android API 17 --> Padding not applied via xml
        int verticalPadding = ctx.getResources().getDimensionPixelSize(com.pan.materialdrawer.R.dimen.material_drawer_padding);
        int topBottomPadding = ctx.getResources().getDimensionPixelSize(com.pan.materialdrawer.R.dimen.material_mini_drawer_item_padding);
        viewHolder.itemView.setPadding(verticalPadding, topBottomPadding, verticalPadding, topBottomPadding);

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

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView icon;
        private TextView badge;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
            this.icon = (ImageView) view.findViewById(com.pan.materialdrawer.R.id.material_drawer_icon);
            this.badge = (TextView) view.findViewById(com.pan.materialdrawer.R.id.material_drawer_badge);
        }
    }
}
