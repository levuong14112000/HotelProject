package com.example.hotelproject;

import java.sql.*;

public class RoomCheckOut_DAO {
    private static Connection connection = Conect.getInstance();

    public static ResultSet read() {
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM RoomCheckOuts";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public static void createRoomCheckOut(RoomCheckOut roomCheckOut, int checkInID, int userID, int roomID) {
        String selectQuery = "SELECT CheckOutID FROM RoomCheckOuts WHERE CheckInID = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, checkInID);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Nếu đã có cùng CheckInID, thực hiện cập nhật (update)
                    int checkOutID = resultSet.getInt("CheckOutID");
                    updateRoomCheckOut(roomCheckOut, checkOutID, userID);
                } else {
                    // Nếu chưa có, thực hiện thêm mới (insert)
                    insertRoomCheckOut(roomCheckOut, userID, roomID, checkInID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertRoomCheckOut(RoomCheckOut roomCheckOut, int userID, int roomID, int checkInID) {
        String insertQuery = "INSERT INTO RoomCheckOuts (RoomID, CheckInID, UserID) VALUES (?, ?, ?)";
        try (
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setInt(1, roomID);
            insertStatement.setInt(2, checkInID);
            insertStatement.setInt(3, userID);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateRoomCheckOut(RoomCheckOut roomCheckOut, int checkOutID, int userID) {
        String updateQuery = "UPDATE RoomCheckOuts SET CheckOutTime = ?, UserID = ? WHERE CheckOutID = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setTimestamp(1, roomCheckOut.getCheckOutTime());
            updateStatement.setInt(2, userID);
            updateStatement.setInt(3, checkOutID);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String showCheckOutInformationWithID(int checkInID) {
        String checkOutTime = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT CheckOutTime FROM RoomCheckOuts WHERE CheckInID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, checkInID);

            rs = stmt.executeQuery();

            if (rs.next()) {
                checkOutTime = rs.getString("CheckOutTime");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkOutTime;
    }

    public static int getCheckOutIDWithCheckInID(int checkInID) {
        int checkOutID = 0;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT CheckOutID FROM RoomCheckOuts WHERE CheckInID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, checkInID);

            rs = stmt.executeQuery();

            if (rs.next()) {
                checkOutID = rs.getInt("CheckOutID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkOutID;
    }

    public static int countCheckOuts() {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) FROM RoomCheckOuts WHERE Deleted = 0";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
