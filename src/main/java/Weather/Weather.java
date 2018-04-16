package Weather;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



@Path("/api")
public class Weather {
    @GET
    @Path("/weatherAll")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public ArrayList<Map<String, Object>> test() {
        PreparedStatement SelectStatement = null;
        Connection connection = connect();

        try {
            String InsertQuery = "Select * from WeatherPhenomen";
            SelectStatement = connection.prepareStatement(InsertQuery);
            SelectStatement.close();
            PreparedStatement selectPreparedStatement = connection.prepareStatement(InsertQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
//            System.out.println(rs);
            return getEntitiesFromResultSet(rs);
//            return rs;

//            return Response.ok().entity(new GenericEntity<List<Product»(result) {})
//            .header(“Access-Control-Allow-Origin”, “*”)
//            .header(“Access-Control-Allow-Methods”, “GET, POST, DELETE, PUT, OPTIONS, HEAD”)
//                    .build();
//            return rs;
//
//            request.setAttribute("textA", getEntitiesFromResultSet(rs));
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
//            dispatcher.forward(request, response);

        } catch (SQLException e) {
            System.out.println("Ошибочка: " + e);

        }
        return null;
    }

    private static Connection connect() {
        Connection conn = null;
        try {

            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:mem:test", "user", "user");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
        }
        return conn;
    }

    protected ArrayList<Map<String, Object>> getEntitiesFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Map<String, Object>> entities = new ArrayList<>();
        while (resultSet.next()) {
            entities.add(getEntityFromResultSet(resultSet));
        }
        return entities;
    }

    protected Map<String, Object> getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Map<String, Object> resultsMap = new HashMap<>();
        for (int i = 1; i <= columnCount; ++i) {
            String columnName = metaData.getColumnName(i).toLowerCase();
            Object object = resultSet.getObject(i);
            resultsMap.put(columnName, object);
        }
        return resultsMap;
    }
}





