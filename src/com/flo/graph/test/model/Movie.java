package com.flo.graph.test.model;

import java.util.List;

public class Movie {

	private Integer movieId;
	private String title;
	private List<String> generas;
	
	public List<String> getGeneras() {
		return generas;
	}
	public void setGeneras(List<String> generas) {
		this.generas = generas;
	}
	public Movie(Integer movieId, String title,  List<String> generas) {
		this.movieId = movieId;
		this.title = title;
        this.generas=generas;		
		
	}
	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}
