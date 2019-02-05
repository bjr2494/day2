package edu.acc.j2ee.hubbub;

public class Pager implements java.io.Serializable {

    private int pageSize;
    private int page;
    private int size;
    private int pageNumber;
        
    public Pager() {
    }
    
    public Pager(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1)
            throw new IllegalArgumentException("Page size cannot be negative");
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return String.format("Pager[pageSize=%d, page=%d, size=%d, pageNumber=%d]",
            pageSize, page, size, pageNumber);
    }
   
}
