package com.hello.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class MusicTable {

    public static final String AUTHORITY = "com.hello.annan.music.provider";
    public static final String TABLE_NAME = "musics";

    // This class cannot be instantiated
    private MusicTable() {
    }

    public static class Columns implements BaseColumns {


        /**
         * The scheme part for this provider's URI
         */
        private static final String SCHEME = "content://";

        /**
         * Path parts for the URIs
         */

        //musics must be same as tableName
        private static final String PATH_MUSICS = "/musics";

        /**
         * Path part for the ID URI
         */
        private static final String PATH_MUSIC_ID = "/musics/";

        /**
         * 0-relative position of a ID segment in the path part of a ID URI
         */
        public static final int _ID_PATH_POSITION = 1;

        /**
         * 0-relative position of a note ID segment in the path part of a note
         * ID URI
         */
        public static final int NOTE_ID_PATH_POSITION = 1;

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_MUSICS);

        /**
         * The content URI base for a single music. Callers must append a
         * numeric id to this Uri to retrieve a music path
         */
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_MUSIC_ID);

        /**
         * The content URI match pattern for a single , specified by its ID. Use
         * this to match incoming URIs or to construct an Intent.
         */
        public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY + PATH_MUSIC_ID + "/#");

        /*
         * MIME type definitions
         */

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of
         * markers.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.yealink.markder";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single .
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.yealink.markder";

        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = _ID + " ASC";

        /**
         * Media file path
         */
        public static final String PATH = "path";

        /**
         * Time internal is the time from beginning to the time to insert next
         * time.
         */
        public static final String DURATION = "time";
    }
}
