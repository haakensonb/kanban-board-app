package com.backend.kanbanboard.models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = Access.READ_ONLY)
    private Long id;

    private String title;

    private String description;

    // @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(nullable = false)
    private ListModel list;

    // @Column(name = "list_id", nullable = false)
    // private Long listId;

    public Card() {
    }

    public Card(String title, String description, ListModel list) {
        this.title = title;
        this.description = description;
        this.list = list;
    }

    public Card(Long id, String title, String description, ListModel list) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.list = list;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListModel getList() {
        return this.list;
    }

    public void setList(ListModel newList) {
        this.list = newList;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Card)) {
            return false;
        }
        Card card = (Card) o;
        return Objects.equals(getId(), card.getId()) && Objects.equals(title, card.title)
                && Objects.equals(description, card.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), title, description);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", title='" + getTitle() + "'" + ", description='" + getDescription()
                + "'" + "}";
    }

}
