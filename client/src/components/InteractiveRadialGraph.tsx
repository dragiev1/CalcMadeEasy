import RadialGraph from './RadialGraph';

function InteractiveRadialGraph() {
  return (
      <div className="rounded-lg shadow-md">
        <RadialGraph value={93.4} size={110} strokeWidth={9} secondaryColor='gray'/>
      </div>
  );
}

export default InteractiveRadialGraph;