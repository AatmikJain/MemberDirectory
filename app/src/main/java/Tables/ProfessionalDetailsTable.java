package Tables;

import android.database.sqlite.SQLiteDatabase;

public class ProfessionalDetailsTable
{
    private String tableName = "ProfessionalDetailsTable";
    private String email = "Email";
    private String companyName = "CompanyName";
    private String position  = "Position";
    private String joinDate = "JoinDate";
    private String endDate = "EndDate";

    public ProfessionalDetailsTable(SQLiteDatabase sqLiteDatabase)
    {
        UserTable userTable = new UserTable();
        String sql = "Create table " + tableName + " ( "+
                email + " varchar(30), " +
                companyName + " varchar(30), "+
                position + " varchar(20), "+
                joinDate + " date, "+
                endDate + " date, " +
                "foreign key (" + email + ") references " + userTable.getTableName()+"("+userTable.getEmail()+"));";
        sqLiteDatabase.execSQL(sql);
    }

    public ProfessionalDetailsTable(String email, String companyName, String position, String joinDate, String endDate) {
        this.email = email;
        this.companyName = companyName;
        this.position = position;
        this.joinDate = joinDate;
        this.endDate = endDate;
    }

    public ProfessionalDetailsTable() {

    }

    public String getTableName() {

        return tableName;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyName() {

        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
