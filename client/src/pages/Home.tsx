import Navbar from "../components/Navbar";
import "../css/Home.css";

function Home() {
  return ( 
    <>
      <Navbar />

      <div className="hero-container">
        <img src={'src/assets/full_logo.svg'} className="full-logo" />
        <div className="animation-player">ANIMATION</div>
      </div>
    </>
  );
};

export default Home;
