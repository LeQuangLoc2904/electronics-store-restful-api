package com.loc.electronics_store.dto.request.product;

import lombok.*;
import java.util.List;
import java.util.Map;

/**
 * DTO để nhận các tiêu chí lọc từ client
 *
 * Ví dụ request:
 * {
 *   "categoryId": 1,
 *   "brandIds": [1, 2, 3],
 *   "minPrice": 5000000,
 *   "maxPrice": 20000000,
 *   "searchKeyword": "iPhone 15",
 *   "attributes": {
 *     "RAM": ["8GB", "12GB"],
 *     "Màn hình": ["6.1 inch", "6.7 inch"],
 *     "Chip": ["A17 Pro"]
 *   },
 *   "page": 0,
 *   "size": 20,
 *   "sortBy": "price",
 *   "sortDirection": "ASC"
 * }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterRequest {

    // Lọc theo danh mục sản phẩm
    private Long categoryId;

    // Lọc theo nhiều thương hiệu
    private List<Long> brandIds;

    // Lọc theo khoảng giá
    private Double minPrice;
    private Double maxPrice;

    // Tìm kiếm theo tên sản phẩm
    private String searchKeyword;

    /**
     * Lọc theo thuộc tính động (KEY CONCEPT!)
     *
     * Cấu trúc:
     * - Key: Tên thuộc tính (VD: "RAM", "Chip", "Màn hình")
     * - Value: Danh sách giá trị cần lọc (VD: ["8GB", "12GB"])
     *
     * Ví dụ thực tế:
     * attributes.put("RAM", Arrays.asList("8GB", "12GB"));
     * attributes.put("Chip", Arrays.asList("A17 Pro"));
     */
    private Map<String, List<String>> attributes;

    // Phân trang
    private Integer page;
    private Integer size;

    // Sắp xếp
    private String sortBy; // "price", "name", "createdAt"
    private String sortDirection; // "ASC", "DESC"

    // Mặc định nếu không được cung cấp
    public Integer getPageOrDefault() {
        return page != null ? page : 0;
    }

    public Integer getSizeOrDefault() {
        return size != null && size > 0 ? size : 20;
    }

    public String getSortByOrDefault() {
        return sortBy != null ? sortBy : "id";
    }

    public String getSortDirectionOrDefault() {
        return sortDirection != null ? sortDirection : "DESC";
    }
}

