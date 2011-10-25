package me.zimity.android.util;

public final class Common {
    
    // Should these strings be moved to the Strings.xml file?
    // But then again, these strings should stay the same regardless of the client language
    
    public static final boolean PRODUCTION = false;
    public static final String CLIENT_ID = "AND_1.0";
    
    public final static int ANALYTICS_DISPATCH_INTERVAL = 20;
    
    public enum LogLevel {
    	DISABLED, INFO, ERROR, DEBUG
    }
    
    public final static LogLevel LOG_LEVEL = LogLevel.DISABLED;
    
    public enum ImprintType {
    	NOTE, PHOTO, AUDIO, VIDEO, REMINDER, EVENT, DETAIL, BOOKMARK
    }
    
    public enum SharingScope {
    	PRIVATE, FRIENDS, GLOBAL
    }
    
    public final static String IMPRINT_TYPE = "imp_type";
    public final static String IMPRINT_NOTE = "note";
    public final static String IMPRINT_SLUG = "slug";
    public final static String IMPRINT_USERID = "user_id";
    public final static String IMPRINT_CLIENT = "client";
    public final static String IMPRINT_LATITUDE = "latitude";
    public final static String IMPRINT_LONGITUDE = "longitude";
    public final static String IMPRINT_ALTITUDE = "altitude";
    public final static String IMPRINT_BEARING = "bearing";
    public final static String IMPRINT_SPEED = "speed";
    public final static String IMPRINT_SHARING = "sharing";
    public final static String IMPRINT_ACCURACY = "accuracy";
    public final static String IMPRINT_SYNCD = "syncd";
    public final static String IMPRINT_DELETED = "deleted";
    public final static String IMPRINT_CREATED = "created";
    public final static String IMPRINT_MODIFIED = "modified";
    public final static String IMPRINT_EXTRA = "extra"; // Contains extra imprint data (ie. picture/audio/video)
    
    public final static String FILE_BASE_DIRECTORY = "/zimity/";
    
    private Common() { } // Prevents instantiation
}