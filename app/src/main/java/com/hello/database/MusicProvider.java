package com.hello.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class MusicProvider extends ContentProvider {

    private static final String TAG = MusicProvider.class.getSimpleName();
    // The incoming URI matches the Musics URI pattern
    private static final int MUSICS = 1;

    // The incoming URI matches the Music ID URI pattern
    private static final int MUSIC_ID = 2;

    // Handle to a new DatabaseHelper.
    private DatabaseHelper mOpenHelper;

    /**
     * A projection map used to select columns from the database
     */
    private static HashMap<String, String> sNotesProjectionMap;

    /**
     * A UriMatcher instance
     */
    private static final UriMatcher sUriMatcher;

    /**
     * A block that instantiates and sets static objects
     */
    static {

        /*
         * Creates and initializes the URI matcher
         */
        // Create a new instance
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Add a pattern that routes URIs terminated with "notes" to a NOTES
        // operation
        sUriMatcher.addURI(MusicTable.AUTHORITY, MusicTable.TABLE_NAME, MUSICS);

        // Add a pattern that routes URIs terminated with "notes" plus an
        // integer
        // to a note ID operation
        sUriMatcher.addURI(MusicTable.AUTHORITY, MusicTable.TABLE_NAME + "/#", MUSIC_ID);
        /*
         * Creates and initializes a projection map that returns all columns
         */

        // Creates a new projection map instance. The map returns a column name
        // given a string. The two are usually equal.
        sNotesProjectionMap = new HashMap<String, String>();

        // Maps the string "_ID" to the column name "_ID"
        sNotesProjectionMap.put(MusicTable.Columns._ID, MusicTable.Columns._ID);

        // Maps "path" to "path"
        sNotesProjectionMap.put(MusicTable.Columns.PATH, MusicTable.Columns.PATH);
        // Maps "duration" to "duration"
        sNotesProjectionMap.put(MusicTable.Columns.DURATION, MusicTable.Columns.DURATION);
    }

    @Override
    public boolean onCreate() {
        // Creates a new helper object. Note that the database itself isn't
        // opened until
        // something tries to access it, and it's only created if it doesn't
        // already exist.
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        /**
         * Chooses the MIME type based on the incoming URI pattern
         */
        switch (sUriMatcher.match(uri)) {

        // If the pattern is for notes or live folders, returns the general
        // content type.
        case MUSICS:
            return MusicTable.Columns.CONTENT_TYPE;

        // If the pattern is for note IDs, returns the note ID content type.
        case MUSIC_ID:
            return MusicTable.Columns.CONTENT_ITEM_TYPE;

        // If the URI pattern doesn't match any permitted patterns, throws
        // an exception.
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Validates the incoming URI. Only the full provider URI is allowed for
        // inserts.
        if (sUriMatcher.match(uri) != MUSICS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If the values map doesn't contain a title, sets the value to the
        // default title.
        if (values.containsKey(MusicTable.Columns.PATH) == false) {
            throw new IllegalArgumentException("column com.hello.media path should not be null!  " + uri);
        }

        // Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        // Performs the insert and returns the ID of the new note.
        long rowId = db.insert(MusicTable.TABLE_NAME, // The table to
                                                              // insert
                                                              // into.
                null, // A hack, SQLite sets this column value to null
                      // if values is empty.
                values // A map of column names, and the values to insert
                       // into the columns.
        );

        // If the insert succeeded, the row ID exists.
        if (rowId > 0) {
            // Creates a URI with the note ID pattern and the new row ID
            // appended to it.
            Uri noteUri = ContentUris.withAppendedId(MusicTable.Columns.CONTENT_ID_URI_BASE, rowId);

            // Notifies observers registered against this provider that the data
            // changed.
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        // If the insert didn't succeed, then the rowID is <= 0. Throws an
        // exception.
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String finalWhere;

        int count;

        // Does the delete based on the incoming URI pattern.
        switch (sUriMatcher.match(uri)) {

        // If the incoming pattern matches the general pattern for notes, does a
        // delete
        // based on the incoming "where" columns and arguments.
        case MUSICS:
            count = db.delete(MusicTable.TABLE_NAME, // The database
                                                             // table
                    // name
                    selection, // The incoming where clause column names
                    selectionArgs // The incoming where clause values
            );
            break;

        // If the incoming URI matches a single note ID, does the delete based
        // on the
        // incoming data, but modifies the where clause to restrict it to the
        // particular note ID.
        case MUSIC_ID:
            /*
             * Starts a final WHERE clause by restricting it to the desired note
             * ID.
             */
            finalWhere = MusicTable.Columns._ID + // The ID column name
                    " = " + // test for equality
                    uri.getPathSegments(). // the incoming note ID
                            get(MusicTable.Columns.NOTE_ID_PATH_POSITION);

            // If there were additional selection criteria, append them to the
            // final
            // WHERE clause
            if (selection != null) {
                finalWhere = finalWhere + " AND " + selection;
            }

            // Performs the delete.
            count = db.delete(MusicTable.TABLE_NAME, // The database
                                                             // table
                    // name.
                    finalWhere, // The final WHERE clause
                    selectionArgs // The incoming where clause values.
            );
            break;

        // If the incoming pattern is invalid, throws an exception.
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        /*
         * Gets a handle to the content resolver object for the current context,
         * and notifies it that the incoming URI changed. The object passes this
         * along to the resolver framework, and observers that have registered
         * themselves for the provider are notified.
         */
        getContext().getContentResolver().notifyChange(uri, null);

        // Returns the number of rows deleted.
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Constructs a new query builder and sets its table name
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(MusicTable.TABLE_NAME);

        /**
         * Choose the projection and adjust the "where" clause based on URI
         * pattern-matching.
         */
        switch (sUriMatcher.match(uri)) {
        // If the incoming URI is for notes, chooses the Notes projection
        case MUSICS:
            qb.setProjectionMap(sNotesProjectionMap);
            break;

        /*
         * If the incoming URI is for a single note identified by its ID,
         * chooses the note ID projection, and appends "_ID = <noteID>" to the
         * where clause, so that it selects that single note
         */
        case MUSIC_ID:
            qb.setProjectionMap(sNotesProjectionMap);
            qb.appendWhere(MusicTable.Columns._ID + // the name of the ID column
                    "=" +
                    // the position of the note ID itself in the incoming URI
                    uri.getPathSegments().get(MusicTable.Columns.NOTE_ID_PATH_POSITION));
            break;

        default:
            // If the URI doesn't match any of the known patterns, throw an
            // exception.
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        String orderBy;
        // If no sort order is specified, uses the default
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = MusicTable.Columns.DEFAULT_SORT_ORDER;
        } else {
            // otherwise, uses the incoming sort order
            orderBy = sortOrder;
        }

        // Opens the database object in "read" mode, since no writes need to be
        // done.
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        /*
         * Performs the query. If no problems occur trying to read the database,
         * then a Cursor object is returned; otherwise, the cursor variable
         * contains null. If no records were selected, then the Cursor object is
         * empty, and Cursor.getCount() returns 0.
         */
        Cursor c = qb.query(db, // The database to query
                projection, // The columns to return from the query
                selection, // The columns for the where clause
                selectionArgs, // The values for the where clause
                null, // don't group the rows
                null, // don't filter by row groups
                orderBy // The sort order
        );

        // Tells the Cursor what URI to watch, so it knows when its source data
        // changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }
}
