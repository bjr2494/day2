package edu.acc.j2ee.hubbub;

import java.util.List;

public interface PagerDao {
    public int fetchCurrentPage(int page, List<Post> thePosts, int size, int adjPageSize);
    public boolean isBack(int currentPage);
    public boolean isMore(int currentPage, int size, int adjPageSize);
    public void start(int currentPage);
    public void end(int currentPage, int size, int adjPageSize);
    public int lastPage(int size, int pageSize);
    public boolean next(int currentPage, int size, int adjPageSize);
    public boolean prev(int currentPage); 
    public List<Integer> determineDropDownOptions(int pageSize, int size);
}

