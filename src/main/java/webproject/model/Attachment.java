package webproject.model;

public class Attachment {

    private int aid;
    private int lid;
    private String name;
    private String mimeContentType;
    private byte[] contents;

    public Attachment(int aid, int lid, String name, String mimeContentType, byte[] contents) {
        this.aid = aid;
        this.lid = lid;
        this.name = name;
        this.mimeContentType = mimeContentType;
        this.contents = contents;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeContentType() {
        return mimeContentType;
    }

    public void setMimeContentType(String mimeContentType) {
        this.mimeContentType = mimeContentType;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }
    
}
