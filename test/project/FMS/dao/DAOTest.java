package project.FMS.dao;

import org.junit.Test;
import project.FMS.example.Inventory;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class DAOTest {
    @Test
    public void testDao() throws Throwable {
        Matcher matcher = Pattern.compile("\\d").matcher((
               "1#橘子"
        ));
        System.out.println(matcher.find());
        System.out.println(matcher);
        
        assertEquals(true, matcher.group(0));
    }
}