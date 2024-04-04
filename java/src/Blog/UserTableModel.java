package Blog;

import adapter.UserAdapter;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    private final List<UserAdapter> userAdapterList;
    private final String[] columnNames = new String[] {
            "Full Name", "User ID", "Username", "Real Name", "Date of Birth", "Gender", "Country", "Description", "Admin"
    };
    private final Class[] columnClass = new Class[] {
            String.class, Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, Boolean.class
    };

    public UserTableModel(List<UserAdapter> userAdapterList) {
        this.userAdapterList = userAdapterList;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return userAdapterList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserAdapter row = userAdapterList.get(rowIndex);
        if (0 == columnIndex) {
            return row.getFullName();
        } else if (1 == columnIndex) {
            return Integer.valueOf(row.getUserId());
        } else if (2 == columnIndex) {
            return row.getUsername();
        } else if (3 == columnIndex) {
            return row.getRealName();
        } else if (4 == columnIndex) {
            return row.getDateOfBirth();
        } else if (5 == columnIndex) {
            return row.getGender();
        } else if (6 == columnIndex) {
            return row.getCountry();
        } else if (7 == columnIndex) {
            return row.getDescription();
        } else if (8 == columnIndex) {
            return row.getAdmin();
        }
        return null;
    }

}
