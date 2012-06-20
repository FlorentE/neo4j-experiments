package com.flo.graph.test.model;

import java.util.Date;

public class Rating {

    private Integer userId;
    private Integer movieId;
    private Integer nbStars;
    private Date ratingDate;
	
    public Rating(Integer userId, Integer movieId, Integer nbStars,
			Date ratingDate) {
	
		this.userId = userId;
		this.movieId = movieId;
		this.nbStars = nbStars;
		this.ratingDate = ratingDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public Integer getNbStars() {
		return nbStars;
	}

	public void setNbStars(Integer nbStars) {
		this.nbStars = nbStars;
	}

	public Date getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(Date ratingDate) {
		this.ratingDate = ratingDate;
	}

	@Override
	public String toString() {
		return "Rating [userId=" + userId + ", movieId=" + movieId
				+ ", nbStars=" + nbStars + ", ratingDate=" + ratingDate + "]";
	}

    
}
