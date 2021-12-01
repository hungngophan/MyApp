package Search;

public class ProductSearch {
    String string;
    int id;

    public ProductSearch(String string) {
        this.string = string;
    }

    public ProductSearch(String string, int id) {
        this.string = string;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
