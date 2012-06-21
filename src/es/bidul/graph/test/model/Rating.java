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
