package com.pan.materialdrawer.model;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pan.materialdrawer.holder.BadgeStyle;
import com.pan.materialdrawer.holder.StringHolder;
import com.pan.materialdrawer.model.interfaces.ColorfulBadgeable;
import com.pan.materialdrawer.model.utils.ViewHolderFactory;

/**
 * Created by mikepenz on 03.02.15.
 */
public class PrimaryDrawerItem extends BasePrimaryDrawerItem<PrimaryDrawerItem> implements ColorfulBadgeable<PrimaryDrawerItem> {
    protected StringHolder mBadge;
    protected BadgeStyle mBadgeStyle = new BadgeStyle();

    @Override
    public PrimaryDrawerItem withBadge(StringHolder badge) {
        this.mBadge = badge;
        return this;
    }

    @Override
    public PrimaryDrawerItem withBadge(String badge) {
        this.mBadge = new StringHolder(badge);
        return this;
    }

    @Override
    public PrimaryDrawerItem withBadge(@StringRes int badgeRes) {
        this.mBadge = new StringHolder(badgeRes);
        return this;
    }

    @Override
    public PrimaryDrawerItem withBadgeStyle(BadgeStyle badgeStyle) {
        this.mBadgeStyle = badgeStyle;
        return this;
    }

    public StringHolder getBadge() {
        return mBadge;
    }

    public BadgeStyle getBadgeStyle() {
        return mBadgeStyle;
    }

    @Override
    public String getType() {
        return "PRIMARY_ITEM";
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return com.pan.materialdrawer.R.layout.material_drawer_item_primary;
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder) {
        Context ctx = holder.itemView.getContext();

        //get our viewHolder
        ViewHolder viewHolder = (ViewHolder) holder;

        //bind the basic view parts
        bindViewHelper((BaseViewHolder) holder);

        //set the text for the badge or hide
        boolean badgeVisible = StringHolder.applyToOrHide(mBadge, viewHolder.badge);
        //style the badge if it is visible
        if (badgeVisible) {
            mBadgeStyle.style(viewHolder.badge, getTextColorStateList(getColor(ctx), getSelectedTextColor(ctx)));
            viewHolder.badgeContainer.setVisibility(View.VISIBLE);
        } else {
            viewHolder.badgeContainer.setVisibility(View.GONE);
        }

        //define the typeface for our textViews
        if (getTypeface() != null) {
            viewHolder.badge.setTypeface(getTypeface());
        }

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
        private View badgeContainer;
        private TextView badge;

        public ViewHolder(View view) {
            super(view);
            this.badgeContainer = view.findViewById(com.pan.materialdrawer.R.id.material_drawer_badge_container);
            this.badge = (TextView) view.findViewById(com.pan.materialdrawer.R.id.material_drawer_badge);
        }
    }
}
