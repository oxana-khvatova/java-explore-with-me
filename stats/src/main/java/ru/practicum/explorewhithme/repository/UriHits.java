package ru.practicum.explorewhithme.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;


public interface UriHits {
    String getApp();
    String getUri();
    Integer getHits();
}
