package ca.qc.johnabbott.cs616.notes.server.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Note class
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
@Entity
@NoteDatesRange
@Table(name="note")
public class Note {

    @Id
    @GeneratedValue(generator = UuidGenerator.generatorName)
    @GenericGenerator(name = UuidGenerator.generatorName, strategy = "ca.qc.johnabbott.cs616.notes.server.model.UuidGenerator")
    @Column(name = "uuid")
    private String uuid;

    @Column(name="created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name="reminder")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reminder;

    @Column(name="modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="body")
    private String body;

    @Column(name="category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany(mappedBy = "notes")
    private List<User> collaborators;

    public String getUuid() {
        return uuid;
    }

    public Note setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public List<User> getCollaborators() {
        return collaborators;
    }

    public Note setCollaborators(List<User> collaborators) {
        this.collaborators = collaborators;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getReminder() {
        return reminder;
    }

    public void setReminder(Date reminder) {
        this.reminder = reminder;
    }

     public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
