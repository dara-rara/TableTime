package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.ReservalRepository;
import com.example.TableTime.adapter.repository.ReviewRepository;
import com.example.TableTime.adapter.repository.UserRepository;
import com.example.TableTime.adapter.web.user.dto.review.ReviewForm;
import com.example.TableTime.domain.restaurant.ReviewEntity;
import com.example.TableTime.domain.restaurant.reserval.State;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReviewService {
    private final ReservalRepository reservalRepository;
    private final ReviewRepository reviewRepository;

    public void createReview (ReviewForm reviewForm) {
        var review = new ReviewEntity();
        var reserval = reservalRepository.getReferenceById(reviewForm.idReserval());
        reserval.setState(State.RATED);
        review.setReserval(reserval);
        review.setUser(reserval.getUser());
        review.setRestaurant(reserval.getRestaurant());
        review.setText(reviewForm.text());
        review.setGrade(reviewForm.grade());
        reviewRepository.save(review);
    }
}
