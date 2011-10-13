package me.zimity.android.app;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

/*
 * Helper class to draw overlay images on MapViews
 */
public class MapViewOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

    public MapViewOverlay(Drawable defaultMarker) {
        super(boundCenterBottom(defaultMarker));
    }

    public void addOverlay(OverlayItem overlay) {
        mOverlays.add(overlay);
        populate();
    }
    
    public void clearOverlays() {
        mOverlays = new ArrayList<OverlayItem>();
    }

    @Override
    protected OverlayItem createItem(int i) {
        return mOverlays.get(i);
    }

    @Override
    public int size() {
        return mOverlays.size();
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        super.draw(canvas, mapView, false);
    }
}
