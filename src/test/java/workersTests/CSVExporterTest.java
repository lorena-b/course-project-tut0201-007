package workersTests;

import static org.junit.Assert.*;

import controllers.Controller;
import entities.Course;
import entities.Schedule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import workers.CSVExporter;
import workers.Scheduler;

public class CSVExporterTest {

    String dummyCSV;
    Scheduler sr;
    Schedule s;
    List<String> courseCodes;
    List<Course> courses;
    CSVExporter c;

    @Before
    public void setUp() throws Exception {
        dummyCSV =
                "Subject,Start Date,Start Time,End Date,End Time,Location\n"
                        + "TST102 TUT-0101 Y,2021-09-14,13:00,2021-09-14,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-09-21,13:00,2021-09-21,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-09-28,13:00,2021-09-28,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-10-05,13:00,2021-10-05,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-10-12,13:00,2021-10-12,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-10-19,13:00,2021-10-19,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-10-26,13:00,2021-10-26,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-11-02,13:00,2021-11-02,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-11-09,13:00,2021-11-09,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-11-16,13:00,2021-11-16,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-11-23,13:00,2021-11-23,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-11-30,13:00,2021-11-30,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2021-12-07,13:00,2021-12-07,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-01-11,13:00,2022-01-11,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-01-18,13:00,2022-01-18,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-01-25,13:00,2022-01-25,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-02-01,13:00,2022-02-01,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-02-08,13:00,2022-02-08,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-02-15,13:00,2022-02-15,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-02-22,13:00,2022-02-22,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-03-01,13:00,2022-03-01,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-03-08,13:00,2022-03-08,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-03-15,13:00,2022-03-15,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-03-22,13:00,2022-03-22,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-03-29,13:00,2022-03-29,14:00,WB 119\n"
                        + "TST102 TUT-0101 Y,2022-04-05,13:00,2022-04-05,14:00,WB 119\n"
                        + "TST103 TUT-0101 Y,2021-09-14,10:00,2021-09-14,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-09-21,10:00,2021-09-21,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-09-28,10:00,2021-09-28,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-10-05,10:00,2021-10-05,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-10-12,10:00,2021-10-12,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-10-19,10:00,2021-10-19,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-10-26,10:00,2021-10-26,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-11-02,10:00,2021-11-02,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-11-09,10:00,2021-11-09,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-11-16,10:00,2021-11-16,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-11-23,10:00,2021-11-23,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-11-30,10:00,2021-11-30,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2021-12-07,10:00,2021-12-07,11:00,ONLINE\n"
                        + "TST103 TUT-0101 Y,2022-01-11,10:00,2022-01-11,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-01-18,10:00,2022-01-18,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-01-25,10:00,2022-01-25,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-02-01,10:00,2022-02-01,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-02-08,10:00,2022-02-08,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-02-15,10:00,2022-02-15,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-02-22,10:00,2022-02-22,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-03-01,10:00,2022-03-01,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-03-08,10:00,2022-03-08,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-03-15,10:00,2022-03-15,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-03-22,10:00,2022-03-22,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-03-29,10:00,2022-03-29,11:00,ROOM 06\n"
                        + "TST103 TUT-0101 Y,2022-04-05,10:00,2022-04-05,11:00,ROOM 06\n"
                        + "TST102 LEC-0101 Y,2021-09-13,09:00,2021-09-13,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-09-20,09:00,2021-09-20,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-09-27,09:00,2021-09-27,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-04,09:00,2021-10-04,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-11,09:00,2021-10-11,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-18,09:00,2021-10-18,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-25,09:00,2021-10-25,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-01,09:00,2021-11-01,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-08,09:00,2021-11-08,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-15,09:00,2021-11-15,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-22,09:00,2021-11-22,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-29,09:00,2021-11-29,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-12-06,09:00,2021-12-06,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-01-10,09:00,2022-01-10,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-01-17,09:00,2022-01-17,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-01-24,09:00,2022-01-24,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-01-31,09:00,2022-01-31,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-07,09:00,2022-02-07,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-14,09:00,2022-02-14,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-21,09:00,2022-02-21,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-28,09:00,2022-02-28,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-07,09:00,2022-03-07,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-14,09:00,2022-03-14,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-21,09:00,2022-03-21,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-28,09:00,2022-03-28,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-04-04,09:00,2022-04-04,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-09-14,09:00,2021-09-14,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-09-21,09:00,2021-09-21,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-09-28,09:00,2021-09-28,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-05,09:00,2021-10-05,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-12,09:00,2021-10-12,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-19,09:00,2021-10-19,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-26,09:00,2021-10-26,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-02,09:00,2021-11-02,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-09,09:00,2021-11-09,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-16,09:00,2021-11-16,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-23,09:00,2021-11-23,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-30,09:00,2021-11-30,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-12-07,09:00,2021-12-07,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-01-11,09:00,2022-01-11,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-01-18,09:00,2022-01-18,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-01-25,09:00,2022-01-25,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-01,09:00,2022-02-01,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-08,09:00,2022-02-08,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-15,09:00,2022-02-15,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-22,09:00,2022-02-22,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-01,09:00,2022-03-01,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-08,09:00,2022-03-08,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-15,09:00,2022-03-15,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-22,09:00,2022-03-22,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-29,09:00,2022-03-29,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-04-05,09:00,2022-04-05,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-09-09,09:00,2021-09-09,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-09-16,09:00,2021-09-16,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-09-23,09:00,2021-09-23,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-09-30,09:00,2021-09-30,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-07,09:00,2021-10-07,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-14,09:00,2021-10-14,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-21,09:00,2021-10-21,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-10-28,09:00,2021-10-28,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-04,09:00,2021-11-04,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-11,09:00,2021-11-11,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-18,09:00,2021-11-18,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-11-25,09:00,2021-11-25,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-12-02,09:00,2021-12-02,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2021-12-09,09:00,2021-12-09,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-01-13,09:00,2022-01-13,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-01-20,09:00,2022-01-20,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-01-27,09:00,2022-01-27,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-03,09:00,2022-02-03,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-10,09:00,2022-02-10,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-17,09:00,2022-02-17,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-02-24,09:00,2022-02-24,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-03,09:00,2022-03-03,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-10,09:00,2022-03-10,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-17,09:00,2022-03-17,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-24,09:00,2022-03-24,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-03-31,09:00,2022-03-31,10:00,LM 159\n"
                        + "TST102 LEC-0101 Y,2022-04-07,09:00,2022-04-07,10:00,LM 159\n"
                        + "TST103 LEC-0101 Y,2021-09-09,12:00,2021-09-09,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-09-16,12:00,2021-09-16,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-09-23,12:00,2021-09-23,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-09-30,12:00,2021-09-30,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-10-07,12:00,2021-10-07,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-10-14,12:00,2021-10-14,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-10-21,12:00,2021-10-21,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-10-28,12:00,2021-10-28,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-11-04,12:00,2021-11-04,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-11-11,12:00,2021-11-11,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-11-18,12:00,2021-11-18,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-11-25,12:00,2021-11-25,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-12-02,12:00,2021-12-02,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2021-12-09,12:00,2021-12-09,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-01-13,12:00,2022-01-13,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-01-20,12:00,2022-01-20,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-01-27,12:00,2022-01-27,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-02-03,12:00,2022-02-03,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-02-10,12:00,2022-02-10,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-02-17,12:00,2022-02-17,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-02-24,12:00,2022-02-24,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-03-03,12:00,2022-03-03,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-03-10,12:00,2022-03-10,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-03-17,12:00,2022-03-17,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-03-24,12:00,2022-03-24,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-03-31,12:00,2022-03-31,15:00,ONLINE\n"
                        + "TST103 LEC-0101 Y,2022-04-07,12:00,2022-04-07,15:00,ONLINE\n";
    }

    @Test(timeout = 5000)
    public void testExport() {
        sr = new Scheduler();
        courseCodes = new ArrayList<>();
        courseCodes.add("TST102Y");
        courseCodes.add("TST103Y");
        courses = Controller.courseInstantiator(courseCodes);
        s = sr.createBasicSchedule(courses);
        c = new CSVExporter();
        c.outputSchedule(s);

        String expected = dummyCSV;
        try {
            assertEquals(expected, c.toString("CSVSchedule_2021-2022_0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(timeout = 5000)
    public void testExportWithCustomName() {
        sr = new Scheduler();
        courseCodes = new ArrayList<>();
        courseCodes.add("TST102Y");
        courseCodes.add("TST103Y");
        courses = Controller.courseInstantiator(courseCodes);
        s = sr.createBasicSchedule(courses);
        c = new CSVExporter();
        c.outputSchedule(s, "Schedule");

        String expected = dummyCSV;
        try {
            assertEquals(expected, c.toString("Schedule"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
