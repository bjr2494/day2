package edu.acc.j2ee.hubbub;

import java.util.List;

public interface PostDao {
    public void addPost(Post post);
    public List<Post> findByRange(int currentPage, int size);
    public List<Post> retrieveSizeOfPosts();

}
