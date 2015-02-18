package db61b;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;


/** @author Christopher Quan
 * Tests basics functionality including:
 *  1. The row class
 */

public class BasicTests {
    @Test
    public void testRow() {
        Row r = new Row(new String[]{"I", "am", "doing", "a", "CS",
            "project."});
        assertEquals(6, r.size());
        assertEquals("I", r.get(0));
        Row s = new Row(new String[]{"I", "am", "doing", "a", "CS",
            "project."});
        Row t = new Row(new String[]{"I", "am", "doing", "a", "project."});
        assertEquals(false, r.equals(t));
    }

    @Test
    public void testTable() {
        List<String> titles = new ArrayList<String>();
        titles.add("Ranking");
        titles.add("Name");
        titles.add("Score");
        Table t1 = new Table(titles);
        assertEquals(3, t1.columns());
        assertEquals(0, t1.findColumn("Ranking"));
        assertEquals(2, t1.findColumn("Score"));
        assertEquals("Name", t1.getTitle(1));
        assertEquals(0, t1.size());
        Row r1 = new Row(new String[]{"1", "Ben", "10212"});
        Row r2 = new Row(new String[]{"2", "Jerry", "9432"});
        t1.add(r1);
        t1.add(r2);
        assertEquals(2, t1.size());
    }

    @Test
    public void readselectTest() {
        Table t1 = Table.readTable("Race");
        List<String> titles = new ArrayList<String>();
        titles.add("Ranking");
        titles.add("Name");
        titles.add("Score");
        Table t2 = new Table(titles);
        Row r1 = new Row(new String[]{"1", "Ben", "10212"});
        Row r2 = new Row(new String[]{"2", "Jerry", "9432"});
        t2.add(r1);
        t2.add(r2);
        assertEquals(t2.size(), t1.size());
        assertEquals(t2.findColumn("Ranking"), t1.findColumn("Ranking"));
        assertEquals(t2.getTitle(1), t1.getTitle(1));
        List<String> titles2 = new ArrayList<String>();
        titles2.add("Ranking");
        titles2.add("Name");
        Table t3 = t1.select(titles2, new ArrayList<Condition>());
        Table t4 = Table.readTable("Raceselect");
        assertEquals(t3.size(), t4.size());
        assertEquals(t3.findColumn("Ranking"), t4.findColumn("Ranking"));
        assertEquals(t3.getTitle(1), t4.getTitle(1));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(BasicTests.class));
    }
}
