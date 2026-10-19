/*
Section 1:
LEFT - Feature:
    See Math, Don't Just Solve It
    Stop memorizing formulas you don't understand. Our visual animations reveal the why behind every concept, transforming abstract symbols into intuitive understanding that sticks.
RIGHT - Demo:
Show an animation of a derivative being built up from secant lines approaching a tangent, with the limit process visualized smoothly.

Section 2:
LEFT - Demo:
    Split-screen animation: Left side shows chaotic, rushed textbook-style explanation. Right side shows my calm, methodical visual breakdown of the same concept (e.g., integration as area accumulation).
RIGHT - Feature:

    Learn at the Speed of Understanding
    No rushing. No stress. Just clear, professional explanations that respect your intelligence. Master calculus deeply, not quickly.

Section 3
LEFT - Feature:

    Problems Crafted by Educators, Not Algorithms
    Every homework problem is hand-designed with purposeful tags, calibrated difficulty, and intelligent confidence scoring. The system learns your patterns—so practice targets exactly what you need, not what's random.

RIGHT - Demo:
Screenshot/mockup of two problem cards:

Section 4
LEFT - Demo:
    Profile dashboard mockup & analytics:

RIGHT - Feature:
    Exams That Measure Growth, Not Just Grades
    Unit assessments adapt to your learning profile. Before: see your readiness by topic. See improvements. Every exam is a checkpoint in your journey—not a judgment, but a map forward.
*/

import { faArrowDown } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "../css/LearnMoreSection.css";

import UserStats from "./UserStats";
import QuestionCard from "./QuestionCard";
// import Latex from "./Latex/Latex";
// import type { ProblemProps } from "../types/Problem";
// import type { UserProgressProps } from "../types/User";
import { useScrollReveal } from "../utils/useScrollReveal";
import { type Problem } from "../types/Problem";
import { getProblemById } from "../api/problemService";
import { useEffect, useState } from "react";

const LearnMoreSection = () => {
  const [sec1Ref, sec1Visible] =useScrollReveal();
  const [sec2Ref, sec2Visible] =useScrollReveal();
  const [sec3Ref, sec3Visible] =useScrollReveal();
  const [sec4Ref, sec4Visible] =useScrollReveal();

  /**
   * Testing: Fetching an example problem and passing the info in the QuestionCard component.
   */
  const problemId = "123e4567-e89b-12d3-a456-426614174000";  
  const [problem, setProblem] = useState<Problem | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchProblem = async () => {
      try {
        setIsLoading(true);
        const data = await getProblemById(problemId);
        setProblem(data); 
      } catch (e) { 
        console.error("Failed to fetch problem! Using fallback data.", e);
        
        // FALLBACK: If the API fails, use this mock data so the UI doesn't break!
        setProblem({
          id: problemId,
          description: "What is the derivative of x²?",
          points: 10,
          isChallenge: false,
          solution: "2x",
          solutionType: "NUMERICAL",
          tags: [
            { id: "1", name: "Derivative", difficulty: 0.3 },
            { id: "2", name: "Power Rule", difficulty: 0.2 }
          ]
        });
      } finally {
        setIsLoading(false); 
      }
    };

    fetchProblem();
  }, [problemId]);

  // Keep the loading screen, but remove the null return so it falls back gracefully
  if (isLoading) return <p style={{ textAlign: 'center', padding: '2rem' }}>Loading problem...</p>; 


  return (
    <div className="learn-more-wrapper">
      <div className="divider">
        <h3>
          <FontAwesomeIcon icon={faArrowDown} /> Learn More{" "}
          <FontAwesomeIcon icon={faArrowDown} />
        </h3>
      </div>
      <div className="learn-container">
        {/* Section 1 */}
        <div className={`section ${sec1Visible ? 'visible' : ''}`} ref={sec1Ref}>
          <div className="explanation">
            <div className="explanation-title">See Math; Be Comfortable With It.</div>
            <div className="explanation-body">
              Stop memorizing formulas. Our interactive animations reveal the{" "}
              <i>why</i> behind "alien" concepts, transforming abstract symbols
              into intuitive understanding that sticks.
            </div>
          </div>
          <div className="demo">
            DEMO : Show an animation of a derivative being built up from secant
            lines approaching a tangent, with the limit process visualized
            smoothly.
          </div>
        </div>

        {/* Section 2 */}
        <div className={`section ${sec2Visible ? 'visible' : ''}`} ref={sec2Ref}>
          <div className="demo">

          </div>
          <div className="explanation">
            <div className="explanation-title text-right">
              Learn at the Speed of Understanding
            </div>

            <div className="explanation-body">
              No rushing. No stress. Just clear, professional explanations that
              respect your intelligence. Master mathematics deeply at your own
              pace.
            </div>
          </div>
        </div>

        {/* Section 3 */}
        <div className={`section ${sec3Visible ? 'visible' : ''}`} ref={sec3Ref}>
          <div className="explanation">
            <div className="explanation-title">
              Problems Crafted by Educators, Not AI
            </div>

            <div className="explanation-body">
              Every homework problem is hand-designed with purposeful tags,
              calibrated difficulty, and intelligent confidence scoring. We
              learn your patterns, record, and display it.
            </div>
          </div>
          <div className="demo">
            {/* {mockProblems.map((problem, index) => (
              <QuestionCard
                key={index}
                id={problem.id}
                question={problem.question}
                position={index + 1}
                solved={problem.solved}
                confidenceScore={problem.confidenceScore}
                tags={problem.tags}
                userSolution={problem.userSolution}/>
            ))} */}
            <QuestionCard 
              id={problem?.id}
              question={problem?.description}
              tags={problem?.tags || []}
              position={3} 
              solved={false}       
            />
          </div>
        </div>

        {/* Section 4 */}
        <div className={`section ${sec4Visible ? 'visible' : ''}`} ref={sec4Ref}>
          <div className="demo">
            <UserStats />
          </div>
          <div className="explanation">
            <div className="explanation-title">
              We Measure Growth, Not Just Grades
            </div>

            <div className="explanation-body">
              See your readiness by topic. See improvements. Every exam is a
              checkpoint in your journey. It is not a judgment, but a map
              forward.
            </div>
          </div>
        </div>
      </div>
      <div className="divider-bottom"></div>
    </div>
  );
};

export default LearnMoreSection;
