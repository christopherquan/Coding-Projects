package db61b;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static db61b.Utils.*;

/** A single table in a database.
 *  @author P. N. Hilfinger
 */
class Table implements Iterable<Row> {

    /** A new Table whose columns are given by TITLE, which may
     *  not contain dupliace names. */
    Table(String[] title) {
        for (int i = title.length - 1; i >= 1; i -= 1) {
            for (int j = i - 1; j >= 0; j -= 1) {
                if (title[i].equals(title[j])) {
                    throw error("duplicate column name: %s",
                                title[i]);
                }
            }
        }
        columnTitles = title;
    }

    /** A new Table whose columns are give by COLUMNTITLE. */
    Table(List<String> columnTitle) {
        this(columnTitle.toArray(new String[columnTitle.size()]));
    }

    /** Return the number of columns in this table. */
    public int columns() {
        return columnTitles.length;
    }

    /** Return the title of the Kth column.  Requires 0 <= K < columns(). */
    public String getTitle(int k) {
        if (k < 0 || k >= columns()) {
            throw error("Input out of range");
        }
        return columnTitles[k];
    }

    /** Return the number of the column whose title is TITLE, or -1 if
     *  there isn't one. */
    public int findColumn(String title) {
        int i = 0;
        for (String x : columnTitles) {
            if (x.equals(title)) {
                return i;
            }
            i++;
        }
        if (i == columns()) {
            i = -1;
        }
        return i;
    }

    /** Return the number of Rows in this table. */
    public int size() {
        return _rows.size();
    }

    /** Returns an iterator that returns my rows in an unspecfied order. */
    @Override
    public Iterator<Row> iterator() {
        return _rows.iterator();
    }

    /** Add ROW to THIS if no equal row already exists.  Return true if anything
     *  was added, false otherwise. */
    public boolean add(Row row) {
        if (row.size() == 0) {
            throw error("Cannot have an empty row.");
        }
        if (row.size() != columns()) {
            throw error("Row has improper number of columns");
        }
        int past = size();
        _rows.add(row);
        if (size() == past) {
            return false;
        }
        return true;
    }

    /** Read the contents of the file NAME.db, and return as a Table.
     *  Format errors in the .db file cause a DBException. */
    static Table readTable(String name) {
        BufferedReader input;
        Table table;
        input = null;
        table = null;
        try {
            input = new BufferedReader(new FileReader(name + ".db"));
            String header = input.readLine();
            if (header == null) {
                throw error("missing header in DB file");
            }
            String[] columnNames = header.split(",");
            table = new Table(columnNames);
            String rrows = input.readLine();
            while (rrows != null) {
                String[] rowdata = rrows.split(",");
                table.add(new Row(rowdata));
                rrows = input.readLine();
            }
        } catch (FileNotFoundException e) {
            throw error("could not find %s.db", name);
        } catch (IOException e) {
            throw error("problem reading from %s.db", name);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    /* Ignore IOException */
                }
            }
        }
        return table;
    }

    /** Write the contents of TABLE into the file NAME.db. Any I/O errors
     *  cause a DBException. */
    void writeTable(String name) {
        PrintStream output;
        output = null;
        try {
            output = new PrintStream(name + ".db");
            String line;
            line = "";
            if  (columns() == 0) {
                return;
            }
            line += getTitle(0);
            for (int j = 1; j < columns(); j++) {
                line = line + "," + getTitle(j);
            }
            output.append(line);
            output.append("\n");
            if  (size() == 0) {
                return;
            }
            for (Row row : _rows) {
                line = "";
                line += row.get(0);
                for (int i = 1; i < row.size(); i++) {
                    line = line + "," + row.get(i);
                }
                output.append(line);
                output.append("\n");
            }

        } catch (IOException e) {
            throw error("trouble writing to %s.db", name);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    /** Print my contents on the standard output. */
    void print() {
        String line;
        for (Row row : _rows) {
            line = " ";
            for (int i = 0; i < row.size(); i++) {
                line = line + " " + row.get(i);
            }
            System.out.println(line);
        }
    }

    /** Return a new Table whose columns are COLUMNNAMES, selected from
     *  rows of this table that satisfy CONDITIONS. */
    Table select(List<String> columnNames, List<Condition> conditions) {
        Table result = new Table(columnNames);
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (String nam : columnNames) {
            if (findColumn(nam) != -1) {
                indices.add(findColumn(nam));
            }
        }
        if (indices.size() != columnNames.size()) {
            throw error("Improper column names.");
        }
        String[] data;
        int j;
        for (Row row : _rows) {
            if (Condition.test(conditions, row)) {
                data = new String[indices.size()];
                j = 0;
                for (int x : indices) {
                    data[j] = row.get(x);
                    j++;
                }
                result.add(new Row(data));
            }
        }
        return result;
    }

    /** Return a new Table whose columns are COLUMNNAMES, selected
     *  from pairs of rows from this table and from TABLE2 that match
     *  on all columns with identical names and satisfy CONDITIONS. */
    Table select(Table table2, List<String> columnNames,
                 List<Condition> conditions) {
        Table result = new Table(columnNames);
        for (String nn : columnNames) {
            if (findColumn(nn) == -1 && table2.findColumn(nn) == -1) {
                throw error("Improper column names.");
            }
        }
        List<Column> com1 = new ArrayList<Column>();
        List<Column> com2 = new ArrayList<Column>();
        for (String name : columnTitles) {
            if (table2.findColumn(name) != -1) {
                com1.add(new Column(name, this));
                com2.add(new Column(name, table2));
            }
        }
        int j;
        String[] data;
        for (Row row1 : _rows) {
            for (Row row2 : table2) {
                if (equijoin(com1, com2, row1, row2) || com1.isEmpty()) {
                    if (Condition.test(conditions, row1, row2)) {
                        data = new String[columnNames.size()];
                        j = 0;
                        for (String n : columnNames) {
                            if (findColumn(n) != -1) {
                                data[j] = row1.get(findColumn(n));
                            } else {
                                data[j] = row2.get(table2.findColumn(n));
                            }
                            j++;
                        }
                        result.add(new Row(data));
                    }
                }
            }
        }
        return result;
    }

    /** Return true if the columns COMMON1 from ROW1 and COMMON2 from
     *  ROW2 all have identical values.  Assumes that COMMON1 and
     *  COMMON2 have the same number of elements and the same names,
     *  that the columns in COMMON1 apply to this table, those in
     *  COMMON2 to another, and that ROW1 and ROW2 come, respectively,
     *  from those tables. */
    private static boolean equijoin(List<Column> common1, List<Column> common2,
                                    Row row1, Row row2) {
        for (int i = 0; i < common1.size(); i++) {
            if (!common1.get(i).getFrom(row1).equals(
                common2.get(i).getFrom(row2))) {
                return false;
            }
        }
        return true;
    }

    /** My rows. */
    private HashSet<Row> _rows = new HashSet<>();
    /** Array string of the column titles. */
    private String[] columnTitles;
}

