package com.pan.materialdrawer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.pan.materialdrawer.holder.ImageHolder;
import com.pan.materialdrawer.model.interfaces.IProfile;
import com.pan.materialdrawer.model.interfaces.Identifyable;
import com.pan.materialdrawer.util.IdDistributor;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mikepenz on 27.02.15.
 */
public class AccountHeader {
    protected final static double NAVIGATION_DRAWER_ACCOUNT_ASPECT_RATIO = 9d / 16d;

    protected static final String BUNDLE_SELECTION_HEADER = "bundle_selection_header";


    protected final AccountHeaderBuilder mAccountHeaderBuilder;

    protected AccountHeader(AccountHeaderBuilder accountHeaderBuilder) {
        this.mAccountHeaderBuilder = accountHeaderBuilder;
    }

    /**
     * Get the Root view for the Header
     *
     * @return
     */
    public View getView() {
        return mAccountHeaderBuilder.mAccountHeaderContainer;
    }

    /**
     * Set the drawer for the AccountHeader so we can use it for the select
     *
     * @param drawer
     */
    public void setDrawer(Drawer drawer) {
        mAccountHeaderBuilder.mDrawer = drawer;
    }

    /**
     * Returns the header background view so the dev can set everything on it
     *
     * @return
     */
    public ImageView getHeaderBackgroundView() {
        return mAccountHeaderBuilder.mAccountHeaderBackground;
    }

    /**
     * set the background for the header via the ImageHolder class
     *
     * @param imageHolder
     */
    public void setHeaderBackground(ImageHolder imageHolder) {
        ImageHolder.applyTo(imageHolder, mAccountHeaderBuilder.mAccountHeaderBackground);
    }

    /**
     * Set the background for the Header
     *
     * @param headerBackground
     */
    public void setBackground(Drawable headerBackground) {
        mAccountHeaderBuilder.mAccountHeaderBackground.setImageDrawable(headerBackground);
    }

    /**
     * Set the background for the Header as resource
     *
     * @param headerBackgroundRes
     */
    public void setBackgroundRes(@DrawableRes int headerBackgroundRes) {
        mAccountHeaderBuilder.mAccountHeaderBackground.setImageResource(headerBackgroundRes);
    }

    /**
     * Toggle the selection list (show or hide it)
     *
     * @param ctx
     */
    public void toggleSelectionList(Context ctx) {
        mAccountHeaderBuilder.toggleSelectionList(ctx);
    }

    /**
     * returns if the selection list is currently shown
     *
     * @return
     */
    public boolean isSelectionListShown() {
        return mAccountHeaderBuilder.mSelectionListShown;
    }


    /**
     * set this to false if you want to hide the first line of the selection box in the header (first line would be the name)
     *
     * @param selectionFirstLineShown
     */
    public void setSelectionFirstLineShown(boolean selectionFirstLineShown) {
        mAccountHeaderBuilder.mSelectionFirstLineShown = selectionFirstLineShown;
        mAccountHeaderBuilder.updateHeaderAndList();
    }

    /**
     * set this to false if you want to hide the second line of the selection box in the header (second line would be the e-mail)
     *
     * @param selectionSecondLineShown
     */
    public void setSelectionSecondLineShown(boolean selectionSecondLineShown) {
        mAccountHeaderBuilder.mSelectionSecondLineShown = selectionSecondLineShown;
        mAccountHeaderBuilder.updateHeaderAndList();
    }

    /**
     * set this to define the first line in the selection area if there is no profile
     * note this will block any values from profiles!
     *
     * @param selectionFirstLine
     */
    public void setSelectionFirstLine(String selectionFirstLine) {
        mAccountHeaderBuilder.mSelectionFirstLine = selectionFirstLine;
        mAccountHeaderBuilder.updateHeaderAndList();
    }

    /**
     * set this to define the second line in the selection area if there is no profile
     * note this will block any values from profiles!
     *
     * @param selectionSecondLine
     */
    public void setSelectionSecondLine(String selectionSecondLine) {
        mAccountHeaderBuilder.mSelectionSecondLine = selectionSecondLine;
        mAccountHeaderBuilder.updateHeaderAndList();
    }

    /**
     * returns the current list of profiles set for this header
     *
     * @return
     */
    public ArrayList<IProfile> getProfiles() {
        return mAccountHeaderBuilder.mProfiles;
    }

    /**
     * Set a new list of profiles for the header
     *
     * @param profiles
     */
    public void setProfiles(ArrayList<IProfile> profiles) {
        mAccountHeaderBuilder.mProfiles = IdDistributor.checkIds(profiles);
        mAccountHeaderBuilder.updateHeaderAndList();
    }

    /**
     * Selects the given profile and sets it to the new active profile
     *
     * @param profile
     */
    public void setActiveProfile(IProfile profile) {
        setActiveProfile(profile, false);
    }

    /**
     * Selects the given profile and sets it to the new active profile
     *
     * @param profile
     */
    public void setActiveProfile(IProfile profile, boolean fireOnProfileChanged) {
        final boolean isCurrentSelectedProfile = mAccountHeaderBuilder.switchProfiles(profile);
        //if the selectionList is shown we should also update the current selected profile in the list
        if (mAccountHeaderBuilder.mDrawer != null && isSelectionListShown()) {
            mAccountHeaderBuilder.mDrawer.setSelection(profile.getIdentifier(), false);
        }
        //fire the event if enabled and a listener is set
        if (fireOnProfileChanged && mAccountHeaderBuilder.mOnAccountHeaderListener != null) {
            mAccountHeaderBuilder.mOnAccountHeaderListener.onProfileChanged(null, profile, isCurrentSelectedProfile);
        }
    }

    /**
     * Selects a profile by its identifier
     *
     * @param identifier
     */
    public void setActiveProfile(int identifier) {
        setActiveProfile(identifier, false);
    }

    /**
     * Selects a profile by its identifier
     *
     * @param identifier
     */
    public void setActiveProfile(int identifier, boolean fireOnProfileChanged) {
        if (mAccountHeaderBuilder.mProfiles != null) {
            for (IProfile profile : mAccountHeaderBuilder.mProfiles) {
                if (profile instanceof Identifyable) {
                    if (profile.getIdentifier() == identifier) {
                        setActiveProfile(profile, fireOnProfileChanged);
                        return;
                    }
                }
            }
        }
    }

    /**
     * get the current active profile
     *
     * @return
     */
    public IProfile getActiveProfile() {
        return mAccountHeaderBuilder.mCurrentProfile;
    }


    /**
     * Helper method to update a profile using it's identifier
     *
     * @param newProfile
     */
    public void updateProfile(@NonNull IProfile newProfile) {
        updateProfileByIdentifier(newProfile);
    }

    /**
     * Helper method to update a profile using it's identifier
     *
     * @param newProfile
     */
    @Deprecated
    public void updateProfileByIdentifier(@NonNull IProfile newProfile) {
        if (mAccountHeaderBuilder.mProfiles != null && newProfile.getIdentifier() >= 0) {
            int found = -1;
            for (int i = 0; i < mAccountHeaderBuilder.mProfiles.size(); i++) {
                if (mAccountHeaderBuilder.mProfiles.get(i) instanceof Identifyable) {
                    if (mAccountHeaderBuilder.mProfiles.get(i).getIdentifier() == newProfile.getIdentifier()) {
                        found = i;
                        break;
                    }
                }
            }

            if (found > -1) {
                mAccountHeaderBuilder.mProfiles.set(found, newProfile);
                mAccountHeaderBuilder.updateHeaderAndList();
            }
        }
    }

    /**
     * Add new profiles to the existing list of profiles
     *
     * @param profiles
     */
    public void addProfiles(@NonNull IProfile... profiles) {
        if (mAccountHeaderBuilder.mProfiles == null) {
            mAccountHeaderBuilder.mProfiles = new ArrayList<>();
        }

        Collections.addAll(mAccountHeaderBuilder.mProfiles, IdDistributor.checkIds(profiles));

        mAccountHeaderBuilder.updateHeaderAndList();
    }

    /**
     * Add a new profile at a specific position to the list
     *
     * @param profile
     * @param position
     */
    public void addProfile(@NonNull IProfile profile, int position) {
        if (mAccountHeaderBuilder.mProfiles == null) {
            mAccountHeaderBuilder.mProfiles = new ArrayList<>();
        }
        mAccountHeaderBuilder.mProfiles.add(position, IdDistributor.checkId(profile));

        mAccountHeaderBuilder.updateHeaderAndList();
    }

    /**
     * remove a profile from the given position
     *
     * @param position
     */
    public void removeProfile(int position) {
        if (mAccountHeaderBuilder.mProfiles != null && mAccountHeaderBuilder.mProfiles.size() > position) {
            mAccountHeaderBuilder.mProfiles.remove(position);
        }

        mAccountHeaderBuilder.updateHeaderAndList();
    }

    /**
     * try to remove the given profile
     *
     * @param profile
     */
    public void removeProfile(@NonNull IProfile profile) {
        if (mAccountHeaderBuilder.mProfiles != null) {
            mAccountHeaderBuilder.mProfiles.remove(profile);
        }

        mAccountHeaderBuilder.updateHeaderAndList();
    }

    /**
     * Clear the header
     */
    public void clear() {
        mAccountHeaderBuilder.mProfiles = null;

        //calculate the profiles to set
        mAccountHeaderBuilder.calculateProfiles();

        //process and build the profiles
        mAccountHeaderBuilder.buildProfiles();
    }

    /**
     * add the values to the bundle for saveInstanceState
     *
     * @param savedInstanceState
     * @return
     */
    public Bundle saveInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putInt(BUNDLE_SELECTION_HEADER, mAccountHeaderBuilder.getCurrentSelection());
        }
        return savedInstanceState;
    }


    public interface OnAccountHeaderListener {
        /**
         * the event when the profile changes
         *
         * @param view
         * @param profile
         * @return if the event was consumed
         */
        public boolean onProfileChanged(View view, IProfile profile, boolean current);
    }

    public interface OnAccountHeaderSelectionViewClickListener {
        /**
         * the event when the user clicks the selection list under the profile icons
         *
         * @param view
         * @param profile
         * @return if the event was consumed
         */
        public boolean onClick(View view, IProfile profile);
    }
}
