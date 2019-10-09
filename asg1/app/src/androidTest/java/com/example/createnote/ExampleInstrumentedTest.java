package com.example.createnote;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.createnote.model.Category;
import com.example.createnote.model.Note;
import com.example.createnote.model.NoteDatabaseHandler;
import com.example.createnote.sqlite.DatabaseException;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private Context appContext;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.createnote", appContext.getPackageName());
    }

    @Test
    public void testDatabase() throws DatabaseException {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        NoteDatabaseHandler dbh = new NoteDatabaseHandler(appContext);
        long id = dbh.getNoteTable().create(new Note()
                        .setTitle("test title")
                        .setBody("test body")
                        .setCategory(Category.INDIGO)
                        .setCreated(new Date())
                        .setHasReminder(true)
                        .setReminder(new Date())
                .setModified(new Date())
        );
        dbh.getNoteTable().deleteByKey(id);
    }
}
