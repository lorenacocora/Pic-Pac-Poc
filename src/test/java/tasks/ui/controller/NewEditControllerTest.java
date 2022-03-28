package tasks.ui.controller;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tasks.model.Task;
import tasks.model.ValidationException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

class NewEditControllerTest
{
    private NewEditController newEditController;
    final String titleTooShort ="Title too short!";
    final String titleTooLong ="Title too long!";
    final String titleTooLongAndDateTooDistant ="Title too long!\nDate too distant!";

    public Date convertToDateViaInstant(LocalDate dateToConvert) { return java.util.Date.from(dateToConvert.atStartOfDay() .atZone(ZoneId.systemDefault()) .toInstant()); }

    @BeforeEach
    void setUp() {
        try {
            newEditController =new NewEditController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @Tag("ECP")
    @DisplayName("testECP_addTask_Valid")
    void testECP_addTask_Valid() throws ParseException
    {
        Task addedTask = null;
        Task expectedTask = new Task("Cumparaturi",convertToDateViaInstant(LocalDate.now()),convertToDateViaInstant(LocalDate.now().plusDays(1)),true, 0);

        try{
            addedTask =  newEditController.addTask("Cumparaturi",convertToDateViaInstant(LocalDate.now()),false, convertToDateViaInstant(LocalDate.now().plusDays(1)),true);
        }
        catch (Exception e)
        {
            fail("Added failed!" + e.getMessage());
        }
        System.out.println(expectedTask.toString());
        System.out.println(addedTask.toString());
        assertTrue(addedTask.myEquals(expectedTask));
    }

    @RepeatedTest(2)
    @Tag("ECP")
    void testECP_addTask_TitleTooShort() throws ParseException
    {
      assertThrows(ValidationException.class,()-> newEditController.addTask("Ok", convertToDateViaInstant(LocalDate.now()),false, convertToDateViaInstant(LocalDate.now().plusDays(1)),true),titleTooShort);
    }

    @Test
    @Tag("ECP")
    @Timeout(60)
    void testECP_addTask_TitleTooLong_StartingDateTooDistant() throws ParseException
    {
        assertThrows(ValidationException.class,()-> newEditController.addTask("CevaTitluCuPeste20DeCaractere", convertToDateViaInstant(LocalDate.now().plusYears(100)),false, convertToDateViaInstant(LocalDate.now().plusYears(100).plusDays(1)),true),titleTooLongAndDateTooDistant);
    }

    @ParameterizedTest
    @Tag("BVA")
    @ValueSource(strings = {"AAA","AAAAAAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAA"})
    void testBVA_addTask_TitleLowerEnd_Valid(String title)
    {
        Task addedTask = null;
        Task expectedTask = new Task(title,convertToDateViaInstant(LocalDate.now()),convertToDateViaInstant(LocalDate.now().plusDays(1)),true, 1);
        try{
            addedTask =  newEditController.addTask(title, convertToDateViaInstant(LocalDate.now()),true, convertToDateViaInstant(LocalDate.now().plusDays(1)),true);
        }
        catch (Exception e)
        {
            fail("Added failed!" + e.getMessage());
        }
        assertTrue(addedTask.myEquals(expectedTask));
    }

    @Test
    @Tag("BVA")
    @Disabled
    void testBVA_addTask_TitleLowerEndPlusOne_Valid() throws ParseException
    {
        Task addedTask = null;
        Task expectedTask = new Task("AAAA",convertToDateViaInstant(LocalDate.now()),convertToDateViaInstant(LocalDate.now().plusDays(1)),true, 1);
        try{
            addedTask =  newEditController.addTask("AAAA", convertToDateViaInstant(LocalDate.now()),true, convertToDateViaInstant(LocalDate.now().plusDays(1)),true);
        }
        catch (Exception e)
        {
            fail("Added failed!" + e.getMessage());
        }
        assertTrue(addedTask.myEquals(expectedTask));
    }

    @Test
    @Tag("BVA")
    void testBVA_addTask_TitleLowerEndMinusOne() throws ParseException
    {
        assertThrows(ValidationException.class,()-> newEditController.addTask("Ok", convertToDateViaInstant(LocalDate.now()),true, convertToDateViaInstant(LocalDate.now().plusDays(1)),true),titleTooShort);
    }

    @Test
    @Tag("BVA")
    void testBVA_addTask_TitleUpperEndPlusOne() throws ParseException
    {
        assertThrows(ValidationException.class,()-> newEditController.addTask("AAAAAAAAAAAAAAAAAAAAA", convertToDateViaInstant(LocalDate.now()),true, convertToDateViaInstant(LocalDate.now().plusDays(1)),true),titleTooLong);
    }

    @AfterEach
    void tearDown() {
    }
}
