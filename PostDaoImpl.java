package edu.acc.j2ee.hubbub;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostDaoImpl implements PostDao {
    
    private static final List<Post> posts = new ArrayList<>();

    @Override
    public void addPost(Post post) {
        posts.add(post);
    }

    @Override
    public List<Post> findByRange(int currentPage, int size) {
        return posts.stream()
                .sorted((a,b) -> b.getPostDate().compareTo(a.getPostDate()))
                .skip(currentPage * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> retrieveSizeOfPosts() {
        return posts;
    }

}
