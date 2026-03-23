import '../css/BeginButton.css';

const Button = () => {
  return (
      <div>
        <svg style={{position: 'absolute', width: 0, height: 0}}>
          <filter width="3000%" x="-1000%" height="3000%" y="-1000%" id="unopaq">
            <feColorMatrix values="1 0 0 0 0 
            0 1 0 0 0 
            0 0 1 0 0 
            0 0 0 3 0" />
          </filter>
        </svg>
        <div className="backdrop" />
        <button className="button">
          <div className="a l" />
          <div className="a r" />
          <div className="a t" />
          <div className="a b" />
          <div className="text">start</div>
        </button>
      </div>
  );
};

export default Button;
