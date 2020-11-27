package ru.job4j.html;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            cnn = DriverManager.getConnection(cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        String addPost = "Insert into job.post (name, author, description, link, created) "
                + "values(?, ?, ?, ?, ?)";
                try (PreparedStatement statement = cnn.prepareStatement(addPost)) {
                    statement.setString(1, post.getTitle());
                    statement.setString(2, post.getAuthor());
                    statement.setString(3, post.getDescription());
                    statement.setString(4, post.getUrl());
                    statement.setDate(5, Date.valueOf(post.getCreationTime()));
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    }

    @Override
    public List<Post> getAll() {
        List<Post> rsl = new ArrayList<>();
        String getAll = "select * from job.post";
        try (Statement statement = cnn.createStatement();
             ResultSet resultSet = statement.executeQuery(getAll)) {
            while (resultSet.next()) {
                rsl.add(postInfo(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rsl;
    }

    @Override
    public Post findById(String id) {
        Post post = new Post();
        String findById = "select * from job.post where id = ?";
        try (PreparedStatement statement = cnn.prepareStatement(findById)) {
            statement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                post = postInfo(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static Post postInfo(ResultSet resultSet) throws Exception {
        return new Post(resultSet.getDate(6).toLocalDate(),
                resultSet.getString(5),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4));
    }

    public static void main(String[] args) {
       Properties properties = new Properties();
       try (InputStream in = PsqlStore.class.getClassLoader()
               .getResourceAsStream("rabbit.properties")) {
           properties.load(in);
       } catch (Exception e) {
           e.printStackTrace();
       }
       PsqlStore store = new PsqlStore(properties);
       SqlRuParse parse = new SqlRuParse(properties.getProperty("jdbc.url"));
       List<Post> posts = parse.list("https://www.sql.ru/forum/job-offers/");
       /*posts.forEach(store::save);*/
       store.getAll().forEach(System.out::println);
    }
}
