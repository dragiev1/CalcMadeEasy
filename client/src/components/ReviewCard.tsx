import React from "react";
import "../css/ReviewCard.css";
import avatar from "../assets/default-user-picture.png";
import { useScrollReveal } from "../utils/useScrollReveal";

// TODO: Move to model folder later when implementing real google reviews!
interface ReviewCardProps {
    name: string;
    rating: number;
    text: string;
    avatarUrl?: string; // optional google account pfp.
    delay?: number;
}

const StarIcon: React.FC<{filled: boolean}> = ({filled}) => (
    <svg
        className={`star ${filled ? "filled" : "empty"}`}
        viewBox="0 0 24 24"
        fill="currentColor"
        xmlns="http://www.w3.org/2000/svg"
    >
        <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z" />
    </svg>
);

const ReviewCard: React.FC<ReviewCardProps> = ({
    name,
    rating,
    text,
    avatarUrl = avatar,
    delay = 0,
}) => {
    const [cardRef, isVisible] = useScrollReveal();
    return (
        <div ref={cardRef} className={`review-card ${isVisible ? 'visible' : ''}`} style={{ transitionDelay: `${delay}ms` }}>
            {/* Quotation marks */}
            <span className="quote-mark left">&ldquo;</span>
            <span className="quote-mark right">&rdquo;</span>

            {/* Avatar / Camera placeholder */}
            <div className="avatar-placeholder">
                {avatarUrl ? (
                <img
                    src={avatarUrl}
                    alt={name}
                    className="w-full h-full object-cover rounded-2xl"
                />
                ) : (
                    <img
                    src={avatarUrl}
                    alt={name}
                    className="w-full h-full object-cover rounded-2xl"
                />
                )}
            </div>

            {/* Name */}
            <div className="reviewer-name">{name.toUpperCase()}</div>

            {/* Stars */}
            <div className="stars">
                {[1, 2, 3, 4, 5].map((i) => (
                    <StarIcon key={i} filled={i <= rating} />
                ))}
            </div>

            {/* Review Text */}
            <p className="review-text">{text}</p>
        </div>
    );
};

export default ReviewCard;