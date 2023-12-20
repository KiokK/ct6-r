package by.kihtenkoolga.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationInfo {

    private Integer pageNumber;
    private Integer pageSize;

    public static final PaginationInfo DEFAULT = new PaginationInfo(0, 20);

    public int getOffset() {
        return pageNumber * pageSize;
    }

}
