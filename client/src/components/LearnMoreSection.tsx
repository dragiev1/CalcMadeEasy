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
import Latex from "./Latex/Latex";
import type { ProblemProps } from "../types/Problem";

const LearnMoreSection = () => {
  const mockProblems: ProblemProps[] = [
    {
      question: <>
                  {""} Integrate the following:{" "}
                  <Latex>{"\\int_0^{\\pi/4} \\sec^2(x)\\,dx"}</Latex>
                </>,
      solved: false,
      tags: [
        { name: "Algebra", difficulty: 0.15, id: 1 },
        { name: "Trig Identity", difficulty: 0.34, id: 2 },
        { name: "Integration By Parts", difficulty: 0.9, id: 3 },
      ],
      confidenceScore: 93,
    },
    {
      question: <>
                  {""} Let <Latex>{"f(x) = x^2"}</Latex>. Using the limit definition of the derivative, find <Latex>{"f'"}</Latex>{" "}:
                </>,
      solved: true,
      tags: [
        { name: "Limit", difficulty: 0.4, id: 4 },
        { name: "Algebra", difficulty: 0.2, id: 5 },
      ],
      confidenceScore: 68,
    },
  ];

  return (
    <>
      <div className="divider">
        <h3>
          <FontAwesomeIcon icon={faArrowDown} /> Learn More{" "}
          <FontAwesomeIcon icon={faArrowDown} />
        </h3>
      </div>
      <div className="learn-container">
        {/* Section 1 */}
        <div className="section">
          <div className="explanation">
            <div className="explanation-title">
              See Math; Be Comfortable With It.
            </div>

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
        <div className="section">
          <div className="demo">
            DEMO :Split-screen animation: Left side shows chaotic, rushed
            textbook-style explanation. Right side shows my calm, methodical
            visual breakdown of the same concept (e.g., integration as area
            accumulation)
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
        <div className="section">
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
            {mockProblems.map((problem, index) => (
              <QuestionCard
                key={index}
                question={problem.question}
                position={index + 1}
                solved={problem.solved}
                confidenceScore={problem.confidenceScore}
                tags={problem.tags}
              />
            ))}
          </div>
        </div>

        {/* Section 4 */}
        <div className="section">
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
    </>
  );
};

export default LearnMoreSection;
