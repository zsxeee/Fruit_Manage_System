package project.FMS.dao;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DAOTest {
    @Test
    public void testDao() throws Throwable {
        DAO dao = new DAO();
        List a = dao.select("SELECT * FROM test");
        assertEquals(0,a.size());
    }
}