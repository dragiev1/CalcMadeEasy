import Navbar from "../components/Navbar";
import "../css/Home.css";
import lorenzVideo from "../assets/RiemannSums.mp4";

function Home() {
  return ( 
    <>
      <Navbar />

      <div className="hero-container">
        <img src={'src/assets/full_logo.svg'} className="full-logo" />
        <div className="animation-player">
          <video 
            src={lorenzVideo}
            autoPlay
            loop
            muted
            playsInline
            className="lorenz-animation" 
          > Your browser does not support the video tag.
          </video>
        </div>
      </div>
    </>
  );
};

export default Home;
