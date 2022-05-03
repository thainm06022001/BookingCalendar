/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.ArrayList;
import modal.Room;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modal.Customer;
import modal.Reservation;

/**
 *
 * @author Admin
 */
public class RoomDB extends DBContext {

    public ArrayList<Room> getElem() {
        ArrayList<Room> depts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM dbo.Room AS r LEFT JOIN dbo.Reservation AS rs ON r.rid = rs.rid LEFT JOIN dbo.Customer AS c ON c.cid = rs.cid";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Room d = new Room();
                d.setRid(rs.getInt(1));
                d.setName(rs.getString(2));
                Reservation r = new Reservation();
                r.setFrom(rs.getString("from"));
                r.setTo(rs.getString("to"));
                d.setFrom(rs.getString("from"));
                d.setTo(rs.getString("to"));
                d.setC(rs.getString("cname"));
                Customer c = new Customer(rs.getInt("cid"), rs.getString("cname"));
                depts.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return depts;
    }
}
