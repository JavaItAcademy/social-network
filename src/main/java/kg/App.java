package kg;

import kg.entities.Comment;
import kg.entities.Post;
import kg.entities.User;
import kg.models.CommentWithUsernameModel;
import kg.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Creating user
        User user = new User();
        user.setUsername("it");
        user.setFollowers(new ArrayList<>());
        create(user);

        User userLiker = new User();
        userLiker.setUsername("Liker");
        create(userLiker);

        //Creating post from user "it"
        Post post = new Post();
        post.setHeader("First post");
        post.setText("Welcome to page");
        post.setUser(user);
        post.setUsers(new ArrayList<>());
        create(post);

        //Comment from user "it"
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Кто смотрит в 2020");
        create(comment);

        Post postFromBd = getList(Post.class).get(0);

        System.out.println(postFromBd);

        postFromBd.setText("Hello123");
        //Like to post from user "Liker"
        postFromBd.getUsers().add(userLiker);
        create(postFromBd);
        List<User> users = getList(User.class);
        System.out.println(users);

        getByUserName("it");


        //Add follower to User "it"
        User userFromBd = getList(User.class).get(0);
        userFromBd.getFollowers().add(userLiker);
        create(userFromBd);

    }

    @SuppressWarnings("unchecked")
    public static List<CommentWithUsernameModel> getByUserName(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> result = session
                .createQuery("select u.username, c.text from Comment c join c.user u where u.username = :username")
                .setParameter("username", username).list();
        session.close();

        List<CommentWithUsernameModel> modelResult = result.stream()
                .map(x -> CommentWithUsernameModel.builder().username(x[0].toString()).text(x[1].toString()).build())
                .collect(Collectors.toList());

        List<CommentWithUsernameModel> modelResult2 = new ArrayList<>();
        for(Object[] i : result) {
            CommentWithUsernameModel commentWithUsernameModel = new CommentWithUsernameModel();
            commentWithUsernameModel.setUsername(i[0].toString());
            commentWithUsernameModel.setText(i[1].toString());
            modelResult2.add(commentWithUsernameModel);
        }
        System.err.println("1: " + modelResult);
        System.err.println("2: " + modelResult2);

        return modelResult;
    }

    public static<T> T getEntity(Integer id, Class<T> type) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T entity = session.get(type, id); //Post post = session.get(Post.class, id);
        session.close();
        return entity;
    }

    @SuppressWarnings("unchecked")
    public static<T> List<T> getList(Class<T> type) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<T> list = session.createQuery("from " + type.getName()).list(); //Post post = session.get(Post.class, id);
        session.close();
        return list;
    }

    public static <T> void create(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
        session.close();
        System.out.println("Создали запись успешно");
    }
}
