package servicios.database.dao_impl;

import java.util.ArrayList;
import java.util.List;

import modelo.Catedra;
import servicios.database.Dao;
import servicios.database.IDao;

import java.sql.SQLException;

public class CatedraDao extends Dao implements IDao<Catedra, String> {

    private static CatedraDao catedraDao = null;

	private CatedraDao(){}

	public static CatedraDao getInstance(){
		if (catedraDao == null)
			catedraDao = new CatedraDao();
		return catedraDao;
	}

    @Override
    public List<Catedra> getAll() {
        
        List<Catedra> catedras = new ArrayList<>();
        try {
			String queryString = "SELECT * FROM catedras";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			while (resultSet.next()) {
                catedras.add(new Catedra(resultSet.getString("caid")));
			}
            return catedras;
		} catch (SQLException e) {
			e.printStackTrace();
            return catedras;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

    }

	@Override
	public Catedra get(String id) {

		Catedra catedra = new Catedra();
        try {
			String queryString = "SELECT * FROM catedras WHERE caid = ?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, id);
			resultSet = ptmt.executeQuery();
			if (resultSet.next()) {
				catedra.setNombre(resultSet.getString("caid"));
				catedra.setPaginaWeb(resultSet.getString("caurl"));

				return catedra;
			}
            return catedra;
		} catch (SQLException e) {
			e.printStackTrace();
            return catedra;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Catedra elem) {
		
        try {
			String queryString = "UPDATE catedras SET caid=?, caurl=? WHERE caid=?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, elem.getNombre());
			ptmt.setString(2, elem.getUrlPaginaWeb());
			ptmt.setString(3, elem.getNombre());
			ptmt.executeUpdate();
			System.out.println("Table Updated Successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			}

			catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		
	}

	@Override
	public boolean delete(String id) {
		
        try {
			String queryString = "DELETE FROM catedras WHERE caid=?";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			ptmt.setString(1, id);
			ptmt.executeUpdate();
			System.out.println("Data deleted Successfully");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}


	}

	@Override
	public void add(Catedra elem) {
		
        try {

            String queryString = "INSERT INTO catedra(caid, caurl) VALUES(?,?)";
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);

            ptmt.setString(1, elem.getNombre());
            ptmt.setString(2, elem.getUrlPaginaWeb());
            ptmt.executeUpdate();

            System.out.println("Data Added Successfully");
    
            } catch (Exception e) {
            	e.printStackTrace();
            }finally{
    
            try {
                    if (ptmt != null)
                        ptmt.close();
                    if (connection != null)
                        connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
		
	}

    


    
}
