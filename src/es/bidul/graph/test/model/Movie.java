/**
 * Copyright (C) 2012 Florent Empis florent.empis@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.bidul.graph.test.model;

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
