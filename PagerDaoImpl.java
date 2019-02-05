package edu.acc.j2ee.hubbub;

import java.util.ArrayList;
import java.util.List;

public class PagerDaoImpl implements PagerDao {

    @Override
    public int fetchCurrentPage(int page, List<Post> thePosts, int size, int adjPageSize) {
        for (int i = 0; i < thePosts.size(); i++) {
            if (i > (adjPageSize))
                page++; i = 0; break;
        }
        return page + 1;    
    }

    @Override
    public boolean isBack(int currentPage) {
        if (!(currentPage != 0))
            return false;
        else return true;
    }

    @Override
    public boolean isMore(int currentPage, int size, int adjPageSize) {
        if (!(currentPage != (size / adjPageSize)))
            return false;
        else return true;
    }

    @Override
    public void start(int currentPage) {
        currentPage = 1;       
    }

    @Override
    public void end(int currentPage, int size, int adjPageSize) {
       currentPage = size / adjPageSize;
    }

    @Override
    public int lastPage(int size, int adjPageSize) {
        return size / adjPageSize;     
    }

    @Override
    public boolean next(int currentPage, int size, int adjPageSize) {
        if (currentPage != (size / adjPageSize))
            return true;
        else return false;       
    }

    @Override
    public boolean prev(int currentPage) {
        if (currentPage != 1)
            return true;
        else return false;
    }   

    @Override
    public List<Integer> determineDropDownOptions(int pageSize, int size) {
        List<Integer> options = new ArrayList<>();
        options.add(pageSize);
        for (int i = 0; i < size; i++) {
            if ((i + 10) >= pageSize && (i + 10) <= size)
                options.add(i);  
        }
        options.add(size);
        return options;   
    }
}
 