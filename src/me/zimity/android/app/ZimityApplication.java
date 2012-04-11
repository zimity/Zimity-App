package me.zimity.android.app;

import java.util.List;

import com.google.inject.Module;

import roboguice.inject.RoboApplicationProvider;

public class ZimityApplication extends RoboApplicationProvider {
	
    protected void addApplicationModules(List<Module> modules) {
        modules.add(new ZimityModule());
    } 

}
