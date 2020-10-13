package net.xilla.rssposter.post;

import net.xilla.core.library.json.SerializedObject;
import net.xilla.core.library.json.XillaJson;
import net.xilla.core.library.manager.Manager;
import net.xilla.core.library.manager.ManagerObject;

public class FeedMessage implements SerializedObject{

    private String title;
    private String description;
    private String link;
    private String author;
    private String guid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        return "FeedMessage [title=" + title + ", description=" + description
                + ", link=" + link + ", author=" + author + ", guid=" + guid
                + "]";
    }

    @Override
    public XillaJson getSerializedData() {
        XillaJson json = new XillaJson();

        json.put("title", title);
        json.put("description", description);
        json.put("link", link);
        json.put("author", author);
        json.put("guid", guid);

        return json;
    }

    @Override
    public void loadSerializedData(XillaJson xillaJson) {
        this.title = xillaJson.get("title");
        this.description = xillaJson.get("description");
        this.link = xillaJson.get("link");
        this.author = xillaJson.get("author");
        this.guid = xillaJson.get("guid");
    }
}
