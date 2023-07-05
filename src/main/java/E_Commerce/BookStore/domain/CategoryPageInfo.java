package E_Commerce.BookStore.domain;

import lombok.Getter;
import lombok.Setter;

public class CategoryPageInfo {
    private Integer totalPages;
    private Long totalElements;

    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public long getTotalElements() {
        return totalElements;
    }
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
