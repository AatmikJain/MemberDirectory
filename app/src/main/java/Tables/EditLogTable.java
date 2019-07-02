package Tables;

import android.database.sqlite.SQLiteDatabase;

public class EditLogTable
{
    private String tableName = "EditLogTable";
    private String email = "Email";
    private String lastEdit = "LastEdit";

    public EditLogTable(SQLiteDatabase sqLiteDatabase)
    {
        UserTable userTable = new UserTable();
        String sql = "Create table " + tableName + " ( "+
                email + " int(7), " +
                lastEdit + " varchar(25), " +
                "foreign key (" + email + ") references " + userTable.getTableName()+"("+userTable.getEmail()+"));";
        sqLiteDatabase.execSQL(sql);
    }

    public EditLogTable(String email, String lastEdit) {
        this.email = email;
        this.lastEdit = lastEdit;
    }

    public EditLogTable() {

    }

    public String getTableName() {
        return tableName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(String lastEdit) {
        this.lastEdit = lastEdit;
    }
}
