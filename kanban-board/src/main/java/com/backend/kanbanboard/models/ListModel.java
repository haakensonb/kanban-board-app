package com.backend.kanbanboard.models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "list_model")
public class ListModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    // ListModel will be deleted if Board is deleted.
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    public ListModel() {
    }

    public ListModel(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    public ListModel(Long id, String name, Board board) {
        this.id = id;
        this.name = name;
        this.board = board;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ListModel)) {
            return false;
        }
        ListModel listModel = (ListModel) o;
        return Objects.equals(id, listModel.id) && Objects.equals(name, listModel.name)
                && Objects.equals(board, listModel.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, board);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", board='" + getBoard() + "'" + "}";
    }

}
