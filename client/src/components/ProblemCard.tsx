import "../css/QuestionCard.css";
import { type ProblemProps } from "../types/Problem";
import TagComponent from "./TagComponent";
import AnswerInput from "./AnswerInput";
import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTag } from "@fortawesome/free-solid-svg-icons";
import { verifyUserSolution } from "../api/problemService";

const ProblemCard: React.FC<ProblemProps> = ({
  id,
  description,
  position,
  showQuestion = true,
  solved = false,
  tags = [], // Default to empty array.
  userSolution,
}) => {
  //  ========== TEMP FOR TESTING ==========
  const pageId = "c3d4e5f6-a7b8-4901-d1e2-f3a4b5c6d7e8";
  //  ======================================

  const [userAnswer, setUserAnswer] = useState(
    solved ? userSolution : "",
  );

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [showTags, setShowTags] = useState(true);
  const [maxAttemptsReached, setMaxAttemptsReached] = useState(false);
  const [status, setStatus] = useState<"idle" | "checking" | "correct" | "incorrect">("idle");

  useEffect(() => {
    if(userSolution !== undefined) {
      setUserAnswer(userSolution);
      if (solved) setStatus("correct");
    }
  }, [solved, userSolution]);

  const handleSubmit = async () => {
    // Prevents submission if already correct, submitting.
    if (!userAnswer?.trim() || isSubmitting || status === "correct") return;

    setIsSubmitting(true);
    setStatus("checking");
    
    try {
      const result = await verifyUserSolution(pageId, id, userAnswer);
            
      if(result.correct) setStatus("correct");
      else setStatus("incorrect");

      // TODO: Create a message bubble displaying this to user!
      if(result.maxAttemptsReached) {
        console.log("Max Attempts Reached!");
        setMaxAttemptsReached(true);
      };
    } catch (e) {
      console.error("Verification failed: ", e);
      setStatus("idle");
    } finally {
      setIsSubmitting(false);
    }
  };

  const toggleTags = () => setShowTags((prev) => !prev);

  // Determine visual status, prioritize live submission status, fallback is idle.
  const evaluationStatus = status !== "idle"
    ? status
    : (solved ? "correct" : "idle");

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
              {showQuestion && description}
            </div>
          </div>
          <div
            className="tag-toggle-btn"
            onClick={toggleTags}
            aria-expanded={showTags}
            aria-label={showTags ? "Hide tags" : "Show tags"}
          >
            <FontAwesomeIcon
              icon={faTag}
              className={`tag-icon ${showTags ? "active" : ""}`}
            />
          </div>
        </div>

        {/* Tags */}
        <div className={`tags-wrapper ${showTags ? 'show' : 'hide'}`}>
          <div className="card-tags">
            {tags.map((tag: any, index: number) => (
              <TagComponent
                key={`${tag.tagName}-${index}`}
                name={tag.tagName}
                difficulty={tag.difficulty}
                id={tag.id}
              />
            ))}
          </div>
        </div>

        <AnswerInput
          value={userAnswer || ""}
          onChange={setUserAnswer}
          onSubmit={handleSubmit}
          disabled={isSubmitting || status === "correct" || maxAttemptsReached}
          aria-label={`Answer for: ${description}`}
          className={evaluationStatus}
          evaluationStatus={evaluationStatus}
        />
      </div>
    </>
  );
};

export default ProblemCard;
