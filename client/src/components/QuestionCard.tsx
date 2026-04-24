import "../css/QuestionCard.css";
import TagComponent from "../components/TagComponent";
import type { ProblemProps } from "../types/Problem";
import RadialGraph from "./RadialGraph";
import AnswerInput from "./AnswerInput";
import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTag } from "@fortawesome/free-solid-svg-icons";

const ProblemCard: React.FC<ProblemProps> = ({
  question,
  position,
  confidenceScore = 100,
  showQuestion = true,
  solved = false,
  tags = [], // Default to empty array.
  userSolution,
}) => {
  const [userAnswer, setUserAnswer] = useState(
    solved && userSolution?.answer ? userSolution.answer : "",
  );

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [showTags, setShowTags] = useState(true);

  useEffect(() => {
    if (userSolution?.answer) setUserAnswer(userSolution.answer);
  }, [solved, userSolution?.answer]);

  const handleSubmit = () => {
    if (!userAnswer.trim() || isSubmitting) return;
    setIsSubmitting(true);
    console.log("Checking answer:", userAnswer);
    // TODO: replace with real API call.
    setTimeout(() => {
      setIsSubmitting(false);
    }, 800);
  };

  const toggleTags = () => setShowTags((prev) => !prev);

  const evaluationStatus = solved
    ? userSolution?.isCorrect !== false
      ? "correct"
      : "incorrect"
    : isSubmitting
      ? "checking"
      : "idle";

  return (
    <>
      <div className="card-container">
        <div className="card-header">
          <div className="question-wrapper">
            <span className="question-number">
              {position}.{")"}
            </span>
            <div className="question-text">
              {/* Question */}
              {showQuestion && question}
            </div>
          </div>
          <div className="">
            {/* Confidence Score */}
            <RadialGraph
              value={confidenceScore}
              className="confidence-score"
              size={50}
              strokeWidth={5}
              secondaryColor={"gray"}
            />
          </div>
        </div>
        <div className="card-tags">
          <button
            type="button"
            className="tag-toggle-btn"
            onClick={toggleTags}
            aria-expanded={showTags}
            aria-label={showTags ? "Hide tags" : "Show tags"}
          >
            <FontAwesomeIcon
              icon={faTag}
              className={`tag-icon ${showTags ? "active" : ""}`}
            />
          </button>
          {showTags &&
            tags.map((tag, index) => (
              <TagComponent
                key={`${tag.name}-${index}`}
                name={tag.name}
                difficulty={tag.difficulty}
                id={tag.id}
              />
            ))}
        </div>

        <AnswerInput
          value={userAnswer}
          onChange={setUserAnswer}
          onSubmit={handleSubmit}
          disabled={isSubmitting || userSolution?.isCorrect}
          aria-label={`Answer for: ${question}`}
          className={evaluationStatus}
          evaluationStatus={evaluationStatus}
        />
      </div>
    </>
  );
};

export default ProblemCard;
