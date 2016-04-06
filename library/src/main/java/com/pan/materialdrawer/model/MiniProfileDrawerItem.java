package com.pan.materialdrawer.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.iconics.typeface.IIcon;
import com.pan.materialdrawer.holder.ImageHolder;
import com.pan.materialdrawer.holder.StringHolder;
import com.pan.materialdrawer.model.interfaces.IProfile;
import com.pan.materialdrawer.model.utils.ViewHolderFactory;

/**
 * Created by mikepenz on 03.02.15.
 */
public class MiniProfileDrawerItem extends AbstractDrawerItem<MiniProfileDrawerItem> implements IProfile<MiniProfileDrawerItem> {
    protected ImageHolder icon;

    public MiniProfileDrawerItem() {
        withSelectable(false);
    }

    public MiniProfileDrawerItem(ProfileDrawerItem profile) {
        this.icon = profile.icon;
        this.mEnabled = profile.mEnabled;
        withSelectable(false);
    }

    @Override
    public MiniProfileDrawerItem withName(String name) {
        return null;
    }

    @Override
    public StringHolder getName() {
        return null;
    }

    @Override
    public MiniProfileDrawerItem withEmail(String email) {
        return null;
    }

    @Override
    public StringHolder getEmail() {
        return null;
    }

    @Override
    public MiniProfileDrawerItem withIcon(Drawable icon) {
        this.icon = new ImageHolder(icon);
        return this;
    }

    @Override
    public MiniProfileDrawerItem withIcon(@DrawableRes int iconRes) {
        this.icon = new ImageHolder(iconRes);
        return this;
    }

    @Override
    public MiniProfileDrawerItem withIcon(Bitmap iconBitmap) {
        this.icon = new ImageHolder(iconBitmap);
        return this;
    }

    @Override
    public MiniProfileDrawerItem withIcon(String url) {
        this.icon = new ImageHolder(url);
        return this;
    }

    @Override
    public MiniProfileDrawerItem withIcon(Uri uri) {
        this.icon = new ImageHolder(uri);
        return this;
    }

    @Override
    public MiniProfileDrawerItem withIcon(IIcon icon) {
        this.icon = new ImageHolder(icon);
        return this;
    }

    @Override
    public ImageHolder getIcon() {
        return icon;
    }

    @Override
    public String getType() {
        return "MINI_PROFILE_ITEM";
    }

    @Override
    @LayoutRes
    public int getLayoutRes() {
        return com.pan.materialdrawer.R.layout.material_drawer_item_mini_profile;
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder) {
        //get our viewHolder
        ViewHolder viewHolder = (ViewHolder) holder;

        //set the identifier from the drawerItem here. It can be used to run tests
        viewHolder.itemView.setId(getIdentifier());

        //set the icon
        ImageHolder.applyToOrSetInvisible(getIcon(), viewHolder.icon);

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
        private ImageView icon;

        public ViewHolder(View view) {
            super(view);

            this.icon = (ImageView) view.findViewById(com.pan.materialdrawer.R.id.material_drawer_icon);
        }
    }
}
