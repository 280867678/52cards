package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.UserDao;
import model.User;
import util.DBUtil;

public class UserDaoImpl implements UserDao{
	public User checkLogin(String userName, String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select * from  user where userName=? and password=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, password);
			rs = ps.executeQuery();
			while(rs.next()){
				user = new User();
				user.setUserName(rs.getString("userName"));
				user.setNickName(rs.getString("password"));
				user.setNickName(rs.getString("nickName"));
				user.setMoney(rs.getInt("money"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBUtil.closeConnection(conn);
			DBUtil.realeaseAll(rs, ps, conn);
		}
		return user;
	}

	@Override
	public boolean register(String userName, String password, String nickName) {
		Connection conn=null;
		PreparedStatement ps=null;
		int rs=0;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="insert into user(userName,nickname,password) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, nickName);
			ps.setString(3, password);
			try{
				rs = ps.executeUpdate();
				conn.commit();
			}catch (Exception e){
				conn.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{
			DBUtil.closeConnection(conn);
		}		
		return rs==1;
	}


}