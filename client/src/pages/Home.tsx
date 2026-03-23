import Navbar from "../components/Navbar";
import "../css/Home.css";
import { useState } from "react";

import gaussianIntegral from "../assets/math-animations/GaussianIntegral.mp4";
import riemannSums from "../assets/math-animations/RiemannSums.mp4";
import lorenzAttractor from "../assets/math-animations/LorenzAttractorWithEquations.mp4";
import BeginButton from "../components/BeginButton";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowDown } from "@fortawesome/free-solid-svg-icons";
import LearnMoreSection from "../components/LearnMoreSection";

const mathAnimations = [riemannSums, gaussianIntegral, lorenzAttractor];

function Home() {
  const [currentVideoIndex, setCurrentVideoIndex] = useState(0);

  const handleVideoEnd = () => {
    setCurrentVideoIndex((prev) => (prev + 1) % mathAnimations.length);
  };

  return (
    <>
      <Navbar />

      <div className="hero-container">
        <div className="full-logo-container">
          <img
            src={"src/assets/full_logo.svg"}
            className="full-logo"
            alt="CalcMadeEasy Logo"
          ></img>
          <div className="subtitle">Where math clicks. </div>
          <BeginButton />
        </div>

        <div className="animation-player">
          <video
            key={currentVideoIndex}
            src={mathAnimations[currentVideoIndex]}
            autoPlay
            muted
            playsInline
            className="lorenz-animation"
            onEnded={handleVideoEnd}
          >
            {" "}
            Your browser does not support the video tag.
          </video>
        </div>
      </div>

      <div className="divider">
        <h3>
          <FontAwesomeIcon icon={faArrowDown} /> Learn More <FontAwesomeIcon icon={faArrowDown} />
        </h3>
      </div>

      <LearnMoreSection />
    </>
  );
}

export default Home;
