package ch.ost.rj.sa.miro2cml.data_access.model.miro.boards;

import java.util.List;

public class BoardCollection {
    private String type;

    private int limit;

    private int offset;

    private int size;

    private String nextLink;

    private List<Data> data;

    private String prevLink;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getNextLink() {
        return this.nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public List<Data> getData() {
        return this.data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getPrevLink() {
        return this.prevLink;
    }

    public void setPrevLink(String prevLink) {
        this.prevLink = prevLink;
    }
}
