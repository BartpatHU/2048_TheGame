package hu.projekt.dao;

import hu.projekt.model.Score;

import java.util.List;

public interface ScoreDAO {

    List<Score> listALL();

    boolean addScore(Score score);
}
