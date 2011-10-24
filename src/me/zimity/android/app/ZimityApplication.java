package me.zimity.android.app;

import java.util.List;

import roboguice.application.RoboApplication;

import com.google.inject.Module;


public class ZimityApplication extends RoboApplication {
	
	protected void addApplicationModules(List<Module> modules) {
		modules.add(new ZimityModule());
	}

}
