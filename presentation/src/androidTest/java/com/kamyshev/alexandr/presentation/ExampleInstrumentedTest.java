package com.kamyshev.alexandr.presentation;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kamyshev.alexandr.data.repositories.ProjectsListRepositoryImpl;
import com.kamyshev.alexandr.domain.global.models.Project;
import com.kamyshev.alexandr.domain.global.models.SubTask;
import com.kamyshev.alexandr.domain.global.models.Task;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Test
    public void useDb() {

        List<Task> taskList = new ArrayList();
        List<SubTask> subtaskList = new ArrayList();

        subtaskList.add(new SubTask("SubTask", true));
        taskList.add(new Task("First part", 10, subtaskList));

        new ProjectsListRepositoryImpl().saveProject(new Project("Disign", 10, taskList));

        List<Project> projects = new ProjectsListRepositoryImpl().getProjects();
        assertEquals(projects.get(0).getName(), "Disign");
        assertEquals(projects.get(0).getProgress(), 10);
        assertEquals(projects.get(0).getTasks(), taskList);
    }
}
