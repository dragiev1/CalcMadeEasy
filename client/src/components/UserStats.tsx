import "../css/UserStats.css";
import InteractiveRadialGraph from "./InteractiveRadialGraph";

const UserStats = () => {
  return (
    <>
      <div className="main-container">
        <div className="year-stats">
          <div className="month-group">
            <div className="bar h-0"></div>
            <p className="month">Jan</p>
          </div>
          <div className="month-group">
            <div className="bar h-0"></div>
            <p className="month">Feb</p>
          </div>
          <div className="month-group">
            <div className="bar h-25"></div>
            <p className="month">Mar</p>
          </div>
          <div className="month-group">
            <div className="bar h-25"></div>
            <p className="month">Apr</p>
          </div>
          <div className="month-group">
            <div className="bar h-50"></div>
            <p className="month">May</p>
          </div>
          <div className="month-group">
            <div className="bar h-50"></div>
            <p className="month">Jun</p>
          </div>
          <div className="month-group">
            <div className="bar h-75"></div>
            <p className="month">Jul</p>
          </div>
          <div className="month-group">
            <div className="bar h-75"></div>
            <p className="month">Aug</p>
          </div>
          <div className="month-group">
            <div className="bar h-75"></div>
            <p className="month">Sep</p>
          </div>
          <div className="month-group">
            <div className="bar h-100"></div>
            <p className="month">Oct</p>
          </div>
          <div className="month-group selected">
            <div className="bar h-100"></div>
            <p className="month">Nov</p>
          </div>
          <div className="month-group">
            <div className="bar h-0"></div>
            <p className="month">Dec</p>
          </div>
        </div>

        <div className="stats-info">
          <div className="graph-container" style={{marginTop: "0.75em"}}>
            <InteractiveRadialGraph />
          </div>
          <div className="info">
            <p>
              Current GPA: <span>4.0</span><br />
            </p>
            <p>
              Confidence Score: <span>97%</span>
            </p>
            <p>
              Longest Streak: <span>31 Days</span>
            </p>
          </div>
        </div>
      </div>
    </>
  );
};

export default UserStats;
