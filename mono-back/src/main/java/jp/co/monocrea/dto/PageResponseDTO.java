package jp.co.monocrea.dto;

import java.util.List;

public class PageResponseDTO<T> {

    public long total;
    public int page;
    public int size;
    public List<T> data;

    public PageResponseDTO(long total, int page, int size, List<T> data) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.data = data;
    }
}